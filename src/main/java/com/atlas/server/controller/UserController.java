package com.atlas.server.controller;

import com.atlas.server.utils.Co;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.lambkit.Lambkit;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.db.sql.column.Example;
import com.lambkit.module.upms.rpc.model.*;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
import com.lambkit.web.controller.LambkitController;

import java.util.ArrayList;
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
        Example example = UpmsUser.sql().andUsernameEqualTo(username).example();
        UpmsUser dao = UpmsUser.service().dao();
        UpmsUser upmsUser = dao.findFirst(example);
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

            //获取权限集合
            ArrayList<String> perlist = new ArrayList<>();
            for (UpmsUserRole upmsUserRole : userRole) {
                roles.add(upmsUserRole.getRoleId());
                List<UpmsRolePermission> upmsRolePermissions = UpmsRolePermission.service().dao().find(UpmsRolePermission.sql().andRoleIdEqualTo(Long.valueOf(upmsUserRole.getRoleId())).example());
                for (UpmsRolePermission urp : upmsRolePermissions) {
                    UpmsPermission per = UpmsPermission.service().dao().findById(urp.getPermissionId());
                    if (per == null) {
                        continue;
                    }
                    if (per.getSystemId() == 6) {
                        perlist.add(per.getPermissionValue());
                    }
                }
            }

            renderJson(Co.ok("data", Co.by("token", token).set("state", "ok").set("userMsg", upmsUser.put("role_id", roles)).set("perlist", perlist)));
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








}
