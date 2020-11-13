package com.soli.server.utils;

import com.sun.media.jai.codec.*;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.Operations;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;
import javax.media.jai.*;
import javax.media.jai.operator.TransposeDescriptor;
import java.awt.*;
import java.awt.image.*;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

public class TiffOP {


    //读取tiff文件到 ImageReader Object[0]：ImageReader ,Object[1]:FileImageInputStream
    private static Object[] readTiff(File tifFile) {
        ImageReader reader = null;
        FileImageInputStream fis = null;
        Object[] res = null;
        try {

            reader = ImageIO.getImageReadersByFormatName("tiff").next();
            fis = new FileImageInputStream(tifFile);
            reader.setInput(fis);
            res = new Object[]{reader, fis};
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }
        return res;
    }


    //读取tiff文件到 BufferedImage
    public static BufferedImage loadTiff(File tifFile, long[] dpiData) {

        ImageReader reader = null;
        FileImageInputStream fis = null;
        BufferedImage res = null;
        try {
            Object[] src = readTiff(tifFile);
            if (src == null) {
                return null;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];
            if (reader != null) {
                int numPages = reader.getNumImages(true);
                if (numPages > 0) {
                    long[] dpiArr = getTiffDPI(reader, 0);
                    dpiData[0] = dpiArr[0];
                    dpiData[1] = dpiArr[1];
                    res = reader.read(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.dispose();
            }
            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    /**
     * 从tiff文件生成原始像素大小tiff文件
     *
     * @param fTiff  源tiff文件
     * @param decDir tiff目标路径,目标文件将会以0001.tif,0002.tif...置于此路径下
     * @return true表示成功, false表示失败
     */
    public static boolean makeSingleTif(File fTiff, File decDir) {
        boolean bres = true;
        ImageReader reader = null;
        FileImageInputStream fis = null;
        try {

            reader = ImageIO.getImageReadersByFormatName("tiff").next();
            fis = new FileImageInputStream(fTiff);
            reader.setInput(fis);

            int numPages = reader.getNumImages(true);
            for (int i = 0; i < numPages; i++) {

                long[] dpiData = getTiffDPI(reader, i);
                BufferedImage bi = reader.read(i);

                File tif = new File(decDir.getPath() + File.separator
                        + String.format("%1$04d", i + 1) + ".tif");
                bres = createTiff(tif, new RenderedImage[]{bi}, dpiData, false);

            }

        } catch (Exception e) {
            e.printStackTrace();
            bres = false;

        } finally {

            if (reader != null) {
                reader.dispose();
            }

            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return bres;
    }

    /**
     * 合并多个Tiff文件成一个多页Tiff文件
     *
     * @param fTiff  目标tiff文件
     * @param tifDir 源tiff文件路径,tiff文件必须以0001.tif,0002.tif...置于此路径下
     * @return true表示成功, false表示失败
     */
    static public boolean mergeTiff(File fTiff, File tifDir) {

        boolean bres = true;
        ImageReader reader = null;
        FileImageInputStream fis = null;
        Vector<BufferedImage> biV = new Vector<BufferedImage>();
        File[] fs = tifDir.listFiles();
        long[] dpi = null;
        for (File f : fs) {
            String fileName = f.getName();
            if (!fileName.endsWith(".tif")) {
                continue;
            }
            try {
                reader = ImageIO.getImageReadersByFormatName("tiff").next();
                fis = new FileImageInputStream(f);
                reader.setInput(fis);
                biV.add(reader.read(0));
                if (dpi == null) {
                    dpi = getTiffDPI(reader, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.dispose();
                }

                if (fis != null) {
                    try {
                        fis.flush();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        RenderedImage[] biArr = new RenderedImage[biV.size()];
        for (int i = 0; i < biArr.length; i++) {
            biArr[i] = biV.get(i);
        }

        bres = createTiff(fTiff, biArr, dpi, false);


        return bres;
    }

    /**
     * 合并多个Tiff文件成一个多页Tiff文件
     *
     * @param fTiff  目标tiff文件
     * @param tif1   第一个tif
     * @param tif2   第二个tif
     * @return true表示成功, false表示失败
     */
    static public boolean mergeTiff(File fTiff, File tif1, File tif2) {
        boolean bres = true;
        ImageReader reader = null;
        FileImageInputStream fis = null;
        Vector<BufferedImage> biV = new Vector<BufferedImage>();
        File[] fs = {tif1, tif2};
        long[] dpi = null;
        for (File f : fs) {
            String fileName = f.getName();
            if (!fileName.endsWith(".tif")) {
                continue;
            }
            try {
                reader = ImageIO.getImageReadersByFormatName("tiff").next();
                fis = new FileImageInputStream(f);
                reader.setInput(fis);
                biV.add(reader.read(0));
                if (dpi == null) {
                    dpi = getTiffDPI(reader, 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.dispose();
                }

                if (fis != null) {
                    try {
                        fis.flush();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        RenderedImage[] biArr = new RenderedImage[biV.size()];
        for (int i = 0; i < biArr.length; i++) {
            biArr[i] = biV.get(i);
        }
        bres = createTiff(fTiff, biArr, dpi, false);
        return bres;
    }

    //获取tiff文件的页数
    public static int getTiffPages(File tifFile) {
        ImageReader reader = null;
        FileImageInputStream fis = null;
        int numpages = 0;
        try {
            Object[] src = readTiff(tifFile);
            if (src == null) {
                return 0;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];
            if (reader != null) {
                numpages = reader.getNumImages(true);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (reader != null) {
                reader.dispose();
            }

            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return numpages;
    }

    //获取tiff dpi
    public static long[] getTiffDPI(ImageReader reader, int index) {
        long[] dpi = new long[2];

        IIOMetadata meta = null;
        try {
            meta = reader.getImageMetadata(index);
            org.w3c.dom.Node n = meta.getAsTree("javax_imageio_1.0");
            n = n.getFirstChild();

            while (n != null) {

                if (n.getNodeName().equals("Dimension")) {

                    org.w3c.dom.Node n2 = n.getFirstChild();

                    while (n2 != null) {

                        if (n2.getNodeName().equals("HorizontalPixelSize")) {

                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();

                            org.w3c.dom.Node n3 = nnm.item(0);

                            float hps = Float.parseFloat(n3.getNodeValue());

                            dpi[0] = Math.round(25.4f / hps);

                        }

                        if (n2.getNodeName().equals("VerticalPixelSize")) {

                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();

                            org.w3c.dom.Node n3 = nnm.item(0);

                            float vps = Float.parseFloat(n3.getNodeValue());

                            dpi[1] = Math.round(25.4f / vps);
                        }

                        n2 = n2.getNextSibling();

                    }

                }

                n = n.getNextSibling();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dpi;
    }

    //获取tiff dpi
    public static long[] getTiffDPI(File tifFile, int index) {
        long[] dpi = new long[2];
        ImageReader reader = null;
        IIOMetadata meta = null;

        try {
            Object[] src = readTiff(tifFile);
            if (src == null) {
                return null;
            }
            reader = (ImageReader) src[0];
            meta = reader.getImageMetadata(index);
            org.w3c.dom.Node n = meta.getAsTree("javax_imageio_1.0");
            n = n.getFirstChild();

            while (n != null) {

                if (n.getNodeName().equals("Dimension")) {

                    org.w3c.dom.Node n2 = n.getFirstChild();

                    while (n2 != null) {

                        if (n2.getNodeName().equals("HorizontalPixelSize")) {

                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();

                            org.w3c.dom.Node n3 = nnm.item(0);

                            float hps = Float.parseFloat(n3.getNodeValue());

                            dpi[0] = Math.round(25.4f / hps);

                        }

                        if (n2.getNodeName().equals("VerticalPixelSize")) {

                            org.w3c.dom.NamedNodeMap nnm = n2.getAttributes();

                            org.w3c.dom.Node n3 = nnm.item(0);

                            float vps = Float.parseFloat(n3.getNodeValue());

                            dpi[1] = Math.round(25.4f / vps);
                        }

                        n2 = n2.getNextSibling();

                    }

                }

                n = n.getNextSibling();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dpi;
    }


    /**
     * 生成tiff原始dpi的png
     *
     * @param fTiff   用于生成文件的tif文件
     * @param decFile 目标png
     * @param tifIdx  tif文件页码索引，从0开始
     */
    public static void makePrintPngFromTifByScal(File fTiff, File decFile, int tifIdx) {


        long d = System.currentTimeMillis();
        FileOutputStream os = null;
        ImageReader reader = null;

        FileImageInputStream fis = null;
        try {

            Object[] src = readTiff(fTiff);
            if (src == null) {
                return;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];


            if (reader != null) {
                int numPages = reader.getNumImages(true);
                if (numPages > 0 && tifIdx < numPages) {
                    long[] dpiData = getTiffDPI(reader, tifIdx);
                    BufferedImage img = reader.read(tifIdx);


                    double meter2inchRatio = 1d / 0.0254d;
                    int dimX = (int) (dpiData[0] * meter2inchRatio) + 1;
                    int dimY = (int) (dpiData[0] * meter2inchRatio) + 1;

                    int iYScal = (Math.abs(dpiData[0] - dpiData[1]) <= 10) ? 1 : 2;

                    int width = img.getWidth();
                    int height = img.getHeight() * iYScal;


                    //200 * 210 /25.4 ---- 200 * 297 /25.4

                    double whScal = 1728.0 / 2338.0;
                    double realScal = (double) width / (double) height;


                    int drawPtX = 0;    //图像起始x位置
                    int drawPtY = 0;    //图像起始Y位置
                    int newW = 1728;
                    int newH = 2338;


                    if (realScal < whScal) {    // 以高度为缩放基准
                        newW = (int) (2338.0 * realScal);
                        drawPtX = (1728 - newW) / 2;
                    } else {
                        newH = (int) (1728.0 / realScal);
                        drawPtY = (2338 - newH) / 2;
                    }

//System.out.println("width=" + width + ", height=" + height + ", newW=" + newW + ", newH=" + newH + ", dimX=" + dimX + ", dimY=" + dimY);
                    BufferedImage decImg = new BufferedImage(1728, 2338, BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D g2 = decImg.createGraphics();
                    g2.setColor(Color.white);
                    g2.fillRect(0, 0, 1728, 2338);

                    g2.drawImage(img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH), drawPtX, drawPtY, null);


                    PNGEncodeParam paramEnc = PNGEncodeParam.getDefaultEncodeParam(decImg);
                    paramEnc.setPhysicalDimension(dimX, dimY, 1);
                    os = new FileOutputStream(decFile);
                    JAI.create("encode", decImg, os, "PNG", paramEnc);
                    os.flush();
                    os.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (reader != null) {
                reader.dispose();
            }
            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {

                    os.flush();
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        System.out.println(("----make print Pngs time----" + (System.currentTimeMillis() - d) + "ms"));

    }


    /**
     * 从tiff文件生成用于显示的一页png
     *
     * @param fTiff   源tiff文件
     * @param pageIdx 页号,从0开始
     * @param dDecPng 目标png文件
     * @return true表示成功, false表示失败
     */
    static public boolean makeDispPage(File fTiff, int pageIdx, File dDecPng) {
        long d = System.currentTimeMillis();

        ImageReader reader = null;
        FileImageInputStream fis = null;
        boolean bres = false;
        try {
            Object[] src = readTiff(fTiff);
            if (src == null) {
                return false;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];
            if (reader != null) {
                int numPages = reader.getNumImages(true);
                if (numPages > 0) {
                    if (pageIdx >= 0 && pageIdx < numPages) {
                        long[] dpi = getTiffDPI(reader, pageIdx);
                        if (dpi != null && dpi.length == 2) {
                            if (dpi[0] <= 0)
                                dpi[0] = 200;
                            if (dpi[1] <= 0)
                                dpi[1] = 200;
                            double dXFac = 96.0 / dpi[0];
                            double dYFac = 96.0 / dpi[1];
                            BufferedImage img = reader.read(pageIdx);
                            RenderedImage decImg = ScaleImg(img, dXFac, dYFac);
                            ImageIO.write(decImg, "png", dDecPng);
                            bres = true;

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (reader != null) {
                reader.dispose();
            }

            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        System.out.println(("----make disp png by IO use time:" + (System.currentTimeMillis() - d) + "ms"));
        return bres;
    }


    /**
     * 从tiff生成缩略图
     *
     * @param fTiff
     * @param decDir
     * @return
     */
    public static boolean makeThumbsFromTiff(File fTiff, File decDir) {
        long d = System.currentTimeMillis();
        int numPages = 0;
        int thumbWidth = 188;
        int thumbHeight = 254;

        ImageReader reader = null;
        FileImageInputStream fis = null;
        boolean bres = true;
        try {
            Object[] src = readTiff(fTiff);
            if (src == null) {
                return false;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];

            if (reader != null) {
                numPages = reader.getNumImages(true);
                if (numPages > 0) {
                    for (int i = 0; i < numPages; i++) {
                        long[] dpiData = getTiffDPI(reader, i);
                        BufferedImage img = reader.read(i);
                        int ht = img.getHeight();
                        int wid = img.getWidth();
                        float fsc = (float) dpiData[0] / (float) dpiData[1];
                        if (Math.abs(fsc - 1.0) > 0.2) {
                            if (fsc < 1) {
                                wid = (int) (wid / fsc);
                            } else {
                                ht = (int) (ht * fsc);
                            }
                        }

                        double realScal = (double) wid / (double) ht;
                        double whScal = (double) thumbWidth / (double) thumbHeight;

                        int newW = thumbWidth;
                        int newH = thumbHeight;

                        if (realScal < whScal) {    // 以高度为缩放基准
                            newW = (int) (thumbHeight * realScal);
                        } else {
                            newH = (int) (thumbWidth / realScal);
                        }
                        int x = (thumbWidth - newW) / 2;
                        int y = (thumbHeight - newH) / 2;

                        System.out.println(newW + "," + newH + "," + x + "," + y);
                        BufferedImage decImg = new BufferedImage(thumbWidth,
                                thumbHeight, BufferedImage.TYPE_3BYTE_BGR);
                        Graphics2D g2 = (Graphics2D) decImg.createGraphics();
                        g2.setColor(new Color(160, 160, 160));
                        g2.fillRect(0, 0, thumbWidth, thumbHeight);
                        g2.drawImage(img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH),
                                x, y, null);

                        ImageIO.write(decImg, "png", decDir);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            bres = false;
        } finally {

            if (reader != null) {
                reader.dispose();
            }
            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(("----make Thumbs time----" + (System.currentTimeMillis() - d) + "ms") + "-----make pages---" + numPages);
        return bres;
    }


    /**
     * 旋转Tiff文件
     *
     * @param fTiff 要旋转的tiff文件
     * @param angle 旋转角度: 90,180,270
     * @return true表示成功, false表示失败
     */
    static public boolean RotateTiff(File fTiff, double angle) {
        FileOutputStream out = null;
        File fTiffTmp = new File(fTiff.getPath() + ".tmpABCD.tmp");
//		File fTiffTmp = new File("c:\\io.tif");
        long d = System.currentTimeMillis();
        ImageReader reader = null;
        FileImageInputStream fis = null;

        boolean bres = false;
        try {
            Object[] src = readTiff(fTiff);
            if (src == null) {
                return false;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];
            if (reader != null) {
                int numPages = reader.getNumImages(true);
                if (numPages > 0) {
                    long[] dpiData = getTiffDPI(reader, 0);
                    BufferedImage bi = reader.read(0);
                    RenderedImage dec1 = RotateImg(bi, angle);
                    bres = createTiff(fTiffTmp, new RenderedImage[]{dec1}, dpiData, false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (reader != null) {
            reader.dispose();
        }
        if (fis != null) {
            try {
                fis.flush();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bres && fTiffTmp.exists()) {
            fTiff.delete();
            fTiffTmp.renameTo(fTiff);
        }
        System.out.println(("----rotate tiff by IO use time:" + (System.currentTimeMillis() - d) + "ms"));
        return bres;
    }


    /**
     * 在Tiff图像上进行盖章操作
     *
     * @param fTiff 单页tiff文件
     * @param stamp 图章tiff文件
     * @param pt    盖章位置
     * @return true表示成功, false表示失败
     */
    static public boolean DrawStamp(File fTiff, File stamp, Point pt) {


        ImageReader reader = null;
        FileImageInputStream fis = null;
        boolean bRes = false;
        try {
            Object[] src = readTiff(fTiff);
            if (src == null) {
                return false;
            }
            reader = (ImageReader) src[0];
            fis = (FileImageInputStream) src[1];
            if (reader != null) {
                int numPages = reader.getNumImages(true);

                if (numPages > 0) {
                    long[] dpiDataSrc = getTiffDPI(reader, 0);

                    BufferedImage imageDec = reader.read(0);

                    long[] dpiData = new long[]{200, 200};
                    BufferedImage img = loadTiff(stamp, dpiData);
                    if (img == null)
                        return false;

                    if (java.lang.Math.abs(dpiData[0] - dpiDataSrc[0]) > 10 ||
                            java.lang.Math.abs(dpiData[1] - dpiDataSrc[1]) > 10) {
                        double factorX = (double) dpiDataSrc[0] / (double) dpiData[0];
                        double factorY = (double) dpiDataSrc[1] / (double) dpiData[1];
                        img = convertRenderedImage(ScaleImg(img, factorX, factorY));
                    }


                    imageDec = DoStamp(imageDec, img, pt);

                    bRes = createTiff(fTiff, new RenderedImage[]{imageDec}, dpiDataSrc, false);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.dispose();
            }
            if (fis != null) {
                try {
                    fis.flush();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bRes;
    }


    private static BufferedImage convertRenderedImage(RenderedImage img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        ColorModel cm = img.getColorModel();

        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);

        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable<String, Object> properties = new Hashtable<String, Object>();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                try {
                    properties.put(keys[i], img.getProperty(keys[i]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        BufferedImage result = new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
        img.copyData(raster);


        return result;
    }

    private static RenderedImage RGBToBilevel(RenderedImage srcImg,
                                              boolean isErrorDiffusion) {
        // Load the ParameterBlock for the dithering operation
        // and set the operation name.
        RenderedImage res = null;
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(srcImg);
        String opName = null;
        if (isErrorDiffusion) {
            opName = "errordiffusion";
            LookupTableJAI lut = new LookupTableJAI(new byte[][]{
                    {(byte) 0x00, (byte) 0xff}, {(byte) 0x00, (byte) 0xff},
                    {(byte) 0x00, (byte) 0xff}});
            pb.add(lut);
            pb.add(KernelJAI.ERROR_FILTER_FLOYD_STEINBERG);
        } else {
            opName = "ordereddither";
            ColorCube cube = ColorCube.createColorCube(DataBuffer.TYPE_BYTE, 0,
                    new int[]{2, 2, 2});
            pb.add(cube);
            pb.add(KernelJAI.DITHER_MASK_443);
        }
        // Create a layout containing an IndexColorModel which maps
        // zero to zero and unity to 255; force SampleModel to be bilevel.
        ImageLayout layout = new ImageLayout();
        byte[] map = new byte[]{(byte) 0x00, (byte) 0xff};
        ColorModel cm = new IndexColorModel(1, 2, map, map, map);
        layout.setColorModel(cm);
        SampleModel sm = new MultiPixelPackedSampleModel(DataBuffer.TYPE_BYTE,
                srcImg.getWidth(), srcImg.getHeight(), 1);
        layout.setSampleModel(sm);
        // Create a hint containing the layout.
        RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);
        // Dither the image.
        res = JAI.create(opName, pb, hints);
        return res;

    }

    private static RenderedImage RotateImg(RenderedImage img, double angle) {
        ParameterBlock params = new ParameterBlock();
        params.addSource(img);
        if (angle == 90.0) {
            params.add(TransposeDescriptor.ROTATE_90);
            return JAI.create("Transpose", params, null);
        } else if (angle == 180.0) {
            params.add(TransposeDescriptor.ROTATE_180);
            return JAI.create("Transpose", params, null);
        } else if (angle == 270.0) {
            params.add(TransposeDescriptor.ROTATE_270);
            return JAI.create("Transpose", params, null);
        }
        Interpolation interp = Interpolation
                .getInstance(Interpolation.INTERP_BILINEAR);
        params.add(0.0F);
        params.add(0.0F);
        params.add((float) java.lang.Math.toRadians(angle));
        params.add(interp);
        return JAI.create("rotate", params, null);
    }


    private static RenderedImage ScaleImg(RenderedImage img, double factorX, double factorY) {
        BufferedImage imgB = convertRenderedImage(img);
        int iType = imgB.getType();
        if (iType == BufferedImage.TYPE_BYTE_BINARY || iType == BufferedImage.TYPE_CUSTOM
                || iType == BufferedImage.TYPE_BYTE_INDEXED)
            iType = BufferedImage.TYPE_3BYTE_BGR;
        double newWidth = img.getWidth() * factorX;
        double newHeight = img.getHeight() * factorY;
        BufferedImage bufferedImage = new BufferedImage((int) newWidth, (int) newHeight, iType);
        bufferedImage.getGraphics().drawImage(
                imgB.getScaledInstance((int) newWidth, (int) newHeight,
                        Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

    private static RenderedImage DoAndImg(RenderedImage img1, RenderedImage img2) {
        ParameterBlock params = new ParameterBlock();
        params.addSource(img1);
        params.addSource(img2);
        return JAI.create("And", params, null);

    }

    private static RenderedImage DoOrImg(RenderedImage img1, RenderedImage img2) {
        ParameterBlock params = new ParameterBlock();
        params.addSource(img1);
        params.addSource(img2);
        return JAI.create("Or", params, null);

    }


    private static BufferedImage DoStamp(BufferedImage image1,
                                         BufferedImage image2, Point pt) throws Exception {


        int imgTp1 = image1.getType();
        int imgTp2 = image2.getType();
        System.out.println("imgTp1=" + imgTp1 + ", imgTp2=" + imgTp2 +
                ", BufferedImage.TYPE_BYTE_BINARY=" + BufferedImage.TYPE_BYTE_BINARY);
        if (BufferedImage.TYPE_BYTE_BINARY == imgTp1
                && imgTp2 != BufferedImage.TYPE_BYTE_BINARY) {

            BufferedImage tmpImg = new BufferedImage(image1.getWidth(),
                    image1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            tmpImg.createGraphics().drawImage(image1, 0, 0, null);
            image1 = tmpImg;
        }
        if (BufferedImage.TYPE_BYTE_BINARY == imgTp2
                && imgTp1 != BufferedImage.TYPE_BYTE_BINARY) {

            BufferedImage tmpImg = new BufferedImage(image2.getWidth(),
                    image2.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            tmpImg.createGraphics().drawImage(image2, 0, 0, null);
            image2 = tmpImg;
        }

//		BufferedImage TmpImg2 = new BufferedImage(image2.getWidth(),
//				image2.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
//		TmpImg2.createGraphics().drawImage(image1, 0, 0, image2.getWidth(),
//				image2.getHeight(), pt.x, pt.y, pt.x + image2.getWidth(),
//				pt.y + image2.getHeight(), null);
//		RenderedImage res = DoAndImg(TmpImg2, image2);

        Graphics2D g2 = image1.createGraphics();
        g2.drawImage(image2, pt.x, pt.y, null);

        return image1;


    }


    private static boolean createTiff(File fTiff, RenderedImage[] images, long[] dpiData, boolean bFax) {
        if (dpiData == null || dpiData.length != 2 || fTiff == null || images == null || images.length == 0)
            return false;

        RenderedImage img1 = null;
        FileOutputStream out = null;
        boolean bres = false;
        try {
            int iCompress = TIFFEncodeParam.COMPRESSION_GROUP4;
            for (int i = 0; i < images.length; ++i) {
                if (images[i].getColorModel().getPixelSize() > 1) {
                    if (bFax) {
                        images[i] = RGBToBilevel(images[i], true);
                    } else {
                        iCompress = TIFFEncodeParam.COMPRESSION_PACKBITS;
                        break;
                    }
                }
            }
            img1 = images[0];
            out = new FileOutputStream(fTiff);
            TIFFEncodeParam paramEnc = new TIFFEncodeParam();
            paramEnc.setCompression(iCompress);
            ImageEncoder encoder = ImageCodec.createImageEncoder("tiff", out, paramEnc);
            Vector<RenderedImage> lastImgs = new Vector<RenderedImage>();
            if (images.length > 1) {
                for (int i = 1; i < images.length; ++i)
                    lastImgs.add(images[i]);
                paramEnc.setExtraImages(lastImgs.iterator());
            }
            if (dpiData[0] > 0 || dpiData[1] > 0) {
                int fieldCount = 1;
                if (dpiData[0] > 0 && dpiData[1] > 0) {
                    fieldCount = 2;
                }
                fieldCount += 1;
                TIFFField[] tiffFields = new TIFFField[fieldCount];
                int index = 0;
                if (dpiData[0] > 0) {
                    long xdpiArray[][] = new long[1][2];
                    xdpiArray[0] = new long[]{dpiData[0], 1};
                    TIFFField xRes = new TIFFField(
                            TIFFImageDecoder.TIFF_X_RESOLUTION,
                            TIFFField.TIFF_RATIONAL, 1, xdpiArray);
                    tiffFields[index++] = xRes;
                }
                if (dpiData[1] > 0) {
                    long ydpiArray[][] = new long[1][2];
                    ydpiArray[0] = new long[]{dpiData[1], 1};
                    TIFFField yRes = new TIFFField(
                            TIFFImageDecoder.TIFF_Y_RESOLUTION,
                            TIFFField.TIFF_RATIONAL, 1, ydpiArray);
                    tiffFields[index++] = yRes;
                }
                TIFFField fSoft = new TIFFField(0x0131, TIFFField.TIFF_ASCII,
                        1, new String[]{"bena create"});
                tiffFields[index++] = fSoft;
                paramEnc.setExtraFields(tiffFields);
            }
            encoder.encode(img1);
            bres = true;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bres;
    }
}