package com.atlas.lambkit.geoserver;

import com.lambkit.core.config.annotation.PropertieConfig;

@PropertieConfig(prefix = "lambkit.geoserver")
public class GeoServerConfig {

    private String geourl = "http://192.168.1.139:10099/geoserver";//http://59.110.20.59:8585/geoserver localhost:8085
    private String geouser = "admin";
    private String geopsw = "Q7P0XFU$2YRQaq08";//Q7P0XFU$2YRQaq08,geoserver
    private String workspace = "jsus";
    private String respath = "D:/jsus";
    private String locurl = "127.0.0.1:8080";
    private String uploadmethod = "external";
    public String getGeourl() {
        return geourl;
    }
    public void setGeourl(String geourl) {
        this.geourl = geourl;
    }
    public String getGeouser() {
        return geouser;
    }
    public void setGeouser(String geouser) {
        this.geouser = geouser;
    }
    public String getGeopsw() {
        return geopsw;
    }
    public void setGeopsw(String geopsw) {
        this.geopsw = geopsw;
    }
    public String getWorkspace() {
        return workspace;
    }
    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }
    public String getLocurl() {
        return locurl;
    }
    public void setLocurl(String locurl) {
        this.locurl = locurl;
    }
    public String getRespath() {
        return respath;
    }
    public void setRespath(String respath) {
        this.respath = respath;
    }
    public String getUploadmethod() {
        return uploadmethod;
    }
    public void setUploadmethod(String uploadmethod) {
        this.uploadmethod = uploadmethod;
    }
}
