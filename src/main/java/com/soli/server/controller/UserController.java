package com.soli.server.controller;

import com.soli.server.utils.Co;
import com.jfinal.aop.Clear;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.LambkitResult;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.swagger.annotation.Api;
import com.lambkit.component.swagger.annotation.ApiOperation;
import com.lambkit.component.swagger.annotation.Param;
import com.lambkit.component.swagger.annotation.Params;
import com.lambkit.db.sql.column.Example;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.rpc.api.UpmsUserService;
import com.lambkit.module.upms.rpc.model.*;
import com.lambkit.plugin.auth.AuthManager;
import com.lambkit.plugin.auth.IUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.RequestManager;
import com.lambkit.web.controller.LambkitController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.bouncycastle.util.Integers;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.soli.server.utils.CodePageUtils.getUserEntity;
import static com.soli.server.utils.CodePageUtils.removeNull;

@Api(tag = "/user", description = "用户")
public class UserController extends LambkitController {


    @Clear(JwtTokenInterceptor.class)
    public void delUser(){
        // 获取当前登陆对象
        String serverSessionId = this.getRequest().getHeader("Authorization");;
        String code = UpmsManager.me().getCache().getSession(serverSessionId);
        String username = null ;
        String userIds = getPara("userIds");
        if(StringUtils.isBlank(userIds)){
                renderJson(Co.ok("data", Ret.fail("errorMsg", "请选择删除的id")));
        }
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data",Ret.fail("errorMsg", "请重新登录")));
            return;
        }
        UpmsUser user = getUserEntity();

        Map<String, Object> first = user._getAttrsAsMap();
        if (first == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "当前登录用户异常")));
            return;
        }else if ("1".equals(first.get("role_id"))){
            renderJson(Co.ok("data", Ret.fail("errorMsg", "当前登录用户权限不足")));
            return;
        }else {
            String[] split = userIds.split(",");
            String sql = "";
            for (int i = 0; i < split.length; i++) {
                sql += "'"+split[i]+"',";
//                UpmsUser byId = UpmsUser.service().dao().findById(split[i]);
//                byId.set("del",1);
//                byId.update();
            }
            if (!"".equals(sql)) {
                sql = sql.substring(0,sql.length()-1);
                int update = Db.use("upms").update("UPDATE upms_user SET del = '1' WHERE user_id in (" + sql + ")");
                if (update > 0 ){
                    renderJson(Co.ok("data",  Ret.ok("msg", "删除成功")));
                    return;
                }else {
                    renderJson(Co.ok("data", Ret.fail("errorMsg", "删除失败")));
                    return;
                }
            }
        }
    }


    /**
     * @Description: web登录
     * @Author:
     * @Date:
     */
    @Clear(JwtTokenInterceptor.class)
    @Params({
            @Param(name = "username", description = "用户名", required = true, dataType = "String"),
            @Param(name = "password", description = "密码", required = true, dataType = "String"),
            @Param(name = "isRemember",description = "记住我", required = true , dataType = "boolean")
    })
    @ApiOperation(url = "/user/login", tag = "/user", httpMethod = "post", description = "用户登录")
    public void login() {
        String username = getPara("username");
        String password = getPara("password");
        Boolean isRemember = getParaToBoolean("isRemember",false);


        //校验
        if (StringUtils.isBlank(username)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写用户名")));
            return;
        }
        if (StringUtils.isBlank(password)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写密码")));
            return;
        }
        System.out.println(username);
        Example example = UpmsUser.sql().andUsernameEqualTo(username).example();
        UpmsUser dao = UpmsUser.service().dao();
        UpmsUser upmsUser = dao.findFirst(example);
        if (upmsUser == null || upmsUser.getUserId() == null){
            renderJson(Co.ok("data", Ret.fail("errorMsg", "用户不存在")));
            return;
        }
        Map<String, Object> stringObjectMap = upmsUser._getAttrsAsMap();

        LambkitResult login = AuthManager.me().getService().login(RequestManager.me().getRequest(), username, password, isRemember);

        System.out.println("sessionId********************"+login.getData());
        if ("success".equals(login.getMessage())){
            if (upmsUser == null || stringObjectMap == null || "1".equals( stringObjectMap.get("del"))) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "用户不存在")));
                return;
            } else if (!upmsUser.getPassword().equals(EncryptUtils.MD5(password + upmsUser.getSalt()))) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "密码错误")));
                return;
            }else if (upmsUser.getLocked() == 1){
                renderJson(Co.ok("data",Ret.fail("errorMsg","账号正在审核中")));
                return;
            } else {

                //renderJson(Co.ok("data", Ret.ok("sessionId",login.getData()).set("userMsg", upmsUser.put("role_id", roles)).set("perlist", perlist).set("urilist", urilist)));
                renderJson(Co.ok("data", Ret.ok("sessionId",login.getData())));

                return;
                 }
        }else {
            String data = login.getData().toString();
            System.out.println("登陆结果 ： ***"+data);
            if (data.startsWith("密码错误")) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "密码错误!")));
            }else if (data.startsWith("帐号已锁定")){
                renderJson(Co.ok("data", Ret.fail("errorMsg", "账号正在审核中!")));
            }else {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "登录失败!")));
            }
            return;
        }

    }

    /**
     * @Description: 获取当前登录用户信息
     * @Author: xuzhonghao
     * @Date: 2019/9/23
     */
    @Clear()
    @ApiOperation(url = "/user/info", tag = "/user", httpMethod = "get", description = "根据token获取用户信息")
    public void info() {
        // 获取当前登陆对象
        String serverSessionId = this.getRequest().getHeader("Authorization");;
        String code = UpmsManager.me().getCache().getSession(serverSessionId);
        String username = null ;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data", Ret.fail("errorMsg","请重新登陆")));
            return;
        }
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "当前登录用户异常")));
            return;
        }

        List<UpmsUserRole> userRoles = UpmsUserRole.service().dao().find(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
        ArrayList<Integer> roles = new ArrayList<>();
        //获取权限集合
        List<String> perlist = new ArrayList<>();
        List<String> urilist = new ArrayList<>();
        for (UpmsUserRole upmsUserRole : userRoles) {
            roles.add(upmsUserRole.getRoleId());
            List<UpmsRolePermission> upmsRolePermissions = UpmsRolePermission.service().dao().find(UpmsRolePermission.sql().andRoleIdEqualTo(Long.valueOf(upmsUserRole.getRoleId())).example());
            for (UpmsRolePermission urp : upmsRolePermissions) {
                UpmsPermission per = UpmsPermission.service().dao().findById(urp.getPermissionId());
                if (per == null) {
                    continue;
                }
                if (per.getSystemId() == 6) {
                    perlist.add(per.getPermissionValue());
                    if (StringUtils.isNotBlank(per.getUri()) && !urilist.contains(per.getUri())) {
                        urilist.add(per.getUri());
                    }
                }
            }
            List<UpmsUserPermission> upmsUserPermissions = UpmsUserPermission.service().dao().find(UpmsUserPermission.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
            for (UpmsUserPermission uup : upmsUserPermissions) {
                UpmsPermission per = UpmsPermission.service().dao().findById(uup.getPermissionId());
                if (per == null) {
                    continue;
                }
                if (per.getSystemId() == 6) {
                    perlist.add(per.getPermissionValue());
                    if (StringUtils.isNotBlank(per.getUri()) && !urilist.contains(per.getUri())) {
                        urilist.add(per.getUri());
                    }
                }
            }
        }
        perlist = removeNull(perlist);
        UpmsUserRole userRole = UpmsUserRole.service().findFirst(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
        UpmsRole role = UpmsRole.service().findFirst(UpmsRole.sql().andRoleIdEqualTo(Long.valueOf(userRole.getRoleId())).example());
        upmsUser.put("rolename", role.getName());
        upmsUser.put("roleid", role.getRoleId());
        upmsUser.put("roletitle", role.getTitle());
        renderJson(Co.ok("data", Ret.ok("data", Co.by("user",upmsUser).set("perlist", perlist))));
    }

    /**
     * @Description: 根据id查询用户信息
     * @Author: xuzhonghao
     * @Date: 2019/9/23
     */
    @Clear()
    @ApiOperation(url = "/user/getUserById", tag = "/user", httpMethod = "get", description = "根据id查询用户信息")
    public void getUserById() {
        IUser user = AuthManager.me().getUser(RequestManager.me().getRequest());
        String username = user.getName();
        Integer id=getParaToInt("id");
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(
                "select * from upms_user where user_id = ?", id);
        if (upmsUser == null) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "当前未查到用戶")));
            return;
        }
        renderJson(Co.ok("data", Ret.ok("data", upmsUser)));
    }

    /**
     *  获取除当前用户用户列表
     */
    @Clear()
    public void getUserList(){
        // 获取当前登陆对象
        String serverSessionId = this.getRequest().getHeader("Authorization");;
        String code = UpmsManager.me().getCache().getSession(serverSessionId);
        String username = null ;
        Integer pageNum = getParaToInt("pageNum",1);
        Integer pageSize = getParaToInt("pageSize",10);

        if (StringUtils.isNotBlank(code)) {
            Session se = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(serverSessionId));
            Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
            String primaryPrincipal = (String) coll.getPrimaryPrincipal();
            username = primaryPrincipal;
            System.out.println("authenticate: " + username);

            String name = getPara("username");
            String roleId = getPara("roleId");
            String time = getPara("time");
            String realname = getPara("realname");
            String phone = getPara("phone");
            String status = getPara("status");


            String sqlSel = "SELECT c.identity,a.del,a.realname,a.phone,a.workaddress,a.workunit,a.create_time as creatime,a.status,a.username,a.user_id,a.locked ";
            String sqlFrom = "FROM upms_user a " +
                    " LEFT JOIN upms_user_role b  ON a.user_id = b.user_id " +
                    " LEFT JOIN  upms_role c ON b.role_id = c.role_id " +
                    " WHERE a.del = '0' AND a.username != '"+ username +"' AND a.username != 'admin' ";
            if (!"admin".equals(username)){
                sqlFrom += " AND b.role_id = '4' ";
            }

            /**
             *  姓名
             */
            if (StringUtils.isNotBlank(realname)){
                sqlFrom += " AND a.realname like '%" + realname + "%' ";
            }
            /**
             *  手机号
             */
            if (StringUtils.isNotBlank(phone)){
                sqlFrom += " AND a.phone = '" + phone + "' ";
            }

            /**
             *  角色状态
             */
            if (StringUtils.isNotBlank(status)){
                sqlFrom += " AND a.status = " + status + " ";
            }

            /**
             *  用户名
             */
            if (StringUtils.isNotBlank(name)){
                sqlFrom += " AND a.username like '%" + name + "%' ";
            }

            /**
             *  roleId
             */
            if (StringUtils.isNotBlank(roleId) && !"null".equals(roleId)){
                sqlFrom += " AND b.role_id = '" + roleId + "' ";
            }

            /**
             * 创建时间
             */
            if (StringUtils.isNotBlank(time)){
                String[] split = time.split(",");
                try {
                    sqlFrom += " AND a.create_time between '" + split[0] + "' AND '" + split[1] + "' ";
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            sqlFrom += " ORDER BY locked DESC, create_time DESC";
            Page<UpmsUser> paginate = UpmsUser.service().dao().paginate(pageNum, pageSize, sqlSel,sqlFrom);
            renderJson(Co.ok("data", Ret.ok("data",paginate))) ;
            return;
        } else {
            System.out.println("无效访问unlogin");
            renderJson(Co.ok("data", Ret.fail("errorMsg","请重新登陆")));
            return;
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
        String realName = getPara("realName");//真实姓名
        String phone = getPara("phone");//手机号/联系方式
        String workaddress = getPara("address");//部门
        String workunit = getPara("workunit");//岗位
        Integer roleId = getParaToInt("roleId");//
        boolean flag = false;
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

        String regxPassword = "^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[0-9a-zA-Z\\W]\\S{7,20}$";
        if (!password.matches(regxPassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "密码格式不正确")));
            return;
        } else if (StringUtils.isBlank(qrpassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写确认密码")));
            return;
        } else if (!password.equals(qrpassword)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "密码与确认密码不一致")));
            return;
        } else if (StringUtils.isBlank(realName)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "请填写真实姓名")));
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
            //开始注册
            flag = orReg(roleId,username, realName, password, phone, workaddress, workunit);
        if (flag) {
            renderJson(Co.ok("data", Ret.ok("msg", "注册成功")));
            return;
        } else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "注册失败")));
            return;
        }
    }


    //普通用户注册
    private boolean orReg(Integer roleId, String username, String realName, String password, String phone, String workaddress, String workunit) {
        //注册
        boolean flag = Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                // TODO Auto-generated method stub
                UpmsUserService upmsUserService = UpmsUser.service();
                UpmsUser upmsUser = getModel(UpmsUser.class, "upms");
                upmsUser.set("workaddress", workaddress);
                upmsUser.set("workunit", workunit);
                upmsUser.setUsername(username);
                upmsUser.setPhone(phone);
                upmsUser.setRealname(realName);
                upmsUser.setCtime(new Date().getTime());
                upmsUser.set("create_time", new Date());
                String salt = StrKit.getRandomUUID();
                upmsUser.setSalt(salt);
                String md5pswd = EncryptUtils.MD5(password + upmsUser.getSalt());
                upmsUser.setPassword(md5pswd);
                upmsUser.setLocked(1);//锁定账户需要审核
                upmsUser.set("status",1);
                upmsUser.set("del", 0);
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

    @Clear()
    @ApiOperation(url = "/user/audit", tag = "/user", httpMethod = "get", description = "审核用户")
    public void audit() {
        // 获取当前登陆对象
        String serverSessionId = this.getRequest().getHeader("Authorization");;
        String code = UpmsManager.me().getCache().getSession(serverSessionId);
        Integer id = getParaToInt("id");
        Integer status = getParaToInt("status");//0审核通过，1待审核，2审核未通过
        Integer locked;
        if(status == 1 || status == 0){
            locked = status;
        }else {
            locked = getParaToInt("locked");
        }
        String username = null ;
        if (!StringUtils.isNotBlank(code)) {
            System.out.println("无效访问unlogin");
            this.renderJson(Co.ok("data",Ret.fail("errorMsg", "请重新登陆")));
            return;
        }
        System.out.println("name : " + username);
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }
        UpmsUser userById = UpmsUser.service().dao().findById(id);
        if (userById == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "您选择的用户不存在")));
            return;
        }
        //判断当前登录用户权限
        boolean user1role = false;
        List<UpmsUserRole> upmsUserRoles = UpmsUserRole.service().dao().find(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
        for (UpmsUserRole role : upmsUserRoles) {
            if (role.getRoleId() == 1 || role.getRoleId() == 3 ) {
                user1role = true;
                break;
            }
        }
        if (!user1role) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "您你没有权限审核用户")));
            return;
        }
        if (locked != null ){
            userById.setLocked(locked);
        }
        if (status != null){
            userById.set("status",status);
        }
        boolean update = userById.update();
        if (update) {
            renderJson(Co.ok("data",Ret.ok("data", "审核成功")));
            return;
        } else {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "审核失败，请联系管理员")));
            return;
        }

    }

    /**
     * @return void
     * @Author
     * @Description //TODO 角色列表
     * @Date 14:19 2020/2/25
     * @Param []
     **/
    @ApiOperation(url = "/user/roleList", tag = "/user", httpMethod = "get", description = "角色列表")
    public void roleList() throws ParseException {

        String username = getPara("username");
        String date = getPara("date");
        Integer role_id = getParaToInt("role_id");

        String token = RequestManager.me().getRequest().getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "请登录")));
            return;
        }
        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String name = JwtKit.getJwtUser(authToken);
        if (name == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "token异常")));
            return;
        }
        System.out.println("name : " + name);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(name).example());

        if (upmsUser == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }
        Integer pageNum = getParaToInt("pageNum", 1);
        Integer pageSize = getParaToInt("pageSize", 10);

        List<UpmsUserRole> upmsUserRoles = UpmsUserRole.service().dao().find(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());

        int user1roleid = 0;
        for (UpmsUserRole role : upmsUserRoles) {
            if (role.getRoleId() == 1 || role.getRoleId() == 3) {
                user1roleid = role.getRoleId();
                break;
            }
        }
        System.out.println();
        Page<Record> paginate;
        if (user1roleid == 1) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("from upms_user u left join upms_user_role r on u.user_id = r.user_id where locked = 0 and del = 0");
            if (StringUtils.isNotBlank(username)) {
                stringBuffer.append(" and u.username LIKE '%" + username + "%'");
            }
            if (StringUtils.isNotBlank(date)) {
                String time[] = date.split(",");
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time[0]);
                Date t = new SimpleDateFormat("yyyy-MM-dd").parse(time[1]);
                stringBuffer.append(" and u.create_time between '" + time[0] + "' and '" + time[1] + "' ");
            }
            if (role_id != null) {
                stringBuffer.append(" and r.role_id =" + role_id + " ");
            }
            paginate = Db.use("upms").paginate(pageNum, pageSize, "select * ",
                    stringBuffer.toString());
        } else if (user1roleid == 3) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("from upms_user u left join upms_user_role r on u.user_id = r.user_id where locked = 0 and del = 0");
            if (StringUtils.isNotBlank(username)) {
                buffer.append(" and u.username LIKE '%" + username + "%'");
            }
            if (StringUtils.isNotBlank(date)) {
                String time[] = date.split(",");
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(time[0]);
                Date t = new SimpleDateFormat("yyyy-MM-dd").parse(time[1]);
                buffer.append(" and u.create_time between '" + time[0] + "' and '" + time[1] + "' ");
            }
            if (role_id != null) {
                buffer.append(" and r.role_id =" + role_id + " ");
            }
            paginate = Db.use("upms").paginate(pageNum, pageSize, "select * ",
                    buffer.toString());
        } else {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "没有权限获得该列表")));
            return;
        }
        renderJson(Co.ok("data",Ret.ok("data", paginate)));
        return;

    }


    /**
     * @return void
     * @Author queer
     * @Description //TODO 修改用户权限
     * @Date 14:19 2020/2/25
     * @Param []
     **/
    @ApiOperation(url = "/user/updateUserByrole", tag = "/user", httpMethod = "get", description = "修改用户权限")
    public void updateUserByrole() {
        Long user_id = getParaToLong("user_id");
        Integer role_id = getParaToInt("role_id");

        String token = RequestManager.me().getRequest().getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "请登录")));
            return;
        }
        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String name = JwtKit.getJwtUser(authToken);
        if (name == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "token异常")));
            return;
        }
        System.out.println("name : " + name);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(name).example());
        if (upmsUser == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "获取用户信息错误")));
            return;
        }

        UpmsUserRole userRole = UpmsUserRole.service().findFirst(UpmsUserRole.sql().andUserIdEqualTo(upmsUser.getUserId()).example());
        if (userRole.getRoleId() != 1 ||  userRole.getRoleId() != 3) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "您没有权限")));
            return;
        }

        UpmsUserRole role = UpmsUserRole.service().findFirst(UpmsUserRole.sql().andUserIdEqualTo(user_id).example());

        if (role == null) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "没有该用户信息")));
            return;
        }

        role.setRoleId(role_id);
        boolean result = role.update();
        if (result) {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "修改权限成功")));
            return;
        } else {
            renderJson(Co.ok("data",Ret.fail("errorMsg", "修改权限失败")));
            return;
        }
    }
    /**
     * @return void
     * @Author queer
     * @Description //TODO 模板引擎
     * @Date 14:19 2020/2/25
     * @Param []
     **/
    @ApiOperation(url = "/user/model", tag = "/user", httpMethod = "get", description = "模板引擎")
    public void model() {
        List<Record> list = Db.find("from sys_tableconfig");
        renderJson(Co.ok("data", list));
        return;
    }

    @Clear()
    @ApiOperation(url = "/user/updatePwd", tag = "/user", httpMethod = "get", description = "更新用户密码")
    public void updatePwd() {
        UpmsUser upmsUser = getUserEntity();
        if (upmsUser == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "当前登录用户异常")));
            return;
        }
        String password = getPara("pass");
        String newpass = getPara("newpass");
        String checknewpass = getPara("checknewpass");
        if (StringUtils.isBlank(password)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "原密码不能为空")));
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

    @Clear()
    @ApiOperation(url = "/user/updatePassword", tag = "/user", httpMethod = "get", description = "强制更新用户密码")
    public void  updatePassword(){
        UpmsUser user = getUserEntity();
        if (user == null) {
            renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "当前登录用户异常")));
            return;
        } else {
            //判断当前登录用户权限
            List<UpmsUserRole> upmsUserRoles = UpmsUserRole.service().dao().find(UpmsUserRole.sql().andUserIdEqualTo(user.getUserId()).example());
            for (UpmsUserRole role : upmsUserRoles) {
                if (!( role.getRoleId() == 1 || role.getRoleId() == 3) ) {
                    renderJson(Co.ok("data", Co.by("state", "fail").set("errorMsg", "当前用户没有权限")));
                    return;
                }
            }
        }
        String username = getPara("username");
        String newpass = getPara("newpass");

        if (StringUtils.isBlank(newpass)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "新密码不能为空")));
            return;
        }
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        //校验原密码
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

    @Clear()
    @ApiOperation(url = "/user/updateUserMessage", tag = "/user", httpMethod = "get", description = "更新用户个人信息")
    public void updateUserMessage(){
        UpmsUser userEntity = getUserEntity();
        String realname = getPara("realname");
        String phone = getPara("phone");
        String workunit = getPara("workunit");
        String workaddress = getPara("workaddress");
        Boolean ifUpdate = false ;
        if (StringUtils.isNotBlank(realname)){
            userEntity.set("realname",realname);
            ifUpdate = true ;
        }
        if (StringUtils.isNotBlank(phone)){
            userEntity.set("phone",phone);
            ifUpdate = true ;
        }
        if (StringUtils.isNotBlank(workunit)){
            userEntity.set("workunit",workunit);
            ifUpdate = true;
        }
        if (StringUtils.isNotBlank(workaddress)){
            userEntity.set("workaddress",workaddress);
            ifUpdate = true ;
        }
        if (ifUpdate){
            ifUpdate = userEntity.update();
            if (ifUpdate) {
                renderJson(Co.ok("data", Ret.ok("msg", "修改成功")));
                return;
            } else {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "修改失败！")));
                return;
            }
        }else {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "没有需要修改的信息！")));
            return;
        }
    }
}
