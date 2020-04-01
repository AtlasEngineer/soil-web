package com.atlas.server.admin.controller;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.common.BaseResult;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.module.upms.UpmsResult;
import com.lambkit.module.upms.UpmsResultConstant;
import com.lambkit.module.upms.rpc.api.UpmsApiService;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.module.upms.rpc.model.UpmsUserRole;
import com.lambkit.module.upms.rpc.service.impl.UpmsApiServiceImpl;
import com.lambkit.plugin.auth.AuthManager;
import com.lambkit.web.controller.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;

//import org.apache.shiro.SecurityUtils;
/*
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.lambkit.common.base.BaseResult;
import com.lambkit.common.util.EncryptUtils;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.StringUtils;
import com.lambkit.db.mgr.TableConfig;
import com.lambkit.module.auth.AuthManager;
import com.lambkit.upms.common.UpmsResult;
import com.lambkit.upms.common.UpmsResultConstant;
import com.lambkit.upms.dao.model.UpmsUser;
import com.lambkit.upms.dao.model.UpmsUserRole;
import com.lambkit.upms.dao.model.query.UpmsUserQuery;
import com.lambkit.upms.dao.model.query.UpmsUserRoleQuery;
import com.lambkit.upms.rpc.api.UpmsApiService;
import com.lambkit.upms.rpc.service.impl.UpmsApiServiceImpl;
import com.lambkit.web.controller.BaseController;
import com.lambkit.cms.CmsEngine;
import com.lambkit.cms.model.PostsModel;
import com.lambkit.cms.modules.user.LoginService;
import com.lambkit.cms.modules.user.LoginValidator;
import com.lambkit.jfinal.component.user.UsersModel;
import com.lambkit.jfinal.shiro.CaptchaFormAuthenticationInterceptor;
import com.lambkit.jfinal.web.BaseController;
import com.lambkit.jfinal.web.CommonInterceptor;
import com.lambkit.jfinal.web.CommonService;
import com.lambkit.mgrdb.entity.TableconfigModel;
import com.lambkit.util.StringUtils;
import com.lambkit.util.file.PathUtils;
import com.lambkit.web.LambService;
import com.lambkit.web.TableConfig;
import com.lambkit.web.Widget;*/

@RequiresRoles(value={"super","admin","member"}, logical=Logical.OR)
public class UserController extends BaseController {

	// -------------------------------
	// 跳转控制
	// -------------------------------
	@Clear
	public void index() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		UpmsApiService upmsApiService = Enhancer.enhance(UpmsApiServiceImpl.class);
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
		setAttr("user", upmsUser);
		setAttr("title", "首页");   
		renderFreeMarker("index.html");
	}
	public void logout() {
    	BaseResult result = AuthManager.me().getService().logout(getRequest());
        String redirectUrl = result.getData().toString();
        if (null == redirectUrl) {
            redirectUrl = "/admin?m=";
        }
        redirect("/admin?m=");
    }
	/**
	 * 跳转到修改密码页面
	 */
	public void repass() {
		setAttr("title", "修改密码");
		render("repass.html");
	}
	
	/**
	 * 修改密码
	 */
	public void modifypass() {
		//原密码
    	String ypass = getPara("ypass");
		String xpass = getPara("password");
		String qrxpass = getPara("qrxpass");
		
		// 当前登录用户权限
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		
		UpmsApiService upmsApiService = Enhancer.enhance(UpmsApiServiceImpl.class);
		UpmsUser upmsUser = upmsApiService.selectUpmsUserByUsername(username);
		if(upmsUser==null) {
			// shiro退出登录
			SecurityUtils.getSubject().logout();
	        renderJson(new UpmsResult(UpmsResultConstant.FAILED, "未登录"));
			return;
		}
		String md5pswd = EncryptUtils.MD5(ypass+upmsUser.getSalt());
		if(ypass.equals("")||xpass.equals("")||qrxpass.equals("")){
			renderJson(new UpmsResult(UpmsResultConstant.FAILED, "请填写完整信息"));
			return;
		}else if(!md5pswd.equals(upmsUser.getPassword())){
			renderJson(new UpmsResult(UpmsResultConstant.FAILED, "原密码错误"));
			return;
		}else if(ypass.equals(xpass)){
			renderJson(new UpmsResult(UpmsResultConstant.FAILED, "原密码不能和新密码一样"));
			return;
		}else if(!xpass.equals(qrxpass)){
			renderJson(new UpmsResult(UpmsResultConstant.FAILED, "新密码必须和确认新密码一样"));
			return;
		}else if(xpass.length()<6||xpass.length()>12){
			renderJson(new UpmsResult(UpmsResultConstant.FAILED, "新密码的长度为6-12位"));
			return;
		}
		upmsUser.setPassword(EncryptUtils.MD5(xpass+upmsUser.getSalt()));
		boolean flag = upmsUser.update();
		if(flag) renderJson(new UpmsResult(UpmsResultConstant.SUCCESS, "更新成功"));
		else renderJson(new UpmsResult(UpmsResultConstant.FAILED, "更新失败"));
	}
	public void list() {
		Integer pagenum = getParaToInt("pagenum",1);
		Integer pagesize = getParaToInt("pagesize",15);
		String sql="from upms_user where status='0'";
		if(StringUtils.hasText(getPara("username"))) sql +=" and username like '%"+getPara("username")+"%'";
		Page<UpmsUser> list = UpmsUser.service().dao().paginate(pagenum, pagesize, "select *", sql);
		setAttr("page", list);
		renderJson();
	}
	public void check() {
		Long id = getParaToLong("id");
	    String roid = getPara("roleid", "6");
	    UpmsUser upmsUser = (UpmsUser)getModel(UpmsUser.class);
	    upmsUser.set("user_id",id);
	    upmsUser.set("status", Integer.valueOf(1));
	    upmsUser.update();
	    UpmsUserRole m = UpmsUserRole.service().dao().findFirst("select * from upms_user_role where user_id='" + id + "'");
	    UpmsUserRole rol = getModel(UpmsUserRole.class);
	    rol.set("user_role_id", m.getUserRoleId());
	    rol.set("role_id", roid);
	    boolean flag = rol.update();
	    renderAt(flag);
	}
}
