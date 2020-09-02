package com.soli.server.utils;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.lambkit.Lambkit;
import com.soli.lambkit.start.GeoServerConfig;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher.UploadMethod;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.apache.commons.httpclient.NameValuePair;

import java.io.File;
import java.util.List;

public class PublicShp {
	public boolean pu(String layername,int id,String epsgCode){
		
		String RESTURL  = "http://127.0.0.1:8080/geoserver";//localhost http://59.110.20.59:8585/geoserver
	    String RESTUSER = "admin";
	    String RESTPW   = "Q7P0XFU$2YRQaq08";
	    String workspace = "jsus";
	    String storename = "jsus";
	    String resPath = PathKit.getWebRootPath()+"\\src\\main\\webapp\\jsus";
	    String uploadMethod ="external";// external 
	    epsgCode = "EPSG:91901"+id;
	    
	    List<String> workspaces=null;
	    try {
	        GeoServerRESTReader reader = new GeoServerRESTReader(RESTURL, RESTUSER, RESTPW);
	        GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
	        workspaces=reader.getWorkspaceNames();
	        if(workspaces.contains(workspace)){
	        	UploadMethod method = UploadMethod.EXTERNAL;
	        	File shpFile = null;
	        	if(uploadMethod.equals("url")) {
	    			method = UploadMethod.URL;
	    			String url = "\\\\127.0.0.1:8080\\";
	    			shpFile=new File(url + resPath + storename + "\\" + layername + ".zip");
	    		} else if(uploadMethod.equals("file")) {
	    			//zipfile
	    			method = UploadMethod.FILE;
	    			System.out.println(layername);
	    			System.out.println(resPath);
	    			shpFile=new File(resPath + File.separator + storename + File.separator + layername+ ".shp");
	    		} else {
	    			shpFile=new File(resPath + File.separator + storename+ File.separator + layername);
	    		}
	        	System.out.println("shpFile :"+shpFile);
	        	String ul=workspace+":"+layername;
            	/*String sql="update ydyl_datalist set url='"+ul+"' where id='"+id+"'";
            	Db.update(sql);*/
	    		return publisher.publishShp(workspace, storename, (NameValuePair[]) null, layername, 
	    				method, shpFile.toURI(), "EPSG:4326", epsgCode, ProjectionPolicy.REPROJECT_TO_DECLARED, null);
	        }
	    }catch (Exception mue){
	        mue.printStackTrace();
	    }
	    System.out.println("------over-----");
	    return false;
	}
	public boolean publishPg(String gsName, String layerName, String tbname,Object id, String epsgCode, String defaultStyle) {
		GeoServerConfig config = Lambkit.config(GeoServerConfig.class);
		//if(config==null) return false;
		String RESTURL  = config.getGeourl();//http://localhost:8080/geoserver
		System.out.println("RESTURL:"+RESTURL);
		String RESTUSER = config.getGeouser();//admin
        String RESTPW   = config.getGeopsw();//"Q7P0XFU$2YRQaq08";
		String workspace = "jsus";//jsus;
		String storename = "jsus";//"jsus";
		GeoServerRESTPublisher publisher = new GeoServerRESTPublisher(RESTURL, RESTUSER, RESTPW);
		GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
		fte.setProjectionPolicy(ProjectionPolicy.REPROJECT_TO_DECLARED);
		fte.addKeyword("KEYWORD");
		fte.setTitle(layerName);
		fte.setName(tbname);
		fte.setSRS(epsgCode);
		fte.setNativeCRS(epsgCode);
		GSLayerEncoder layerEncoder = new GSLayerEncoder();
		layerEncoder.setDefaultStyle(defaultStyle);
		String ul=workspace+":"+layerName;
    	String sql="update kk_geolist set url='"+ul+"' where id='"+id+"'";
    	Db.update(sql);
		return publisher.publishDBLayer(workspace, storename, fte, layerEncoder);
		}
	public static void  main(String arg[]){
		PublicShp p = new PublicShp();
		boolean pu = p.pu("ad", 1, "");
		if(!pu){
			System.out.println(pu);
		}else {
			System.out.println("aaa");
		}

	}
}
