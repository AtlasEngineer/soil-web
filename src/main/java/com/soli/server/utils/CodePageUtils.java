package com.soli.server.utils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.web.RequestManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @版权 : Copyright (c) 2019-2021 爱特拉斯信息科技有限公司技术开发部
 * @author: queer
 * @E-mail: xuzhonghao0808@163.com
 * @创建日期: 2019年11月28日 上午11:24:29
 * @ClassName CpdetectorUtils
 * @类描述-Description: TODO
 */
public class CodePageUtils {

    public static <T> List<T> removeNull(List<? extends T> oldList) {

        oldList.removeAll(Collections.singleton(null));
        return (List<T>) oldList;
    }


    public static UpmsUser getUserEntity(){
        String serverSessionId = RequestManager.me().getRequest().getHeader("Authorization");
        Session se = SecurityUtils.getSecurityManager().getSession(new DefaultSessionKey(serverSessionId));
        Object obj = se.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimplePrincipalCollection coll = (SimplePrincipalCollection) obj;
        String primaryPrincipal = (String) coll.getPrimaryPrincipal();

//        System.out.println("******Authorization****"+serverSessionId);
//        String code = UpmsManager.me().getCache().getSession(serverSessionId);
//        System.out.println("******code****"+code);
//        String username = "";
//        System.out.println("********读取的session*****"+code);
//        if (org.apache.commons.lang.StringUtils.isNotBlank(code)) {
//        Subject subject = SecurityUtils.getSubject();
//        String username = (String) subject.getPrincipal();
//        } else {
//           return null;
//        }
        System.out.println("********解析的用户名为********* "+primaryPrincipal);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(primaryPrincipal).example());
        return  upmsUser;
    }

    public static String getCodePage(String path) {
        try {
            InputStream dbf = new FileInputStream(path);
            byte[] bytes = new byte[30];
            dbf.read(bytes);
            byte b = bytes[29];
            System.out.println(b);
            String hexString = Integer.toHexString(Byte.toUnsignedInt(b));
            System.out.println(hexString);
            if ("0".equals(hexString) || hexString == "0") {
                return "UTF-8";
            }
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            Record record = Db.findFirst("select name from codepage where str = '0x" + hexString + "' ");
            System.out.println("编码方式：" + record.getStr("name"));
            return record.getStr("name");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
