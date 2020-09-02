package com.soli.server.utils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.util.TimeUtils;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class TestShapeFile {

	/*public static void main(String[] args) throws IOException {
		TestShapeFile shp = new TestShapeFile();
		PublicShp p = new PublicShp();                                                   
		String shpfile = shp.unZip(new File("D:/jsus/01134838y39058741e97343ed.zip"), "D:/jsus/01134838y39058741e97343ed/");
		if(StrKit.notBlank(shpfile)) {
			System.out.println(shpfile+"1");
			String f = shpfile.substring(8,33);
			System.out.println(f);
//			p.pu(f);
//			shp.loadShp(shpfile);//("D:/web/szgs/target/recvfiles/ysgx/ysgx.shp");
			String shppath = new File(shpfile).getParent();
			System.out.println(shppath);
			//PathUtils.deleteDirectory(new File(shppath));
		}
			
		System.out.println("---------------over-------------");
	}*/
	
	/**
	 * 解压文件
	 * @param zipFile 目标文件
	 * @param descDir 指定解压目录
	 * @return
	 */
	public String unZip(File zipFile, String descDir) {
		String shpfile = null;
	    File pathFile = new File(descDir);
	    if(!pathFile.exists()){
	        pathFile.mkdirs();
	    }
	    String name = pathFile.getName();
	    System.out.println(name+":name");
	    ZipFile zip = null;
	    try {
	        //指定编码，否则压缩包里面不能有中文目录
	        zip = new ZipFile(zipFile, Charset.forName("gbk"));
	        for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
	            ZipEntry entry = (ZipEntry)entries.nextElement();
	            String zipEntryName = entry.getName();
	            String shap = zipEntryName.substring(zipEntryName.lastIndexOf(".") + 1).trim().toLowerCase();
	            int dot = zipEntryName.lastIndexOf('.');
				String f="";
				if(dot!=-1){
					 f = zipEntryName.substring(0, dot);
				}
				String replace =null;
				if(!shap.equals("xml")){
					replace = zipEntryName.replace(f, name);
				}else{
					replace = zipEntryName.replace(f, name+".shp");	
				}
	            InputStream in = zip.getInputStream(entry);
	            String outPath = (descDir+replace).replace("/", File.separator);
	            //判断路径是否存在,不存在则创建文件路径
	            File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
	            if(!file.exists()){
	                file.mkdirs();
	            }
	            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
	            if(new File(outPath).isDirectory()){
	                continue;
	            }
	            //保存文件路径信息
	            //urlList.add(outPath);
//	            System.out.println("unZip: " + outPath);
	            if(outPath.endsWith(".shp")) {
	            	shpfile = outPath;
	            }

	            OutputStream out = new FileOutputStream(outPath);
	            byte[] buf1 = new byte[2048];
	            int len;
	            while((len=in.read(buf1))>0){
	                out.write(buf1,0,len);
	            }
	            in.close();
	            out.close();
	        }
	        //必须关闭，否则无法删除该zip文件
	        zip.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return shpfile;
	}

	
	public void loadShp(String shpfile) {
        ShapefileDataStore shpDataStore = null;
        TimeUtils.startTime("read Shp feature");
       try{
            File file = new File(shpfile);
            String filename = file.getName();
            int type = -1;
            if("zlgx".equals(filename)) {
            	type = 1;
            } else if("ysgx".equals(filename)) {
            	type = 2;
            } else if("fggx".equals(filename)) {
            	type = 0;
            } else if("original".equals(filename)) {
            	type = 0;
            }
            System.out.println("type: " + type);
            if(type==0) {
            	//覆盖更新
            	Db.delete("delete from sz_waterline;");
            } else if(type==1) {
            	//增量更新
            } else if(type==2) {
            	//要素更新
            	Db.delete("delete from sz_waterline where level='5级';");
            } else {
            	return;
            }
            shpDataStore = new ShapefileDataStore(file.toURI().toURL());
            //设置字符编码
            Charset charset = Charset.forName("gbk");
            shpDataStore.setCharset(charset);
            String typeName = shpDataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource =  shpDataStore.getFeatureSource (typeName);
            SimpleFeatureCollection result = featureSource.getFeatures();
            SimpleFeatureIterator itertor = result.features();
            //int i=0;
            while (itertor.hasNext())
            {
                SimpleFeature feature = itertor.next();
                //FeatureId fid = feature.getIdentifier();
                String id = feature.getID();
                Collection<Property> p = feature.getProperties();
                Iterator<Property> it = p.iterator();
                //int j=0;
                Record record = new Record();
                record.set("shpid", Integer.parseInt(id.replace(typeName + ".", "")));
                record.set("userid", 1);
                record.set("type", "sz");
                record.set("shape", "折线");
                String geom = "";
                while(it.hasNext()) {
                    Property pro = it.next();
                    String field = pro.getName().toString();
                    String value = pro.getValue().toString();
                    field = field.equals("the_geom")?"wkt":field;
                    if(field.equalsIgnoreCase("name")) record.set("name", value);
                    else if(field.equalsIgnoreCase("code")) record.set("code", value);
                    else if(field.equalsIgnoreCase("level")) record.set("level", value);
                    else if(field.equalsIgnoreCase("SHAPE_Leng")) record.set("length", Double.parseDouble(value));
                    else if(field.equalsIgnoreCase("cost")) record.set("cost", Double.parseDouble(value));
                    
                    if(!field.equals("wkt")) {
                    	//System.out.println(field+"="+value);
                    } else {
                    	geom = value;
                    	System.out.println(geom);
                    }
                }
                
              //要素更新
                if(type==2 && !"5级".equals(record.get("level"))) {
                	continue;
                }
                Db.save("sz_waterline", record);
                Db.update("UPDATE sz_waterline SET geom = st_geomfromtext('"+geom+"',4326) where id="+record.get("id"));
            }
            itertor.close();
            TimeUtils.endTime("read Shp feature");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
	}
	
	public boolean deleteDir(String path){
		File file = new File(path);
		if(!file.exists()){//判断是否待删除目录是否存在
			System.err.println("The dir are not exists!");
			return false;
		}
		
		String[] content = file.list();//取得当前目录下所有文件和文件夹
		for(String name : content){
			File temp = new File(path, name);
			if(temp.isDirectory()){//判断是否是目录
				deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
				temp.delete();//删除空目录
			}else{
				if(!temp.delete()){//直接删除文件
					System.err.println("Failed to delete " + name);
				}
			}
		}
		return true;
	}
}
