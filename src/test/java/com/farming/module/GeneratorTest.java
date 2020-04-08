package com.farming.module;

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
            config.setOutRootDir("F:/atlas/Atlas_ainong/src/main/java");
            //生成java代码的包地址
            config.setBasepackage("com.atlas.server");
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
            Map<String,Object> options = Maps.newHashMap();
            //需要去掉的前缀
            options.put("tableRemovePrefixes", "at_");
            //仅包含如下表格
            options.put("includedTables", "at_species_pests,at_pests_sample,at_insect_species,at_insect_pests,at_insect_type,catalogue,catalogue_keep,catalogue_sample,at_botany_type");
            options.put("hasMgrTable", true);
            Msch.generator(templatePath, options, config);
            System.exit(0);
    }
}
