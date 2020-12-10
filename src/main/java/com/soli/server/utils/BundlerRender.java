package com.soli.server.utils;

import com.jfinal.log.Log;
import com.jfinal.render.Render;

import javax.servlet.ServletOutputStream;

import java.io.IOException;

public class BundlerRender extends Render {

    private static Log log = Log.getLog(BundlerRender.class);

    private byte[] bytes;

    public BundlerRender(byte[] bytes) {
        this.bytes=bytes;
    }

    @Override
    public void render() {
        ServletOutputStream sos = null;
        try {
            //设置头信息,内容处理的方式,attachment以附件的形式打开,就是进行下载,并设置下载文件的命名
            //response.setHeader("Content-Disposition","attachment;filename="+file.getName());
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/png");
            sos = response.getOutputStream();
            sos.write(bytes);
        } catch (Exception e) {
            log.error("图片render出错:" + e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (sos != null) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }




}
