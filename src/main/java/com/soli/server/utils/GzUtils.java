package com.soli.server.utils;

import com.alibaba.excel.util.StringUtils;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * @author: xiaobaitu88
 * @Date: 2019/10/23 21:19
 * @Description:
 */
public class GzUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(GzUtils.class);

    public static void main(String[] args) {
        deCompressGZipFile("D:\\hello2.tar.gz", "D:\\ssl");
    }

    /**
     * Tar文件解压方法
     *
     * @param tarGzFile 要解压的压缩文件名称（绝对路径名称）
     * @param destDir   解压后文件放置的路径名（绝对路径名称）当路径不存在，会自动创建
     * @return 解压出的文件列表
     */
    public static void deCompressGZipFile(String tarGzFile, String destDir) {

        // 建立输出流，用于将从压缩文件中读出的文件流写入到磁盘
        TarArchiveEntry entry = null;
        TarArchiveEntry[] subEntries = null;
        File subEntryFile = null;
        try (FileInputStream fis = new FileInputStream(tarGzFile);
             GZIPInputStream gis = new GZIPInputStream(fis);
             TarArchiveInputStream taris = new TarArchiveInputStream(gis);) {
            while ((entry = taris.getNextTarEntry()) != null) {
                StringBuilder entryFileName = new StringBuilder();
                entryFileName.append(destDir).append(File.separator).append(entry.getName());
                File entryFile = new File(entryFileName.toString());
                if (entry.isDirectory()) {
                    if (!entryFile.exists()) {
                        entryFile.mkdir();
                    }
                    subEntries = entry.getDirectoryEntries();
                    for (int i = 0; i < subEntries.length; i++) {
                        try (OutputStream out = new FileOutputStream(subEntryFile)) {
                            subEntryFile = new File(entryFileName + File.separator + subEntries[i].getName());
                            IOUtils.copy(taris, out);
                        } catch (Exception e) {
                            LOGGER.error("deCompressing file failed:" + subEntries[i].getName() + "in" + tarGzFile);
                        }
                    }
                } else {
                    checkFileExists(entryFile);
                    OutputStream out = new FileOutputStream(entryFile);
                    IOUtils.copy(taris, out);
                    out.close();
                    //如果是gz文件进行递归解压
                    if (entryFile.getName().endsWith(".gz")) {
                        deCompressGZipFile(entryFile.getPath(), destDir);
                    }
                }
            }
            //如果需要刪除之前解压的gz文件，在这里进行

        } catch (Exception e) {
            LOGGER.warn("decompress failed", e);
        }
    }

    public static void checkFileExists(File file) {
        //判断是否是目录
        if (file.isDirectory()) {
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            //判断父目录是否存在，如果不存在，则创建
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void readTarFile(File tarFile) {
        String orginFileName = tarFile.getName();
        try {
            ArchiveInputStream archiveInputStream = null;
            if (StringUtils.endsWithIgnoreCase(tarFile.getName(), ".gz")) {
                archiveInputStream = new ArchiveStreamFactory()
                        .createArchiveInputStream("tar", new GZIPInputStream(new BufferedInputStream(new FileInputStream(tarFile))));
            } else {
                archiveInputStream = new ArchiveStreamFactory()
                        .createArchiveInputStream("tar", new BufferedInputStream(new FileInputStream(tarFile)));
            }
            TarArchiveEntry entry = null;
            String name = orginFileName.substring(0, orginFileName.indexOf(".tar.gz"));
            while ((entry = (TarArchiveEntry) archiveInputStream.getNextEntry()) != null) {
                if (entry.getSize() > 0) {
                    String xmlfileName = name+ ".xml";
                    String jpgfileName = name + ".jpg";
                    if (xmlfileName.equalsIgnoreCase(entry.getName())) {
                        byte[] bytes = getContent(archiveInputStream);
                        getFile(bytes, tarFile.getParentFile().getAbsolutePath()+"/"+name, xmlfileName);
                    }
                    if (jpgfileName.equalsIgnoreCase(entry.getName())) {
                        byte[] bytes = getContent(archiveInputStream);
                        getFile(bytes, tarFile.getParentFile().getAbsolutePath()+"/"+name, jpgfileName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) { //判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "/" + fileName);
            System.out.println(file.getAbsolutePath());
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static byte[] getContent(File file) {
        try {
            return getContent(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    public static byte[] getContent(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            //byte[] buffer = new byte[16 * 1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                baos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}