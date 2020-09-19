package com.atlas.test.module;

import com.beust.jcommander.internal.Maps;
import com.lambkit.db.mgr.MgrdbConfig;
import com.lambkit.db.mgr.MgrdbManager;

import java.util.Map;

/**
 * Sysconfig表格配置初始化
 * @author yangyong
 */
public class SysconfigInitTest {
	
	public static void main(String[] args) {
		Map<String, Object> options = Maps.newHashMap();
		//标题中需要去掉的前缀
		options.put("tableRemovePrefixes", "meta_,sys_");
		//不包含如下数据表
		options.put("excludedTables", "sys_fieldconfig, sys_tableconfig");
		//仅包含如下数据表
		options.put("includedTables", "tr_directory");
		MgrdbManager.me().run(options, MgrdbConfig.SYSCONFIG);
		System.exit(0);
	}
}
