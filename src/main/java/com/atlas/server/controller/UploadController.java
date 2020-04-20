package com.atlas.server.controller;


import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.lambkit.common.util.PathUtils;
import com.lambkit.plugin.jwt.JwtTokenInterceptor;
import com.lambkit.web.controller.LambkitController;

import java.io.File;
import java.util.UUID;

@Clear(JwtTokenInterceptor.class)
public class UploadController extends LambkitController {


    /**
     * @Description: 上传压缩文件
     * @Author: yangxueyang
     * @Date: 2019/8/28
     */
    public void uploadZip(){
        UploadFile uf = getFile();
        File file = uf.getFile();
        String rootPath = PathKit.getWebRootPath() + "/upload/zip/";
        String filename = UUID.randomUUID().toString() + ".zip";
        String fileext = PathUtils.getExtensionName(file.getName());
        if(!"zip".equals(fileext))
        {
            setAttr("msg", "文件格式不正确");
            setAttr("error", "true");
            file.delete();
        }else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                setAttr("msg", "重命名失败");
                setAttr("error", "true");
            } else {
                System.out.println(filename);
                setAttr("filename",file.getName());
                setAttr("url", "/upload/zip/" + filename);
                setAttr("error", "false");
            }
        }
        renderJson();
    };








}
