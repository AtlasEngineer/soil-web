package com.soli.server.utils;


import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.GridCoverageLayer;
import org.geotools.map.*;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;

public class GtsLayer {
    private String name;
    private Layer layer = null;
    ReferencedEnvelope bounds;

    public GtsLayer(String name) {
        this.name = name;
    }

    public MapContent getMap() {
        MapContent map = new MapContent();
        map.addLayer(layer);
        return map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Layer getLayer() {
        return layer;
    }

    public void setLayer(Layer layer) {
        this.layer = layer;
    }

    /**
     * 加入栅格图层
     */
    public void addCoverage(GridCoverage2D coverage) {
        addCoverage(coverage, createDefaultRasterStyle());
    }

    public void addCoverage(GridCoverage2D coverage, Style style) {
        this.layer = new GridCoverageLayer(coverage, style);
    }

    private Style createDefaultRasterStyle() {
        Style style = ReadTiffUtils.createStyle(1, -1, 0.219907);
//        StyleFactory sf = CommonFactoryFinder.getStyleFactory();
//        RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
//        Style style = SLD.wrapSymbolizers(sym);
        return style;
    }


}
