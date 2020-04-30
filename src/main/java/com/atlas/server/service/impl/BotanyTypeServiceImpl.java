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
package com.atlas.server.service.impl;

import com.atlas.server.model.Catalogue;
import com.atlas.server.model.InsectPests;
import com.atlas.server.utils.Co;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.common.util.StringUtils;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.BotanyTypeService;
import com.atlas.server.model.BotanyType;
import com.lambkit.core.cache.impl.RedisCacheImpl;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;
import com.sun.org.apache.xml.internal.resolver.Catalog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yangyong
 * @version 1.0
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-08
 * @since 1.0
 */
public class BotanyTypeServiceImpl extends LambkitModelServiceImpl<BotanyType> implements BotanyTypeService {


    private BotanyType DAO = null;

    public BotanyType dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(BotanyType.class);
        }
        return DAO;
    }

    @Override
    public Page all(Integer pageNum, Integer pageSize) {

        if (pageNum == null  ) {
            pageNum = 1;

        }
        if(pageSize == null){
            pageSize = 5;
        }
        Page page = Db.paginate(pageNum, pageSize, "select *", "from news where del=0");

        return page;
    }

    @Override
    public Record searchNewsById(Integer id) {

        String token = RequestManager.me().getRequest().getHeader("Authorization");
        System.out.println("token"+token);
        Record record = Db.findFirst("select * from news where del=0 and id=" + id + "");
        if(record!=null){
            if(StringUtils.isNotBlank(token)){
                JwtConfig config = Lambkit.config(JwtConfig.class);
                String tokenPrefix = config.getTokenPrefix();
                String authToken = token.substring(tokenPrefix.length());
                String username = JwtKit.getJwtUser(authToken);
                if (username == null) {
                    return null;
                }
                System.out.println("username : " + username);
                UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
                if (upmsUser == null) {
                    return null;
                }
                RedisCacheImpl redis = new RedisCacheImpl();
                Integer t_id = redis.get("news", upmsUser.getUserId());

                Integer status = Db.queryInt("select status from news_collection c where c.user_id=" + upmsUser.getUserId() + " and c.news_id=" + id + ""); //0收藏 1取消收藏
                if (null == status) {
                    record.set("is_Collection", false);
                } else {
                    if (status > 0) {
                        record.set("is_Collection", false);
                    } else {
                        record.set("is_Collection", true);
                    }

                }
                if (t_id != null) {  //在redis里存在用户看过该文章直接返回详情
                    return record;
                } else {    //先加入redis，在返回详情
                    redis.put("news", upmsUser.getUserId(), id, 60 * 20);
                    Integer num = record.getInt("volume");
                    num++;
                    String sql = "update news set volume=" + num + " where id=" + id + " ";
                    Integer result = Db.update(sql);

                    if (result > 0) {
                        return record;
                    } else {
                        return null;
                    }
                }

            }else {
                record.set("is_Collection", false);
                return  record;

            }
        }else {
            return  null;
        }

    }

    @Override
    public Co addNews(String news_id, Integer status,Integer type) {
        String token = RequestManager.me().getRequest().getHeader("Authorization");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置日期格式
        String time=df.format(new Date());

        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String username = JwtKit.getJwtUser(authToken);
        if (username == null) {
            return null;
        }
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (upmsUser == null) {
            return null;
        }
        String sql = "";
        Record record = Db.findFirst("select status from news_collection c where c.user_id=" + upmsUser.getUserId() + " and c.news_id='" + news_id + "'");

        if(type==0){
            Record news=Db.findFirst("select * from news s where s.id='"+news_id+"'");
            if (record == null) {
                sql = "insert into news_collection (user_id,news_id,status,title,time,image,type) value (" + upmsUser.getUserId() + ",'" + news_id + "'," + status + ",'"+news.getStr("title")+"','"+time +"','"+news.getStr("image")+"',"+type+") ";
            } else {
                sql = "UPDATE  news_collection SET status=" + status + " WHERE user_id=" + upmsUser.getUserId() + " and news_id='" + news_id + "' and type="+type+"";
            }
        }else if(type==1){

            if (record == null) {
                Record news=Db.findFirst("select * from catalogue_sample s where s.id='"+news_id+"'");
                Catalogue catalogue=Catalogue.service().dao().findFirst(Catalogue.sql().andIdEqualTo(news_id).example());
                if(news==null){
                    sql = "insert into news_collection (user_id,news_id,status,title,time,image,type) value (" + upmsUser.getUserId() + ",'" + news_id + "'," + status + ",'"+catalogue.getName()+"','"+time +"','/upload/1-11.jpg',"+type+") ";
                }else {
                    sql = "insert into news_collection (user_id,news_id,status,title,time,image,type) value (" + upmsUser.getUserId() + ",'" + news_id + "'," + status + ",'"+catalogue.getName()+"','"+time +"','"+news.getStr("url")+"',"+type+") ";
                }
            } else {
                sql = "UPDATE  news_collection SET status=" + status + " WHERE user_id=" + upmsUser.getUserId() + " and news_id='" + news_id + "' and type="+type+"";
            }
        }else if(type==2){
            Record news=Db.findFirst("select * from at_pests_sample s where s.id='"+news_id+"'");
            InsectPests insectPests=InsectPests.service().dao().findById(news_id);
            if (record == null) {
                if(news==null){
                    sql = "insert into news_collection (user_id,news_id,status,title,time,image,type) value (" + upmsUser.getUserId() + "," + news_id + "," + status + ",'"+insectPests.getName()+"','"+time +"','/upload/1-11.jpg',"+type+") ";
                }else {
                    sql = "insert into news_collection (user_id,news_id,status,title,time,image,type) value (" + upmsUser.getUserId() + "," + news_id + "," + status + ",'"+insectPests.getName()+"','"+time +"','"+news.getStr("url")+"',"+type+") ";
                }
            } else {
                sql = "UPDATE  news_collection SET status=" + status + " WHERE user_id=" + upmsUser.getUserId() + " and news_id='" + news_id + "' and type="+type+"";
            }
        }

        Integer result = Db.update(sql);
        if (result > 0) {
            return Co.ok("msg", "成功");
        } else {
            return Co.fail("msg", "失败");
        }

    }

    @Override
    public Co newsByCollection(Integer pageNum,Integer pageSize) {
        if (pageNum == null  ) {
            pageNum = 1;

        }
        if(pageSize == null){
            pageSize = 5;
        }
        String token = RequestManager.me().getRequest().getHeader("Authorization");

        JwtConfig config = Lambkit.config(JwtConfig.class);
        String tokenPrefix = config.getTokenPrefix();
        String authToken = token.substring(tokenPrefix.length());
        String username = JwtKit.getJwtUser(authToken);
        if (username == null) {
            return null;
        }
        System.out.println("username : " + username);
        UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
        if (upmsUser == null) {
            return null;
        }
        Page page=Db.paginate(pageNum,pageSize,"select *","from news_collection c where c.user_id="+upmsUser.getUserId().intValue()+" and status=0");
        return Co.ok("data",page);
    }
}
