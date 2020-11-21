package com.soli.server.utils;

import com.jfinal.kit.Kv;
import com.lambkit.common.util.StringUtils;
import com.vividsolutions.jts.io.ParseException;
import org.gdal.gdal.*;
import org.gdal.gdalconst.gdalconstConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class GdalUtils {

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String gf1Path = "D:\\IdeaWorkSpace\\soil-web\\src\\main\\webapp\\upload\\crawlers_gf\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063.tiff";
        try {
            //116.944 36.066
//            getPix(gf1Path);
            Kv gf1Data = getGF1Data("113.999 36,114 36,114 35.999,113.999 35.999,113.999 36", gf1Path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - l) + "ms");
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
        Band band4 = hDataset.GetRasterBand(3);//红
        Band band5 = hDataset.GetRasterBand(4);//近红
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
        //获取数据
        System.out.println("x:"+min_x+"到"+max_x);
        System.out.println("y:"+max_y+"到"+min_y);
        System.out.println("x:" + (max_x - min_x));
        System.out.println("y:" + (min_y - max_y));
        float[][] data = new float[min_y - max_y][max_x - min_x];
        Double[] nodatas = new Double[]{null};
        band4.GetNoDataValue(nodatas);
        Double noData = nodatas[0];

        if (max_x == min_x && max_y == min_y) {
            //田块在一个像素里
            int buf4[] = new int[1];
            int buf5[] = new int[1];
            data = new float[1][1];
            band4.ReadRaster(min_x, min_y, iXSize, 1, buf4);
            band5.ReadRaster(min_x, min_y, iXSize, 1, buf5);
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
                band4.ReadRaster(0, i, iYSize, 1, buf4);
                band5.ReadRaster(0, i, iYSize, 1, buf5);
                for (int j = max_y; j < min_y; j++) {
                    //行列号转地理坐标
                    double[] doubles = ImageRowCol2Projection(dGeoTrans, i, j);
                    double lon = doubles[0];
                    double lat = doubles[1];
                    boolean iscontains = GeometryRelated.withinGeo(lon, lat, "POLYGON((" + latlons + "))");
                    if (iscontains) {
                        //面内，赋值像素值
                        int b4 = buf4[j];
                        int b5 = buf5[j];
//                        System.out.println(b4+"-"+b5);
                        if (noData == null || Math.abs(b4 - noData) > 1e-6) {
                            //ndvi = B5-B4/B5+B4   B4是红，B5是近红 正常结果范围在-1到1之间
                            double add = Arith.add(b5, b4);
                            if (add == 0) {
                                data[j - max_y][i - min_x] = 2;
                            } else {
                                Double div = Arith.div(Arith.sub(b5, b4), add);
                                if (div == 0) {
                                    System.out.println(lon+" "+lat);
                                }
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
