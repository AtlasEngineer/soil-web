package com.atlas.test;


import com.jfinal.kit.PathKit;
import com.lambkit.common.util.PathUtils;
import com.lambkit.common.util.SecurityUtils;
import com.soli.server.utils.ZipUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class DbPasswordEncode {

    public static String demo(String filepath) {
        System.out.println(filepath);
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        File file = new File(rootPath + filepath);
        System.out.println("file:" + file.getAbsolutePath());
        System.out.println(file.getName());
        if (file == null) {
            System.out.println("找不到该文件");
        }
        String name = file.getName().split("\\.")[0];
        System.out.println(name + "-----------------name");
        //解压后文件夹
        String s = rootPath + "/d/" + name;
        System.out.println("file.getPath():" + file.getPath());
        try {
            ZipUtils.decompress(file.getPath(), s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("s" + s);
        File files = new File(s);
        System.out.println("aaaaaaaaaaaaaaaaaaaaa:" + files.getPath());
        File fileS = null;
        String[] fileList = files.list();
        for (String y : fileList) {
            File file2 = new File(s + File.separator + y);
            if (file2.isDirectory()) {
                s = s + File.separator + name;
                System.out.println("新的s为路径:" + s);
                fileS = new File(s);
            }
        }
        if(fileS!=null){ //如果他是文件夹的话
             fileList = fileS.list();
        }
        List<String> list = new ArrayList<>();
        int i = 0;
        for (String y : fileList) {
            File file1 = new File(s + File.separator + y);
            System.out.println("file1:" + file1.getName());
            String fileext = PathUtils.getExtensionName(file1.getName());
            if (file1.isFile()) {
                String s1 = file1.getName().split("\\.")[1];
                String s2 = file1.getName().split("\\.")[0];
                File file01 = new File(s + File.separator + s2 + "." + s1);
                System.out.println("file01:" + file01.getName());
                file1.renameTo(file01);
                String path = file01.getPath();
                System.out.println("path" + path);
                list.add(file01.getPath());
                i++;
            }
        }
        System.out.println("成功的i:" + i);
        String newZip = rootPath + "/d/" + name + ".zip";
//        ZipUtils.compress(s, newZip);
        try {
            ZipUtils.compress(list, newZip, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newZip;
    }public static boolean deleteFile(String sPath) {

        boolean flag = false;

        File file = new File(sPath);

        // 路径为文件且不为空则进行删除

        if (file.isFile() && file.exists()) {

            file.delete();

            flag = true;

        }

        return flag;

    }


    public static void decompress(String zipFile, String dstPath) throws IOException {
        File pathFile = new File(dstPath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = null;
            OutputStream out = null;
            try {
                in = zip.getInputStream(entry);
                String outPath = (dstPath + "/" + zipEntryName).replaceAll("\\*", "/");
                ;
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }

                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }

                if (null != out) {
                    out.close();
                }
            }
        }
        zip.close();
    }

    private static int mb = 1048576;// 1MB



    public static void zoom(String oldFile,long length,double newRate) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00");// 设置保留位数
        double rate=0.8;
        //log.info("原始图片大小：" + df.format((float) length / mb) + "MB");
        File e=new File(oldFile);
        String fileext = PathUtils.getExtensionName(new File(oldFile).getName());
        String rootPath = PathKit.getWebRootPath().replace("\\", "/");
        String filename = UUID.randomUUID().toString() + "." + fileext;
        System.out.println("rootPath + filename"+rootPath + filename);
        File f=new File(rootPath + filename);
        boolean b = e.renameTo(new File(rootPath + filename));
        if(b){
            long newfile = new File(oldFile).length();
            int i = 1;

            // 如果首次压缩还大于2MB则继续处理
            while ((float) newfile / mb >= newRate) {
                //log.info("压缩后图片大小：" + newfile);
                rate = rate - 0.05;// 暂定按照0.03频率压缩
                //log.info(i + " rate=" + rate);
                BufferedImage srcImage = ImageIO.read(new File(oldFile));
                int WIDTH = (int) (srcImage.getWidth() * rate);
                int HEIGHT = (int) (srcImage.getHeight() * rate);
                BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics();
                g.drawImage(srcImage, 0, 0, WIDTH, HEIGHT, null);
                // 缩小
                ImageIO.write(image, fileext, f);
                i++;
                newfile = f.length();
                //log.info("压缩次数：" + i);
            }
            // 调整方向
            System.out.println("zoomFile"+f);
            BufferedImage newImage = ImageIO.read(f);
            BufferedImage image1 = Rotate(newImage, 90);// 顺时针旋转90度
            ImageIO.write(image1,fileext, f);
        }else {
            System.out.println("失败");
        }

    }
    public static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }



    public static void getAllFileName(String path,ArrayList<String> fileNameList) {
        //ArrayList<String> files = new ArrayList<String>();
        boolean flag = false;
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
             System.out.println("文     件：" + tempList[i]);
                //fileNameList.add(tempList[i].toString());
                fileNameList.add(tempList[i].getName());
            }
            System.out.println(fileNameList.size());
            if (tempList[i].isDirectory()) {
              System.out.println("文件夹：" + tempList[i]);
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
    }


    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                }

            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
		String[] keys = {
                "", "y3Szd1R%zzO3RtkH", "sdgkii3&#fs"
        };
        System.out.println("key | AESEncode | AESDecode");
        for (String key : keys) {
            System.out.print(key + " | ");
            String encryptString = SecurityUtils.encodePassword(key, "pg");
            System.out.print(encryptString + " | ");
            String decryptString = SecurityUtils.decodePassword(encryptString, "pg");
            System.out.println(decryptString);
        }





    }
}
