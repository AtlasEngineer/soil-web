package com.soli.server.utils;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * gemotry之间的关系
 */
public class GeometryRelated {

    private static GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public static Geometry union(String geometry,String geometry2) throws ParseException {
        WKTReader reader = new WKTReader( geometryFactory );
        Geometry read = reader.read(geometry);
        Geometry read1 = reader.read(geometry2);
        Geometry union = read.union(read1);

        return union;
    }
    public static Geometry union(Geometry geometry,String geometry2) throws ParseException {
        WKTReader reader = new WKTReader( geometryFactory );
        Geometry read1 = reader.read(geometry2);
        Geometry union = geometry.union(read1);
        return union;
    }
    public static Geometry getGeometry(String geometry) throws ParseException {
        WKTReader reader = new WKTReader( geometryFactory );
        Geometry read1 = reader.read(geometry);
        return read1;
    }

    /**
     *  两个几何对象是否是重叠的
     * @return
     * @throws ParseException
     */
    public static boolean equalsGeo() throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(5 0, 0 0)");
        return geometry1.equals(geometry2);//true
    }

    /**
     * 几何对象没有交点(相邻)
     * @return
     * @throws ParseException
     */
    public static boolean disjointGeo() throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(0 1, 0 2)");
        return geometry1.disjoint(geometry2);
    }

    /**
     * 至少一个公共点(相交)
     * @return
     * @throws ParseException
     */
    public static boolean intersectsGeo() throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(0 0, 0 2)");
        Geometry interPoint = geometry1.intersection(geometry2);//相交点
        System.out.println(interPoint.toText());//输出 POINT (0 0)
        return geometry1.intersects(geometry2);
    }

    /**
     * 判断以x,y为坐标的点point(x,y)是否在geometry表示的Polygon中
     * @param x
     * @param y
     * @param geometry wkt格式
     * @return
     */
    public static boolean withinGeo(double x,double y,String geometry,int srid) throws ParseException {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);
        Coordinate coord = new Coordinate(x,y);
        Point point = geometryFactory.createPoint( coord );
        WKTReader reader = new WKTReader( geometryFactory );
        Polygon polygon = (Polygon) reader.read(geometry);
        return point.within(polygon);
    }

    /**
     * 判断以x,y为坐标的点point(x,y)是否在geometry表示的Polygon中
     * @param x
     * @param y
     * @param geometry wkt格式
     * @return
     */
    public static boolean withinGeo(double x,double y,String geometry) throws ParseException {
        Coordinate coord = new Coordinate(x,y);
        Point point = geometryFactory.createPoint( coord );
        WKTReader reader = new WKTReader( geometryFactory );
        Polygon polygon = (Polygon) reader.read(geometry);
        return point.within(polygon);
    }

}
