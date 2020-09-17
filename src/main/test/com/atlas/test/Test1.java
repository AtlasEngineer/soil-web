package com.atlas.test;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

class test1 {

    public static void main(String[] args) {
        try{
            //定义属性
            final SimpleFeatureType TYPE = DataUtilities.createType("Location",
                    "location:Point," + // <- the geometry attribute: Point type
                            "POIID:String," + // <- a String attribute
                            "MESHID:String," + // a number attribute
                            "OWNER:String"
            );
            SimpleFeatureCollection collection = FeatureCollections.newCollection();
            GeometryFactory geometryFactory = new GeometryFactory();
            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);

            double latitude = Double.parseDouble("19.968");
            double longitude = Double.parseDouble("110.402");
            String POIID = "2050003092";
            String MESHID = "0";
            String OWNER = "不睡觉假装王祖贤";

            /* Longitude (= x coord) first ! */
            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
            Object[] obj = {point, POIID, MESHID, OWNER};
            // SimpleFeature只能创建一次
            SimpleFeature feature = featureBuilder.buildFeature(null, obj);
//            collection.add(feature);
//            feature = featureBuilder.buildFeature(null, obj);
//            collection.add(feature);

            File newFile = new File("D:/newPoi1.shp");
            ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put("url", newFile.toURI().toURL());
            params.put("create spatial index", Boolean.TRUE);
            ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
            newDataStore.createSchema(TYPE);
            newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);
            newDataStore.setCharset(Charset.forName("UTF-8"));

            Transaction transaction = new DefaultTransaction("create");
            String typeName = newDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(collection);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
            } else {
                System.out.println(typeName + " does not support read/write access");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
