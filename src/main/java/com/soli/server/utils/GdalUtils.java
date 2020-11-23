package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.lambkit.common.util.StringUtils;
import com.vividsolutions.jts.io.ParseException;
import org.gdal.gdal.*;
import org.gdal.gdal.Driver;
import org.gdal.gdalconst.gdalconst;
import org.gdal.gdalconst.gdalconstConstants;
import org.gdal.ogr.*;
import org.gdal.osr.SpatialReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class GdalUtils {

    static int bandNum = -1;
    static String gf1Path = "D:\\IdeaWorkSpace\\soil-web\\src\\main\\webapp\\upload\\crawlers_gf\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063.tiff";
    static int rCount = -1;
    static int dataType = -1;

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        if (gdal.GetDriverCount() < 1) gdal.AllRegister();
        String outPath = "D:\\";
        Dataset dsTIF = gdal.Open(gf1Path, gdalconstConstants.GA_ReadOnly);

        // 切割为 1000*1000的影像块
        // clipTIF(dsTIF, outPath, 1000, 1000); // 使用Band接口
        clipTIFOnDSReadWrite(dsTIF, outPath, 5000, 5000); // 使用Dataset接口
        System.out.println("耗时:" + (System.currentTimeMillis() - l) + "ms");
    }

    /**
     * 切割影像 使用Dataset接口读写影像
     *
     * @param dsTIF
     * @param outPath
     * @param clipWidth
     * @param clipHeight
     */
    public static void clipTIFOnDSReadWrite(Dataset dsTIF, String outPath, int clipWidth, int clipHeight) {
        Driver driver = gdal.GetDriverByName("GTIFF");
        int pWidth = clipWidth;
        int pHeight = clipHeight;
        // 宽、高、波段数
        rCount = dsTIF.getRasterCount();
        // 从波段中获取影像的数据类型，gdal中波段索引从1开始
        dataType = dsTIF.GetRasterBand(1).GetRasterDataType();

        // 六参数信息
        double[] geoTransform = dsTIF.GetGeoTransform();

        int iBlockCols = dsTIF.getRasterXSize() / clipWidth;
        int iBlockRows = dsTIF.getRasterYSize() / clipHeight;
        if (dsTIF.getRasterXSize() % clipWidth > 0)
            iBlockCols++;
        if (dsTIF.getRasterYSize() % clipHeight > 0)
            iBlockRows++;
        int iBlock = 1;
        double dCurrYcoor = geoTransform[3];
        for (int iCol = 0; iCol < iBlockCols; iCol++) {
            geoTransform[3] = dCurrYcoor;// 重置为左上角的坐标(Y坐标)，按列输出
            if (iCol > 0)
                geoTransform[0] = geoTransform[0] + (clipWidth * geoTransform[1] + clipWidth * geoTransform[2]);
            for (int iRow = 0; iRow < iBlockRows; iRow++) {
                // 计算裁剪后的左上角坐标，设定裁剪后影像块的坐标位置
                if (iRow > 0)
                    geoTransform[3] = geoTransform[3] + (clipHeight * geoTransform[4] + clipHeight * geoTransform[5]);

                if (dsTIF.getRasterXSize() % clipWidth > 0 && iCol == iBlockCols - 1) {
                    clipWidth = dsTIF.getRasterXSize() % pWidth;
//					System.out.println(clipWidth);
                } else {
                    clipWidth = pWidth;
//					System.out.println(clipWidth);
                }
                if (dsTIF.getRasterYSize() % clipHeight > 0 && iRow == iBlockRows - 1) {
                    clipHeight = dsTIF.getRasterYSize() % clipHeight;
                } else {
                    clipHeight = pHeight;
                }

                // 创建结果图像
                Dataset outputDs = driver.Create(outPath + iBlock++ + ".tif", clipWidth, clipHeight, rCount, dataType);
                outputDs.SetGeoTransform(geoTransform);
                outputDs.SetProjection(dsTIF.GetProjection());
                int[] bands = new int[] { 1, 2, 3 };

                // 按Dataset读取
                for (int i = 0; i < clipHeight; i++) {
                    byte[] cache = new byte[clipWidth * clipHeight];
                    dsTIF.ReadRaster(iCol * pWidth, iRow * pHeight + i, clipWidth, 1, clipHeight, 1, dataType, cache,
                            bands);
                    outputDs.WriteRaster(0, i, clipWidth, 1, clipHeight, 1, dataType, cache, bands);
                    outputDs.FlushCache();
                }
                outputDs.delete();
            }
        }

        // 释放资源
        dsTIF.delete();
    }

    public static void getPix(String gf1Path) {
        gdal.AllRegister();

        Dataset dataset = gdal.Open(gf1Path, gdalconstConstants.GA_ReadOnly);
        Vector<Object> objects = new Vector<>();
        WarpOptions warpOptions = new WarpOptions(objects);
        Dataset[] src_array = {dataset};

        File tiff = new File(gf1Path);
        String new_tiff = tiff.getParent() + "/warp.tif";
        Dataset hDataset = gdal.Warp(new_tiff, src_array, warpOptions);

        Driver hDriver = hDataset.GetDriver();
        System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
        double[] dGeoTrans = hDataset.GetGeoTransform();

        int[] maxRowCol = Projection2ImageRowCol(dGeoTrans, 116.944, 36.066);
        int max_x = (int) maxRowCol[0];
        int max_y = (int) maxRowCol[1];

        int bandSize = hDataset.GetRasterCount();
        for (int i = 1; i < bandSize; i++) {
            Band band = hDataset.GetRasterBand(i);//红
            int buf[] = new int[1];
            band.ReadRaster(max_x, max_y, 1, 1, buf);
            System.out.println(buf[0]);
        }
    }

    public static Layer getEnlovpe(String latlons){
        String strVectorFile ="D:\\PlaneShap001.shp";
        // 注册所有的驱动
        ogr.RegisterAll();
        // 为了支持中文路径，请添加下面这句代码
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8","NO");
        // 为了使属性表字段支持中文，请添加下面这句
        gdal.SetConfigOption("SHAPE_ENCODING","");

        //创建数据，这里以创建ESRI的shp文件为例
        String strDriverName = "ESRI Shapefile";
        org.gdal.ogr.Driver oDriver =ogr.GetDriverByName(strDriverName);

        // 创建数据源
        DataSource oDS = oDriver.CreateDataSource(strVectorFile,null);
        //创建田块layer
        Layer oLayer =oDS.CreateLayer("TestPolygon", null, ogr.wkbPolygon, null);

        // 下面创建属性表
        // 先创建一个叫FieldID的整型属性
        FieldDefn oFieldID = new FieldDefn("FieldID", ogr.OFTInteger);
        oLayer.CreateField(oFieldID, 0);

        FeatureDefn oDefn =oLayer.GetLayerDefn();
        Feature oFeatureTriangle = new Feature(oDefn);
        oFeatureTriangle.SetField(0, 0);
        Geometry geomTriangle = Geometry.CreateFromWkt("POLYGON (("+latlons+"))");
        oFeatureTriangle.SetGeometry(geomTriangle);
        oLayer.CreateFeature(oFeatureTriangle);
        oLayer.SyncToDisk();

        return oLayer;
    }

    public static void writerTiff(Layer layer){
        String strVectorFile = "D:\\test118.tiff";
//        String txtArrayFile = "C:/Users/Administrator/Desktop/WPI_F_WH_D_Z_Z_195017_195117_AL_2d_avg.txt";
        gdal.AllRegister();
        gdal.SetConfigOption("GDAL_FILENAME_IS_UTF8","YES");
        //gdal.SetConfigOption("SHAPE_ENCODING","CP936");

        SpatialReference ref = new SpatialReference();
        //只有这个投影支持输出geotiff OGC WKT   错误参数：WGS84
        ref.SetWellKnownGeogCS("WGS84");
        String[] defWkt = null;
        //ref.ExportToWkt();// 可配置String数组 defWkt
        //驱动名称
        String strDriverName = "GTiff";
        org.gdal.gdal.Driver oDriver = gdal.GetDriverByName(strDriverName);
        if (oDriver == null) {
            System.out.println(strDriverName+ " 驱动不可用！\n");
            return;
        }
        // @param数 (name, xsize, ysize, bands, java.lang.String[] options)
        Dataset dataset = oDriver.Create(strVectorFile, 45, 45, 1, gdalconst.GDT_Float32);
        // @param double[] argin  地理坐标 米转经纬度单位
        // 0:左上角x坐标 1:东西方向空间分辨率 2:x方向旋转角0° 3:左上角y坐标 4:y方向旋转角0° 5:南北方向空间分辨率
        double [] argin = {95, 1, 0, 45, 0, -1};
        dataset.SetGeoTransform(argin);
        // @param int xoff(偏移量), int yoff, int xsize, int ysize, int buf_type, byte[] array
//        Band band = dataset.GetRasterBand(1);// 波段  (色彩)
        // ReadRaster​(int xoff, int yoff, int xsize, int ysize, int buf_type, float[] array)
        // 参数说明： 起始x 起始y x数量(行) y数量(列) 数据类型 数组
//        band.WriteRaster(0, 0, 45, 45, gdalconst.GDT_Float32, arr);

        dataset.CopyLayer(layer,"testLayer");
        dataset.delete();
        oDriver.delete();
    }

    public static Kv getGF1Data(String latlons, String gf1Path) throws ParseException {
//        String gf1Path = "D:\\IdeaWorkSpace\\soil-web\\src\\main\\webapp\\upload\\crawlers_gf\\GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872\\GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872.tiff";
        List<Double> lons = new ArrayList<>();
        List<Double> lats = new ArrayList<>();
        String[] split = latlons.split(",");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            lons.add(Double.parseDouble(s.split(" ")[0]));
            lats.add(Double.parseDouble(s.split(" ")[1]));
        }
        Double min_lon = Collections.min(lons);
        Double max_lon = Collections.max(lons);
        Double min_lat = Collections.min(lats);
        Double max_lat = Collections.max(lats);

        gdal.AllRegister();

        Dataset dataset = gdal.Open(gf1Path, gdalconstConstants.GA_ReadOnly);
        Vector<Object> objects = new Vector<>();
        WarpOptions warpOptions = new WarpOptions(objects);
        Dataset[] src_array = {dataset};

        File tiff = new File(gf1Path);
        String new_tiff = tiff.getParent() + "/warp.tif";
        Dataset hDataset = gdal.Warp(new_tiff, src_array, warpOptions);
        if (StringUtils.isBlank(hDataset.GetProjectionRef())) {
            return Kv.by("errorMsg", "获取不到数据坐标系").set("code", 400);
        }

        if (hDataset == null) {
            System.err.println(gdal.GetLastErrorMsg());
            return Kv.by("errorMsg", "GDALOpen failed - " + gdal.GetLastErrorNo()).set("code", 400);
        }
        double[] dGeoTrans = hDataset.GetGeoTransform();

        Driver hDriver = hDataset.GetDriver();
        System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
        int iXSize = hDataset.getRasterXSize();
        int iYSize = hDataset.getRasterYSize();
        System.out.println("Size is " + iXSize + ", " + iYSize);
        //波段数
        int rasterCount = hDataset.getRasterCount();
        if (rasterCount != 4) {
            return Kv.by("errorMsg", "必须为四波段高分1的tiff数据").set("code", 400);
        }

//        Layer enlovpe = getEnlovpe(latlons);
//        Layer testlayer = dataset.CreateLayer("test");
//        Layer layer = dataset.GetLayer(0);
//        layer.Clip(enlovpe,testlayer);
//
        Band band3 = hDataset.GetRasterBand(3);//红
        Band band4 = hDataset.GetRasterBand(4);//近红
        //包围盒空间坐标转tif行列号
        int[] maxRowCol = Projection2ImageRowCol(dGeoTrans, max_lon, max_lat);
        int[] minRowCol = Projection2ImageRowCol(dGeoTrans, min_lon, min_lat);
        int max_x = (int) maxRowCol[0];
        int max_y = (int) maxRowCol[1];
        int min_x = (int) minRowCol[0];
        int min_y = (int) minRowCol[1];
        if (max_y < 0) {
            max_y = 0;
        }
        if (min_x < 0) {
            min_x = 0;
        }
        if (min_y > iYSize) {
            min_y = iYSize;
        }
        if (max_x > iXSize) {
            max_x = iXSize;
        }
        //获取二维数组
        System.out.println("x:" + min_x + "到" + max_x);
        System.out.println("y:" + max_y + "到" + min_y);
        System.out.println("x:" + (max_x - min_x));
        System.out.println("y:" + (min_y - max_y));
        float[][] data = new float[min_y - max_y][max_x - min_x];
        Double[] nodatas = new Double[]{null};
        band3.GetNoDataValue(nodatas);
        Double noData = nodatas[0];
        if (max_x == min_x && max_y == min_y) {
            //田块在一个像素里
            int buf4[] = new int[1];
            int buf5[] = new int[1];
            data = new float[1][1];
            band3.ReadRaster(min_x, min_y, 1, 1, buf4);
            band4.ReadRaster(min_x, min_y, 1, 1, buf5);
            double add = Arith.add(buf4[0], buf5[0]);
            if (add == 0) {
                data[0][0] = 0;
            } else {
                Double div = Arith.div(Arith.sub(buf4[0], buf5[0]), add);
                if (div == -1 || div == 1) {
                    data[0][0] = 0;
                } else {
                    data[0][0] = div.floatValue();
                }
            }
        } else {
            int buf4[] = new int[iYSize];
            int buf5[] = new int[iYSize];
            for (int i = min_x; i < max_x; i++) {
                band3.ReadRaster(0, i, iYSize, 1, buf4);
                band4.ReadRaster(0, i, iYSize, 1, buf5);
                for (int j = max_y; j < min_y; j++) {
                    //行列号转地理坐标
                    double[] doubles = ImageRowCol2Projection(dGeoTrans, i, j);
                    double lon = doubles[0];
                    double lat = doubles[1];
                    boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))");
                    if (iscontains) {
                        //面内，赋值像素值
                        int b4 = buf4[(iYSize-j)-1];
                        int b5 = buf5[(iYSize-j)-1];
//                        System.out.println(b4+"-"+b5);
                        if (noData == null || Math.abs(b4 - noData) > 1e-6) {
                            //ndvi = B5-B4/B5+B4   B4是红，B5是近红 正常结果范围在-1到1之间
                            double add = Arith.add(b5, b4);
                            if (add == 0) {
                                data[j - max_y][i - min_x] = 2;
                            } else {
                                Double div = Arith.div(Arith.sub(b5, b4), add);
//                                if (div == 0) {
//                                    System.out.println(lon + " " + lat);
//                                }
                                if (div > 1) {
                                    data[j - max_y][i - min_x] = 1;
                                } else if (div < -1) {
                                    data[j - max_y][i - min_x] = -1;
                                } else {
                                    data[j - max_y][i - min_x] = div.floatValue();
                                }
                            }
                        } else {
                            data[j - max_y][i - min_x] = 2;
                        }
//                        System.out.print(data[j - max_y][i - min_x] + ", ");
                    } else {
                        data[j - max_y][i - min_x] = 2;
                    }
                }
//                System.out.println("\n");
            }
        }
        dataset.delete();
        hDataset.delete();
        File file = new File(new_tiff);
        file.delete();
        return Kv.by("data", data).set("intersec", true).set("code", 200);
//        return testlayer;
    }

    private static int[] Projection2ImageRowCol(double[] adfGeoTransform, double dProjX, double dProjY) {
//        for (int i = 0; i < adfGeoTransform.length; i++) {
//            System.out.println("6参" + i + ":" + adfGeoTransform[i]);
//        }
        double dTemp = adfGeoTransform[1] * adfGeoTransform[5] - adfGeoTransform[2] * adfGeoTransform[4];
        double dCol = 0.0, dRow = 0.0;
        dCol = (adfGeoTransform[5] * (dProjX - adfGeoTransform[0]) -
                adfGeoTransform[2] * (dProjY - adfGeoTransform[3])) / dTemp + 0.5;
        dRow = (adfGeoTransform[1] * (dProjY - adfGeoTransform[3]) -
                adfGeoTransform[4] * (dProjX - adfGeoTransform[0])) / dTemp + 0.5;
        int col = (int) Math.ceil(dCol);
        int row = (int) Math.ceil(dRow);
        return new int[]{col, row};
    }

    private static double[] ImageRowCol2Projection(double[] adfGeoTransform, int iCol, int iRow) {
        double dProjX = adfGeoTransform[0] + adfGeoTransform[1] * iCol + adfGeoTransform[2] * iRow;
        double dProjY = adfGeoTransform[3] + adfGeoTransform[4] * iCol + adfGeoTransform[5] * iRow;
        return new double[]{dProjX, dProjY};
    }


}
