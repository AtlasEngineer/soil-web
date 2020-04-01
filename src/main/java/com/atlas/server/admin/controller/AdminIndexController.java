package com.atlas.server.admin.controller;


import com.atlas.server.admin.interceptor.AdminRoleInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.upload.UploadFile;
import com.lambkit.common.BaseResult;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.RedisUtil;
import com.lambkit.common.util.StringUtils;
import com.lambkit.component.shiro.session.ShiroSession;
import com.lambkit.module.sysconfig.TableconfigModel;
import com.lambkit.module.upms.UpmsConstant;
import com.lambkit.module.upms.UpmsManager;
import com.lambkit.module.upms.UpmsResult;
import com.lambkit.module.upms.UpmsResultConstant;
import com.lambkit.module.upms.rpc.api.UpmsApiService;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.module.upms.rpc.service.impl.UpmsApiServiceImpl;
import com.lambkit.module.upms.shiro.ShiroRedisSessionDao;
import com.lambkit.plugin.auth.AuthManager;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.BaseController;
import com.lambkit.web.interceptor.CommonInterceptor;
import org.apache.commons.lang.BooleanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Clear(JwtTokenInterceptor.class)
@RequiresRoles(value={"super","admin","member"}, logical=Logical.OR)
public class AdminIndexController extends BaseController {

	// -------------------------------
	// 跳转控制
	// -------------------------------
	@Clear
	@Before(value= AdminRoleInterceptor.class)
	public void index() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		if(username!=null) {
		UpmsApiService upmsApiService = Enhancer.enhance(UpmsApiServiceImpl.class);
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
			setAttr("user", upmsUser);
		}else{
			//login();
			return;
			//setAttr("user", null);	
		}
		keepPara();
		String appdata = this.getPara("m");
		if(StringUtils.hasText(appdata)) {
			setAttr("tbnav", TableconfigModel.dao.find("select * from sys_tableconfig where tbnavtypeid>8 order by tbnavtypeid, tborder, tbid"));
		}
		setAttr("title", "首页"); 
		System.out.println(getAttr("template").toString());
		String temp = getAttr("template", "admin");
		renderFreeMarker(temp + "/index.html"); 
	}
	
	public void home() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsApiService upmsApiService = Enhancer.enhance(UpmsApiServiceImpl.class);
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
		setAttr("usernum", 3);
		setAttr("userdaynum", 5);
		setAttr("postnum", 6);
		setAttr("postchecknum", 7);
		setAttr("newpost", TableconfigModel.dao.find("select * from sys_tableconfig where tbnavtypeid>7 order by tbnavtypeid, tborder, tbid"));
		setAttr("username", upmsUser.getUsername());
		renderFreeMarker("home.html"); 
	}
	
	@Clear
	@Before(CommonInterceptor.class)
	public void login() {
		if (getRequest().getMethod().equals("GET")) {
    		Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String serverSessionId = session.getId().toString();
            // 判断是否已登录，如果已登录，则回跳
            String code = Redis.use().get(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + serverSessionId);
            String username = (String) subject.getPrincipal();
            // code校验值
            if (StringUtils.isNotBlank(code) && StrKit.notBlank(username) && !username.equals("null")) {
                // 回跳
                String backurl = getRequest().getParameter("backurl");
                //String username = (String) subject.getPrincipal();
                if (StringUtils.isBlank(backurl)) {
                    backurl = "/";
                    backurl = subject.hasRole("admin") ? "/manage" : "";
                    backurl = subject.hasRole("super") ? "/manage" : "";
                } else {
                    if (backurl.contains("?")) {
                        backurl += "&upms_code=" + code + "&upms_username=" + username;
                    } else {
                        backurl += "?upms_code=" + code + "&upms_username=" + username;
                    }
                }
                System.out.println("认证中心帐号通过，带code回跳：{}" + backurl);
                redirect(backurl);
            } else {
            	keepPara();
            	//renderJsp("login.jsp");
//            	render("/hqlogin/login.html");
				renderJson(Ret.fail("msg","请登录"));
            }
    	} else {
    		renderJson(tologinResult());
    	}
	}

	
	@Clear
    public void dologin() {
    	if (getRequest().getMethod().equals("GET")) {
    		Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String serverSessionId = session.getId().toString();
            // 判断是否已登录，如果已登录，则回跳
            String code = Redis.use().get(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + serverSessionId);
            String username = (String) subject.getPrincipal();
            // code校验值
            if (StringUtils.isNotBlank(code) && StrKit.notBlank(username) && !username.equals("null")) {
                // 回跳
                String backurl = getRequest().getParameter("backurl");
                //String username = (String) subject.getPrincipal();
                if (StringUtils.isBlank(backurl)) {
                    backurl = "/";
                    backurl = subject.hasRole("admin") ? "/manage" : "";
                    backurl = subject.hasRole("super") ? "/manage" : "";
                } else {
                    if (backurl.contains("?")) {
                        backurl += "&upms_code=" + code + "&upms_username=" + username;
                    } else {
                        backurl += "?upms_code=" + code + "&upms_username=" + username;
                    }
                }
                System.out.println("认证中心帐号通过，带code回跳：{}" + backurl);
                redirect(backurl);
                //render("/admin?m=");
            } else {
            	keepPara();
            	//renderJsp("login.jsp");
            	render("login.html");
            }
    	} else {
			UpmsResult upmsResult = tologinResult();
			renderJson(Ret.ok().set("token",upmsResult.getData()));
    		//render("index.html");
    	}
    }
	
	private UpmsResult tologinResult() {

		String phone = getRequest().getParameter("phone");
        String password = getRequest().getParameter("password");
//        String captcha = getRequest().getParameter("captcha");
        String rememberMe = getRequest().getParameter("rememberMe");
        if (StringUtils.isBlank(phone)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME, "邮箱不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "密码不能为空！");
        }
//        if (StringUtils.isBlank(captcha)) {
//            return new UpmsResult(UpmsResultConstant.EMPTY_CAPTCHA, "验证码不能为空！");
//        }
//        if (!validateCaptcha("captcha")) {
//            return new UpmsResult(UpmsResultConstant.INVALID_CAPTCHA, "验证码不正确！");
//        }
        UpmsUser user = UpmsManager.me().getUpmsApiService().selectUpmsUserByUsername(phone);
        if(user==null) {
        	return new UpmsResult(UpmsResultConstant.INVALID_USERNAME, "帐号不存在！");
        }
        List<UpmsUserRole> userRole = UpmsUserRole.service().find(UpmsUserRole.sql().andUserIdEqualTo(user.getUserId()).example());
        boolean flag = false;
        for (UpmsUserRole upmsUserRole : userRole) {
			if(upmsUserRole!=null && upmsUserRole.getRoleId() < 6) {
				flag = true;
				break;
			}
		}
        if(!flag) {
        	return new UpmsResult(UpmsResultConstant.INVALID_ACCOUNT, "帐号不可用！");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        System.out.println("sessionId: "+sessionId);
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = Redis.use().get(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + sessionId);
        // code校验值
        if (StringUtils.isBlank(hasCode)) {
            BaseResult result = AuthManager.me().getService().login(getRequest(), phone, password, BooleanUtils.toBoolean(rememberMe));
            if(result.getCode()!=UpmsResultConstant.SUCCESS.getCode()) {
            	return (UpmsResult) result;
            }
            // 更新session状态
            ShiroRedisSessionDao upmsSessionDao = Enhancer.enhance(ShiroRedisSessionDao.class);
            upmsSessionDao.updateStatus(sessionId, ShiroSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            Redis.use().lpush(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证帐号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(UpmsConstant.LAMBKIT_UPMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        // 回跳登录前地址
        String backurl = getRequest().getParameter("backurl");
        System.out.println("backurl:"+backurl);
        if (StringUtils.isBlank(backurl)) {
        	if("16601349428".equals(phone)) {
        		return new UpmsResult(UpmsResultConstant.SUCCESS, "/admin?m=");
        		//render("admin/index.html");
        	} else {
        		return new UpmsResult(UpmsResultConstant.SUCCESS, "{'token':'"+ tologinResult().getData().toString()+"'}");
        	}
        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, backurl);
        }
    }
	
	public void logout() {
    	BaseResult result = AuthManager.me().getService().logout(getRequest());
        String redirectUrl = result.getData().toString();
        if (null == redirectUrl) {
            redirectUrl = "/admin?m=";
        }
        redirect("/admin?m=");
    }
	@Clear
	public void needPermission() {
		render("/WEB-INF/errors/needPermission.html");
	}
	// -------------------------------
	// 功能函数
	// -------------------------------
	@RequiresAuthentication
	public void upload() {
		System.out.println("/app/upload");
		// 获取上传的文件
		UploadFile uf = getFile("Filedata", PathKit.getWebRootPath()
				+ "/static/upload/attach");
		String fileext = PathUtils.getExtensionName(uf.getFileName());
		String filename = System.currentTimeMillis() + "." + fileext;
		uf.getFile().renameTo(
				new File(PathKit.getWebRootPath() + "/static/upload/attach/"
						+ filename));

		// 拼接文件上传的完整路径 
		String fileurl = "http://" + this.getRequest().getRemoteHost() + ":"
				+ this.getRequest().getLocalPort() + "/static/upload/attach/"
				+ filename;// uf.getFileName();

		this.setAttr("fileUrl", fileurl);
		this.setAttr("fileName", filename);
		System.out.println("================fileurl:" + fileurl);
		// 以json格式进行渲染
		renderJson();
	}
}
