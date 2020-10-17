package com.atlas.test;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.lambkit.plugin.mail.MailKit;
import com.lambkit.plugin.mail.MailPlugin;
import com.soli.server.utils.ReadTiffUtils;
import org.junit.Test;

;

/**
 *
 * @版权 : Copyright (c) 2019-2021 爱特拉斯信息科技有限公司技术开发部
 * @author: queer
 * @E-mail: xuzhonghao0808@163.com
 * @创建日期: 2019年12月25日 下午2:34:10
 * @ClassName EmailTest
 * @类描述-Description: TODO
 */
public class EmailTest {

	@Test
	public void test() {
//		new MailPlugin(PropKit.use("mail.properties").getProperties()).start();
//		MailKit.send("1825714477@qq.com", null, "Jfinal 邮件发送插件使用说明",
//				"Jfinal 邮件发送插件使用说明如下：<br/>1、配置插件");
		Kv tiffXY = ReadTiffUtils.getTiffXY("C:\\Users\\xiaoxu\\Desktop\\田\\田1\\田1_Level_19.tif");
	}
}
