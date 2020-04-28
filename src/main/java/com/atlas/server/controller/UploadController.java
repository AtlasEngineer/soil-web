package com.atlas.server.controller;


import com.atlas.server.utils.Co;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.lambkit.common.util.PathUtils;
import com.lambkit.component.swagger.annotation.ApiOperation;
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
    }
    /**
     * @return void
     * @Author queer
     * @Description //TODO 上传识别图片(未审核)
     * @Date 9:30 2019/11/21
     * @Param [image-file]
     **/
    @ApiOperation(url = "/update/updateImg", tag = "/upldate", httpMethod = "post", description = "图片上传")
    public void updateImg() {
        //getFile一定放在第一个参数去获取，否则都获取不到参数
        UploadFile uf = getFile("file", "image");
        File file = uf.getFile();
        System.out.println("上传时文件名：" + file.getName());
        String rootPath = PathKit.getWebRootPath() + "/upload/question/";
        String fileext = PathUtils.getExtensionName(file.getName());
        String filename = UUID.randomUUID().toString() + "." + fileext;
        if (!"jpg".equals(fileext) && !"png".equals(fileext) && !"gif".equals(fileext) && !"jpeg".equals(fileext)) {
            renderJson(Co.ok("data", Ret.fail("errorMsg", "图片格式不正确")));
            file.delete();
            return;
        } else {
            boolean b = file.renameTo(new File(rootPath + filename));
            if (!b) {
                renderJson(Co.ok("data", Ret.fail("errorMsg", "重命名失败")));
                return;
            } else {
                renderJson(Co.ok("data", Ret.ok("url", "/upload/question/" + filename).set("yname", file.getName())));
                return;
            }
        }
    }








}