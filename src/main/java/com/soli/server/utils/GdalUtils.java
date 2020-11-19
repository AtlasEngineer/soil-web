//package com.soli.server.utils;
//
//import org.gdal.gdal.*;
//import org.gdal.gdalconst.gdalconstConstants;
//import org.gdal.ogr.FeatureDefn;
//import org.gdal.ogr.Layer;
//import org.gdal.osr.SpatialReference;
//
//import java.util.Vector;
//
//public class GdalUtils {
//
//    public static void main(String[] args) {
//        String tif1 = "D:\\IdeaWorkSpace\\soil-web\\src\\main\\webapp\\upload\\crawlers_gf\\GF1_WFV3_E114.1_N35.6_20200907_L1A0005044872\\aaa.tif";
//        String fileName_tif = "D:\\IdeaWorkSpace\\soil-web\\src\\main\\webapp\\upload\\crawlers_gf\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063\\GF1_WFV3_E117.1_N35.6_20200927_L1A0005092063.tiff";
//        gdal.AllRegister();
////        Dataset hDataset = gdal.Open(fileName_tif);
////        String s1 = hDataset.GetProjection();
//
//
//        Dataset hDataset = gdal.Open(fileName_tif, gdalconstConstants.GA_ReadOnly);
//        WarpOptions warpOptions = new WarpOptions(new Vector<>());
//        Dataset[] src_array = {hDataset};
//        Dataset dataset = gdal.Warp("warp.tif", src_array, warpOptions);
//
//        System.out.println(dataset.GetProjection());
//
////        WarpOptions warpOptions = new WarpOptions(new Vector());
////        gdal.Warp("EPSG:4326", fileName_tif, fileName_tif, "GTiff");
//
////        System.out.println("s1:"+s1);
//        //宽、高、波段数
//        int x = hDataset.getRasterXSize();
//        int y = hDataset.getRasterYSize();
//        int b = hDataset.getRasterCount();
//
//        System.out.println("波段数"+b);
//
//        if (hDataset == null) {
//            System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
//            System.err.println(gdal.GetLastErrorMsg());
//            System.exit(1);
//        }
//        double[] dGeoTrans = hDataset.GetGeoTransform();
//        String s = hDataset.GetProjectionRef();
//        System.out.println("图像地理描述:" + s);
//
//        Driver hDriver = hDataset.GetDriver();
//        System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
//        int iXSize = hDataset.getRasterXSize();
//        int iYSize = hDataset.getRasterYSize();
//        System.out.println("Size is " + iXSize + ", " + iYSize);
//        Band band = hDataset.GetRasterBand(1);
//
//        double dTemp = dGeoTrans[1] * dGeoTrans[5] - dGeoTrans[2] * dGeoTrans[4];
//        double Xpixel = (dGeoTrans[5] * (115.0 - dGeoTrans[0]) - dGeoTrans[2] * (37.0 - dGeoTrans[3])) / dTemp + 0.5;
//        double Yline = (dGeoTrans[1] * (37.0 - dGeoTrans[3]) - dGeoTrans[4] * (115.0 - dGeoTrans[0])) / dTemp + 0.5;
//        System.out.println(Xpixel + "-" + Yline);
//        int buf[] = new int[iXSize];
//        for (int i = 0; i < 10; i++) {
//            band.ReadRaster(0, i, iXSize, 1, buf);
//            for (int j = 0; j < 10; j++)
//                System.out.print(buf[j] + ", ");
//            System.out.println("\n");
//        }
//
//        Projection2ImageRowCol(dGeoTrans, 115.0, 37.0);
//        hDataset.delete();
//    }
//
//    private static Boolean Projection2ImageRowCol(double[] adfGeoTransform, double dProjX, double dProjY) {
//        for (int i = 0; i < adfGeoTransform.length; i++) {
//            System.out.println(adfGeoTransform[i]);
//        }
//        double dTemp = adfGeoTransform[1] * adfGeoTransform[5] - adfGeoTransform[2] * adfGeoTransform[4];
//        double dCol = 0.0, dRow = 0.0;
//        dCol = (adfGeoTransform[5] * (dProjX - adfGeoTransform[0]) -
//                adfGeoTransform[2] * (dProjY - adfGeoTransform[3])) / dTemp + 0.5;
//        dRow = (adfGeoTransform[1] * (dProjY - adfGeoTransform[3]) -
//                adfGeoTransform[4] * (dProjX - adfGeoTransform[0])) / dTemp + 0.5;
//        System.out.println(dCol);
//        System.out.println(dRow);
//
//        dProjX = adfGeoTransform[0] + adfGeoTransform[1] * 5 + adfGeoTransform[2] * 5;
//        dProjY = adfGeoTransform[3] + adfGeoTransform[4] * 5 + adfGeoTransform[5] * 5;
//        System.out.println(dProjX + "_" + dProjY);
//        return true;
//    }
//
//
//}
