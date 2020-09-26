package com.atlas.test.module;

import com.google.common.collect.Maps;
import com.lambkit.generator.GeneratorConfig;
import com.lambkit.generator.GeneratorType;
import com.lambkit.generator.Msch;

import java.util.Map;

/**
 * @author: xuzhonghao
 * @create: 2019-08-02 16:14
 */
public class GeneratorTest {

    public static void main(String[] args) {
            GeneratorConfig config = new GeneratorConfig();
            //生成java代码的存放地址
            config.setOutRootDir("D:/aaaGeneratorConfig");
            //生成java代码的包地址
            config.setBasepackage("com.soli.server");
            //生成前端文件文件夹
            config.setWebpages("app");
            //表格配置方式
            config.setMgrdb("normal");
            //选择一种模板语言
            config.setEngine(GeneratorConfig.TYPE_VELOCITY);
            //选择一种处理引擎
            config.setType(GeneratorType.DB);
            //模板地址，根目录是项目文件夹
            String templatePath = "/template";
            System.out.println("地址："+System.getProperty("user.dir"));
            Map<String,Object> options = Maps.newHashMap();
            //需要去掉的前缀
            options.put("tableRemovePrefixes", "tr_");
            //仅包含如下表格
            //options.put("includedTables", "ld_company,ld_research_team,ld_research_team_user,ld_field_option,ld_field_point,ld_field_point_image,ld_field_point_video,ld_field_project,ld_field_project_option,ld_field_type,ld_research_projects,ld_research_qurvey,ld_data_sharing,ld_data_research_area,ld_data_research_direction");
            options.put("includedTables", "tr_catalogue");
            options.put("hasMgrTable", true);
            Msch.generator(templatePath, options, config);
            System.exit(0);
    }
}
