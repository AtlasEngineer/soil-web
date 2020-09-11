package com.soli.server.service.impl;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.common.model.LambkitModel;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.soli.server.service.QueryShapeService;
import com.soli.server.utils.GeometryRelated;
import com.soli.server.utils.ReadTiffUtils;

import java.util.ArrayList;
import java.util.List;

import static com.soli.server.utils.ReadTiffUtils.getAltitude;

public class QueryShapeServiceImpl extends LambkitModelServiceImpl implements QueryShapeService {
    @Override
    public LambkitModel dao() {
        return null;
    }

    @Override
    public Ret getPoint(Integer id, Double lon, Double lat) {
        Record tr_data = Db.findById("tr_data", id);
        if (tr_data != null){
            //获取文件类型  0 -shp   1- tif   2-其他文件
            Integer fileType = tr_data.getInt("type");
            String fileUrl = tr_data.get("url");
            if (fileType == 0){
                try {
                    //如果是shp  url为对应数据表名称
                    List<Record> records = Db.find("SELECT * FROM  \"" + fileUrl + "\" ");
                    List<Record> result = new ArrayList<>();
                    for (Record r : records) {
                        String geom = r.getStr("geom");
                        boolean iscontains = GeometryRelated.withinGeo(lon, lat, geom);
                        if (iscontains) {
                            result.add(r);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (fileType == 1){
                //如果是tif  直接进行查询
                try {
                    double altitude = getAltitude(lon, lat, fileUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
