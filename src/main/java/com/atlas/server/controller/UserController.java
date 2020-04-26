package com.atlas.server.controller;

import com.atlas.server.utils.Co;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.lambkit.Lambkit;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.db.sql.column.Example;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.api.UpmsUserService;
import com.lambkit.module.upms.rpc.model.*;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
import com.lambkit.web.controller.LambkitController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserController extends LambkitController {

    /**
     * @Description: web登录
     * @Author: yangxueyang
     * @Date:
     */
    @Clear(JwtTokenInterceptor.class)
    @Params({
            @Param(name = "username", description = "用户名", required = true, dataType = "String"),
            @Param(name = "password", description = "密码", required = true, dataType = "String")
    })
    @ApiOperation(url = "/user/login", tag = "/user", httpMethod = "post", description = "用户登录")
    public void login() {
        String username = getPara("username");
        String password = getPara("password");

        //校验
        if (StringUtils.isBlank(username)) {
            renderJson(Co.fail("msg", "请填写用户名"));
            return;
        }
        if (StringUtils.isBlank(password)) {
            renderJson(Co.fail("msg", "请填写密码"));
            return;
        }
        System.out.println(username);
        UpmsUser upmsUser = UpmsManager.me().getUpmsApiService().selectUpmsUserByUsername(username);
        if (upmsUser == null) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "用户不存在")));
            return;
        } else if (!upmsUser.getPassword().equals(EncryptUtils.MD5(password + upmsUser.getSalt()))) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "密码错误")));
            return;
        } else {
            List<UpmsUserRole> userRole = UpmsUserRole.service().dao().find(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
            ArrayList<Integer> roles = new ArrayList<>();
            boolean flag = false;
            for (UpmsUserRole upmsUserRole : userRole) {
                if (upmsUserRole != null && upmsUserRole.getRoleId() > 5) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                renderJson(Co.by("token", ""));
            }
            String token = null;
            try {
                token = JwtKit.getToken(username, password);
            } catch (Exception e) {
                e.printStackTrace();
                renderJson(Co.ok("data", Co.fail("token", token)).set("msg", "账户审核中"));
                return;
            }
            if (StringUtils.isBlank(token)) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "账号或密码错误，获取token失败")));
                return;
            }
            Lambkit.getCache().put("jwtcache", username + "_username", username);
            System.out.println("token:" + token);

            renderJson(Co.ok("data", Co.ok("token", token).set("state", "ok").set("userMsg", upmsUser.put("role_id", roles))));
        }


    }


    /**
     * @Description: 获取当前登录用户信息
     * @Author: yangxueyang
     * @Date: 2019/9/23
     */
    @Clear()
    @ApiOperation(url = "/user/info", tag = "/user", httpMethod = "get", description = "根据token获取用户信息")
    public void info() {
        String token = RequestManager.me().getRequest().getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("msg", "请登录")));
            return;
        }
        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String username = JwtKit.getJwtUser(authToken);
        if (username == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("msg", "token异常")));
            return;
        }
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(
                "select user_id,username,realname,phone,workaddress,workunit,title from upms_user where username = ?", username);
        if (upmsUser == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("msg", "当前登录用户异常")));
            return;
        }
        UpmsUserRole userRole = UpmsUserRole.service().findFirst(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());

        UpmsRole role = UpmsRole.service().findFirst(UpmsRole.sql().andRoleIdEqualTo(Long.valueOf(userRole.getRoleId())).example());

        upmsUser.put("rolename", role.getName());
        upmsUser.put("roleid", role.getRoleId());
        upmsUser.put("roletitle", role.getTitle());
        renderJson(Co.ok("data", upmsUser));
    }


    @Clear()
    @ApiOperation(url = "/user/updatePwd", tag = "/user", httpMethod = "get", description = "更新用户密码")
    public void updatePwd() {
        String token = RequestManager.me().getRequest().getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "请登录")));
            return;
        }
        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String username = JwtKit.getJwtUser(authToken);
        if (username == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "token异常")));
            return;
        }
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (upmsUser == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "当前登录用户异常")));
            return;
        }
        String password = getPara("pass"); //原密码
        String newpass = getPara("newpass");//新密码
        String checknewpass = getPara("checknewpass");//确认密码
        if (StringUtils.isBlank(password)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "原不能为空")));
            return;
        }
        if (StringUtils.isBlank(newpass)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "新密码不能为空")));
            return;
        }
        if (StringUtils.isBlank(checknewpass)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "确认密码不能为空")));
            return;
        }
        if (!newpass.equals(checknewpass)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "确认密码与新密码不一致")));
            return;
        }
        //校验原密码
        String ypassword = upmsUser.getPassword();
        String md5pswd = EncryptUtils.MD5(password + upmsUser.getSalt());
        if (!ypassword.equals(md5pswd)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "原密码错误！")));
            return;
        } else {
            String newpassMd5 = EncryptUtils.MD5(newpass + upmsUser.getSalt());
            upmsUser.setPassword(newpassMd5);
            boolean update = upmsUser.update();
            if (update) {
                renderJson(Co.ok("data", Ret.ok("msg", "修改成功")));
                return;
            } else {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "修改失败！")));
                return;
            }
        }


    }


    /**
     * @Description: 用户注册
     * @Author: xuzhonghao
     * @Date: 2019/9/23
     */
    @Clear
    @Params({
            @Param(name = "username", description = "手机号", required = true, dataType = "String"),
            @Param(name = "password", description = "密码", required = true, dataType = "String"),
            @Param(name = "qrPassword", description = "确认密码", required = true, dataType = "String"),
            @Param(name = "roleId", description = "角色", required = true, dataType = "Int"),
            @Param(name = "realName", description = "真实姓名/企业名称", required = true, dataType = "String"),
    })
    @ApiOperation(url = "/user/register", tag = "/user", httpMethod = "post", description = "用户注册")
    public void register() {
        String username = getPara("username");//用户名
        String password = getPara("password");//密码
        String qrpassword = getPara("repassword");//确认密码
        String code = getPara("code");//验证码

        String phone = getPara("phone");//手机号/联系方式

        String regxusename = "[0-9a-zA-Z]{3,12}";
        if (StringUtils.isBlank(username)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写用户名")));
            return;
        }
        UpmsUser first1 = UpmsUser.service().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (first1 != null && first1.getInt("del") == 0) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "该用户名已被注册")));
            return;
        } else if (!username.matches(regxusename)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "用户名格式不正确，必须为3-12位字母和数字")));
            return;
        } else if (StringUtils.isBlank(password)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写密码")));
            return;
        }

        String regxPassword = "(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^[^\\s\\u4e00-\\u9fa5]{8,20}$";
        if (!password.matches(regxPassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "密码格式不正确")));
            return;
        } else if (StringUtils.isBlank(qrpassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写确认密码")));
            return;
        } else if (!password.equals(qrpassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "密码与确认密码不一致")));
            return;
        } else if (StringUtils.isBlank(phone)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写手机号")));
            return;
        } else if (StringUtils.isNotBlank(phone)) {
            String regxPhone = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";
            if (!phone.matches(regxPhone)) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "手机号格式不正确")));
                return;
            }
        }
        UpmsUser first = UpmsUser.service().findFirst(UpmsUser.sql().andPhoneEqualTo(phone).example());
        if (first != null && first.getInt("del") == 0) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "该手机号已被注册")));
            return;
        }
        boolean flag = false;

        flag = orReg(1, username, password, phone);


        if (flag) {
            renderJson(Co.ok("data", Co.ok("msg", "注册成功")));
        } else {
            renderJson(Co.ok("data", Co.fail("errorMsg", "注册失败")));
        }
    }

    private boolean orReg(Integer roleId, String username, String password, String phone) {
        //注册
        boolean flag = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                // TODO Auto-generated method stub
                UpmsUserService upmsUserService = UpmsUser.service();
                UpmsUser upmsUser = getModel(UpmsUser.class, "upms");

                upmsUser.setUsername(username);
                upmsUser.setPhone(phone);
                upmsUser.set("creatime", new Date());
                String salt = StrKit.getRandomUUID();
                upmsUser.setSalt(salt);
                String md5pswd = EncryptUtils.MD5(password + upmsUser.getSalt());
                upmsUser.setPassword(md5pswd);
                upmsUser.setLocked(0);//锁定账户需要审核
                upmsUser.set("del", 0);
                upmsUser.setCtime(DateTimeUtils.getCurrentTimeLong());
                upmsUser.setAvatar(getPara("avatar", "/resources/zheng-admin/images/avatar.jpg"));
                UpmsUser user = upmsUserService.createUser(upmsUser);
                if (user == null) {
                    return false;
                }
                UpmsUserRole upmsUserRole = new UpmsUserRole();
                upmsUserRole.setUserId(upmsUser.getUserId());
                upmsUserRole.setRoleId(roleId);
                boolean rflag = upmsUserRole.save();
                return rflag;
            }
        });
        return flag;
    }



    @Clear
    @ApiOperation(url = "/user/getCode", tag = "/user", httpMethod = "post", description = "获取验证码")
    public void getCode() {



    }



}
