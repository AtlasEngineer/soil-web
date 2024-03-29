/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soli.server.service;

import com.jfinal.kit.Ret;
import com.lambkit.common.service.LambkitService;

import com.lambkit.core.api.route.ApiBody;
import com.lambkit.core.api.route.ApiMapping;
import com.lambkit.core.api.route.ApiRenderJFinalJson;
import com.soli.server.model.Tiankuai;
import com.vividsolutions.jts.io.ParseException;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.referencing.operation.TransformException;

import java.io.IOException;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-09-27
 * @version 1.0
 * @since 1.0
 */
public interface TiankuaiService extends LambkitService<Tiankuai> {

    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.camera",useLogin = false)
    public Ret all(Integer id);


    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.diseases",useLogin = false)
    public Ret searchDiseases(String type,String period,String crop);


    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.diseases.del",useLogin = false)
    public Ret searchDiseasesDel(String type,Integer id);




    /**
     *  数据查询 -  聚合查询
     * @param countyId      县id
     * @param type          数据类型
     * @param time          时间【 以逗号分隔 】 eg: 2020-10-16,2020-10-17
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.compoundQuery",useLogin = false)
    public Ret compoundQuery(Integer countyId,Integer type,String time[],String latlons,List<Integer> id) throws ParseException, IOException, CQLException, java.text.ParseException, TransformException;

    /**
     *  数据查询 -  聚合查询
     * @param time          时间【 以逗号分隔 】 eg: 2020-10-16,2020-10-17
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.queryCount",useLogin = false)
    public Ret queryCount(String latlons,String time[],List<Integer> id) throws IOException, CQLException;


    /**
     *  数据查询 -  点查询
     * @param countyId      县id
     * @param type          数据类型
     * @param time          时间【 以逗号分隔 】 eg: 2020-10-16,2020-10-17
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.compoundQuery.spot",useLogin = false)
    public Ret compoundQueryBySpot(Double longitude, Double latitude,List<Integer> id) throws IOException, CQLException, ParseException;



    /**
     *  数据查询 -  多边形查询
     * @param countyId      县id
     * @param type          数据类型
     * @param time          时间【 以逗号分隔 】 eg: 2020-10-16,2020-10-17
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.compoundQuery.noodles",useLogin = false)
    public Ret compoundQueryByNoodles(String latlons) throws IOException, CQLException, ParseException;



    /**
     *  数据管理 -  无人机影像数据
     * @param countyId      县id
     * @param type          数据类型
     * @param time          时间【 以逗号分隔 】 eg: 2020-10-16,2020-10-17
     * @return
     */
    @ApiBody(ApiRenderJFinalJson.class)
    @ApiMapping(value = "search.uav",useLogin = false)
    public Ret uav();




}
