package com.atlas.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atlas.lambkit.start.MessageConfig;
import com.atlas.server.utils.Base64Util;
import com.atlas.server.utils.Co;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.upload.UploadFile;
import com.lambkit.Lambkit;
import com.lambkit.common.util.DateTimeUtils;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.core.cache.impl.RedisCacheImpl;
import com.lambkit.db.sql.column.Example;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.api.UpmsUserService;
import com.lambkit.module.upms.rpc.model.*;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
import com.lambkit.web.controller.LambkitController;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

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


    /**
     * @Description: 更新用户密码
     * @Author: yangxueyang
     * @Date: 2019/9/23
     */
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
            renderJson(Co.ok("data", Co.fail("errorMsg", "原不能为空")));
            return;
        }
        if (StringUtils.isBlank(newpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "新密码不能为空")));
            return;
        }
        if (StringUtils.isBlank(checknewpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "确认密码不能为空")));
            return;
        }
        if (!newpass.equals(checknewpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "确认密码与新密码不一致")));
            return;
        }
        //校验原密码
        String ypassword = upmsUser.getPassword();
        String md5pswd = EncryptUtils.MD5(password + upmsUser.getSalt());
        if (!ypassword.equals(md5pswd)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "原密码错误！")));
            return;
        } else {
            String newpassMd5 = EncryptUtils.MD5(newpass + upmsUser.getSalt());
            upmsUser.setPassword(newpassMd5);
            boolean update = upmsUser.update();
            if (update) {
                renderJson(Co.ok("data", Co.ok("msg", "修改成功")));
                return;
            } else {
                renderJson(Co.fail("data", Co.fail("errorMsg", "修改失败！")));
                return;
            }
        }


    }


    /**
     * @Description: 忘記密碼
     * @Author: yangxueyang
     * @Date: 2019/9/23
     */
    @Clear()
    @ApiOperation(url = "/user/forgetPwd", tag = "/user", httpMethod = "get", description = "更新用户密码")
    public void forgetPwd() {

        String phone = getPara("phone");//手机号联系方式

        String newpass = getPara("newpass");//新密码
        String checknewpass = getPara("checknewpass");//确认密码


        if (StringUtils.isBlank(newpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "新密码不能为空")));
            return;
        }
        if (StringUtils.isBlank(checknewpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "确认密码不能为空")));
            return;
        }
        if (!newpass.equals(checknewpass)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "确认密码与新密码不一致")));
            return;
        }
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andPhoneEqualTo(phone).example());

        if (upmsUser==null) {
            renderJson(Co.fail("data", Co.fail("errorMsg", "改用戶為空！")));
        }

        String newpassMd5 = EncryptUtils.MD5(newpass + upmsUser.getSalt());
        upmsUser.setPassword(newpassMd5);
        boolean update = upmsUser.update();
        if (update) {
            renderJson(Co.ok("data", Co.ok("msg", "修改成功")));

        } else {
            renderJson(Co.fail("data", Co.fail("errorMsg", "修改失败！")));
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


        String phone = getPara("phone");//手机号联系方式

        RedisCacheImpl redis = new RedisCacheImpl();
        String getCode = redis.get("code", phone);

        if (!code.equals(getCode)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "验证码不正确")));
            return;
        }

        String regxusename = "[0-9a-zA-Z]{3,12}";
        if (StringUtils.isBlank(username)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "请填写用户名")));
            return;
        }
        UpmsUser first1 = UpmsUser.service().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (first1 != null && first1.getInt("del") == 0) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "该用户名已被注册")));
            return;
        } else if (!username.matches(regxusename)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "用户名格式不正确，必须为3-12位字母和数字")));
            return;
        } else if (StringUtils.isBlank(password)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "请填写密码")));
            return;
        }

        String regxPassword = "^[a-zA-Z0-9]{6,20}$";
        if (!password.matches(regxPassword)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "密码格式不正确")));
            return;
        } else if (StringUtils.isBlank(qrpassword)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "请填写确认密码")));
            return;
        } else if (!password.equals(qrpassword)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "密码与确认密码不一致")));
            return;
        } else if (StringUtils.isBlank(phone)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "请填写手机号")));
            return;
        } else if (StringUtils.isNotBlank(phone)) {
            String regxPhone = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
            if (!phone.matches(regxPhone)) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "手机号格式不正确")));
                return;
            }
        }
        UpmsUser first = UpmsUser.service().findFirst(UpmsUser.sql().andPhoneEqualTo(phone).example());
        if (first != null && first.getInt("del") == 0) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "该手机号已被注册")));
            return;
        }
        boolean flag = false;

        flag = orReg(1, username, password, phone);


        if (flag) {
            renderJson(Co.ok("data", Co.ok("msg", "注册成功")));
        } else {
            renderJson(Co.fail("data", Co.fail("errorMsg", "注册失败")));
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
                upmsUser.setSex(1);
                upmsUser.setLocked(0);//锁定账户需要审核
                upmsUser.setCtime(DateTimeUtils.getCurrentTimeLong());
                upmsUser.setRealname(username);
                upmsUser.set("autograph","一花一世界   一岁一枯荣");
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


    /**
     * @Description: 获取验证码
     * @Author: yangxueyang
     * @Date:
     */
    @Clear
    @ApiOperation(url = "/user/getCode", tag = "/user", httpMethod = "post", description = "获取验证码")
    public void getCode() {
        String phone = getPara("phone");
        MessageConfig config = Lambkit.config(MessageConfig.class);

        if (StringUtils.isBlank(phone)) {
            renderJson(Co.fail("data", Co.fail("errorMsg", "手机号为空！")));
            return;
        }else{
            String regxPhone = "^((13[0-9])|(14[579])|(15([0-3,5-9]))|(16[6])|(17[0135678])|(18[0-9]|19[89]))\\d{8}$";
            if (!phone.matches(regxPhone)) {
                renderJson(Co.ok("data", Co.fail("errorMsg", "手机号格式不正确")));
                return;
            }
        }

        String code = String.format("%04d", new Random().nextInt(9999));

        if (StringUtils.isBlank(code)) {
            renderJson(Co.fail("data", Co.fail("errorMsg", "获取失败！")));
            return;
        }

        RedisCacheImpl redis = new RedisCacheImpl();
        redis.put("code", phone, code, 60 * 2);

        String result = sendMessage(phone, config, code);
        if ("success".equals(result)) {
            renderJson(Co.ok("data", Co.ok("msg", "获取成功")));
        } else {
            renderJson(Co.fail("data", Co.fail("errorMsg", "获取失败！")));
        }
    }



    /**
     * @Description: 验证验证码
     * @Author: yangxueyang
     * @Date:
     */
    @Clear
    @ApiOperation(url = "/user/verifyCode", tag = "/user", httpMethod = "post", description = "验证验证码")
    public void verifyCode() {
        String phone = getPara("phone");
        String code=getPara("code");


        if (StringUtils.isBlank(phone)) {
            renderJson(Co.fail("data", Co.fail("errorMsg", "手机号为空！")));
            return;
        }

        if (StringUtils.isBlank(code)) {
            renderJson(Co.fail("data", Co.fail("errorMsg", "验证码为空！")));
            return;
        }

        RedisCacheImpl redis = new RedisCacheImpl();
        String getCode=redis.get("code", phone);

        if(getCode.equals(code)){
            renderJson(Co.ok("data", Co.ok("msg", "验证成功")));
        } else {
            renderJson(Co.fail("data", Co.fail("errorMsg", "验证失败！")));
        }
    }



    /**
     * @Description: 修改用戶信息
     * @Author: yangxueyang
     * @Date:
     */
    @Clear
    @ApiOperation(url = "/user/updateInfo", tag = "/user", httpMethod = "post", description = "修改用户信息")
    public void updateInfo() {
        String realName = getPara("realName");    //真实姓名
        String autograph = getPara("autograph"); //签名
        Integer sex = getParaToInt("sex"); //性别（0男 1女）
        String birthday = getPara("birthday"); //生日
        String address = getPara("address");  //住址
        String work = getPara("work"); //工作

        if (StringUtils.isBlank(realName)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "真实姓名不能为空")));
            return;
        }
        if (StringUtils.isBlank(autograph)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "签名不能为空")));
            return;
        }
        if (sex == null) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "性别不能为空")));
            return;
        }

        if (StringUtils.isBlank(birthday)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "生日不能为空")));
            return;
        }
        if (StringUtils.isBlank(address)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "住址不能为空")));
            return;
        }
        if (StringUtils.isBlank(work)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "工作不能为空")));
            return;
        }

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
        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sft.parse(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int age = Base64Util.getAgeByBirth(date);

        upmsUser.setRealname(realName);
        upmsUser.set("age", age);
        upmsUser.set("sex", sex);
        upmsUser.set("work", work);
        upmsUser.set("autograph", autograph);
        upmsUser.set("workaddress", address);
        upmsUser.set("birthday", birthday);

        boolean result = upmsUser.update();

        if (result) {
            renderJson(Co.ok("data", Co.ok("msg", "修改成功")));

        } else {
            renderJson(Co.fail("data", Co.fail("errorMsg", "修改失败！")));

        }


    }

    /**
     * @Description: 修改用戶頭像
     * @Author: yangxueyang
     * @Date:
     */
    @Clear
    @ApiOperation(url = "/user/updatePortrait", tag = "/user", httpMethod = "post", description = "修改用戶頭像")
    public void updatePortrait() {

        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Co.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                file.delete();
                renderJson(Co.ok("data", Co.fail("errorMsg", "重命名失败")));
                return;
            } else {
                String url = "/upload/"+filename;

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
                upmsUser.setAvatar(url);
                boolean result = upmsUser.update();

                if (result) {
                    renderJson(Co.ok("data", Co.ok("msg", "修改成功").set("url", "/upload/" + filename)));

                } else {
                    renderJson(Co.fail("data", Co.fail("errorMsg", "修改失败！")));
                }
            }
        }
    }


    //發短信
    public static String sendMessage(String phone, MessageConfig config, String code) {
        String pathUrl = "https://openapi-smsp.getui.com/v1/sps/push_sms_list"; //短信群推url
        JSONObject requestDataObject = new JSONObject();
        requestDataObject.put("appId", config.getAppId());
        requestDataObject.put("authToken", doPostOrGet(config));
        requestDataObject.put("smsTemplateId", config.getSmsTemplateId());
        System.out.println(EncryptUtils.MD5(phone));
        Map<String, String> param = new HashMap<String, String>();
        param.put("phone", Base64Util.getBase64(phone));

        param.put("code", code);
        requestDataObject.put("smsParam", param);
        List<String> phoneNum = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
            phoneNum.add(EncryptUtils.md5(phone));
        }
        requestDataObject.put("recNum", phoneNum);

        System.out.println(requestDataObject.toString());

        OutputStreamWriter out = null;
        BufferedReader br = null;
        String result = "";
        String message = "";
        try {
            URL url = new URL(pathUrl);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //请求方式
            conn.setRequestMethod("POST");
            //conn.setRequestMethod("GET");
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            conn.setDoOutput(true);
            conn.setDoInput(true);
            /**
             * 下面的三句代码，就是调用第三方http接口
             */
            //获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            //发送请求参数即数据
            out.write(requestDataObject.toString());
            //flush输出流的缓冲
            out.flush();
            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                result += str;
            }
            System.out.println(result);
            message = JSON.parseObject(result).getString("msg");
            //关闭流
            is.close();
            //断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }


    //获取授权的方法
    public static String doPostOrGet(MessageConfig config) {

        String pathUrl = "https://openapi-smsp.getui.com/v1/sps/auth_sign";
        long timestamp = System.currentTimeMillis();

        //sha256加密，使用org.apache.commons包中自带的加密方法，需将加密后数据一起上传
        String sign = DigestUtils.sha256Hex(String.format("%s%d%s", config.getAppKey(), timestamp, config.getMasterSecret()));
        JSONObject requestDataObject = new JSONObject();
        requestDataObject.put("sign", sign);
        requestDataObject.put("timestamp", timestamp);
        requestDataObject.put("appId", config.getAppId());


        OutputStreamWriter out = null;
        BufferedReader br = null;
        String result = "";
        String authToken = "";
        try {
            URL url = new URL(pathUrl);
            //打开和url之间的连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //请求方式
            conn.setRequestMethod("POST");
            //conn.setRequestMethod("GET");

            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            //DoOutput设置是否向httpUrlConnection输出，DoInput设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            conn.setDoOutput(true);
            conn.setDoInput(true);

            /**
             * 下面的三句代码，就是调用第三方http接口
             */
            //获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            //发送请求参数即数据
            out.write(requestDataObject.toString());
            //flush输出流的缓冲
            out.flush();

            /**
             * 下面的代码相当于，获取调用第三方http接口后返回的结果
             */
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                result += str;
            }
            System.out.println(result);
            String msg = JSON.parseObject(result).getString("data");
            authToken = JSON.parseObject(msg).getString("authToken");
            //关闭流
            is.close();
            //断开连接，disconnect是在底层tcp socket链接空闲时才切断，如果正在被其他线程使用就不切断。
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return authToken;
    }


    //統計用户收藏数量
    @Clear
    public  void  count(){
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

        Integer collection_num=Db.queryInt("select count(*) from news_collection n WHERE n.user_id="+upmsUser.getUserId()+" and `status`=0");
        Integer discern_num=Db.queryInt("select count(*) from catalogue_keep n WHERE n.user_id="+upmsUser.getUserId()+" and del=0");

        renderJson(Co.ok().set("discern_num",discern_num).set("collection_num",collection_num));

    }


}
