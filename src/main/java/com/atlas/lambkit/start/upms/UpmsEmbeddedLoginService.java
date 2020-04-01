package com.atlas.lambkit.start.upms;

import com.google.gson.JsonObject;
import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.redis.Redis;
import com.lambkit.Lambkit;
import com.lambkit.common.BaseResult;
import com.lambkit.common.util.JsonUtils;
import com.lambkit.common.util.RedisUtil;
import com.lambkit.component.shiro.session.ShiroSession;
import com.lambkit.module.upms.UpmsConstant;
import com.lambkit.module.upms.UpmsResult;
import com.lambkit.module.upms.UpmsResultConstant;
import com.lambkit.module.upms.shiro.ShiroRedisSessionDao;
import com.lambkit.plugin.auth.AuthManager;
import com.lambkit.plugin.jwt.JwtKit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.UUID;

public class UpmsEmbeddedLoginService implements LoginService {

	public void captcha(Controller c) {
    	c.renderCaptcha();
	}
	
    public void login(Controller c) {
    	if (c.getRequest().getMethod().equals("GET")) {
    		Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            String serverSessionId = session.getId().toString();
            // 判断是否已登录，如果已登录，则回跳
            String code = Redis.use().get(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + serverSessionId);
            String username = (String) subject.getPrincipal();
            // code校验值
            if (StringUtils.isNotBlank(code) && StrKit.notBlank(username) && !username.equals("null")) {
                // 回跳
                String backurl = c.getRequest().getParameter("backurl");
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
                c.redirect(backurl);
            } else {
            	c.keepPara();
            	//renderJsp("login.jsp");
            	c.render("login.html");
            }
    	} else {
            UpmsResult upmsResult = tologinResult(c);
//            Object token = new JSONObject().put("token", upmsResult.getData().toString());
//            System.out.println(token.toString());
//            Object data = new JSONObject().put("data", token);
            c.renderJson(Ret.ok().set("token",upmsResult.getData()).set("code",200));
//            c.renderJson(tologinResult(c));
    	}
    }
    
    private UpmsResult tologinResult(Controller c) {
        String jsonString = HttpKit.readData(c.getRequest());
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        String phone = jsonObject.get("phone").toString();
        String password = jsonObject.get("password").toString();
        System.out.println(phone+":"+password);

//        String email = c.getRequest().getParameter("email");
//        String password = c.getRequest().getParameter("password");

        String rememberMe = c.getRequest().getParameter("rememberMe");

        if (StringUtils.isBlank(phone)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME, "手机号不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "密码不能为空！");
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        System.out.println("sessionId: "+sessionId);
        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = Redis.use().get(UpmsConstant.LAMBKIT_UPMS_SERVER_SESSION_ID + "_" + sessionId);
        // code校验值
//        JwtKit.userService.getJwtAbleInfo(phone);
        String token = JwtKit.getToken(phone, password);
        Lambkit.getCache().put("jwtcache", "phone",phone);
        BaseResult result = null;
        if (StringUtils.isBlank(hasCode)) {
            result = AuthManager.me().getService().login(c.getRequest(), phone, password, BooleanUtils.toBoolean(rememberMe));
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
        String backurl = c.getRequest().getParameter("backurl");
        System.out.println("backurl:"+backurl);
        if (StringUtils.isBlank(backurl)) {
        	/*
        	if("admin".equals(username)) {
        		return new UpmsResult(UpmsResultConstant.SUCCESS, "/manage");
        	} else {
        		return new UpmsResult(UpmsResultConstant.SUCCESS, "/");
        	}*/
//        	return new UpmsResult(UpmsResultConstant.SUCCESS,new JSONObject().put("token",StringUtils.isBlank(result.message)?"aaabbbccc":"aaabbbccc"));
            return new UpmsResult(UpmsResultConstant.SUCCESS, token);

        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, token);
        }
    }
	
    
    public void logout(Controller c) {
    	BaseResult result = AuthManager.me().getService().logout(c.getRequest());
        String redirectUrl = result.getData().toString();
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        c.redirect(redirectUrl);
    }
    
    public void ajaxLogout(Controller c) {
    	AuthManager.me().getService().logout(c.getRequest());
    	c.renderJson(new UpmsResult(UpmsResultConstant.SUCCESS, "logout"));
    }
    
}
