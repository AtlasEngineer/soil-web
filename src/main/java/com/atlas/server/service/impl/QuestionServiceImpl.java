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

import com.atlas.server.model.Answer;
import com.atlas.server.model.InsectSpecies;
import com.atlas.server.model.Reply;
import com.atlas.server.model.sql.AnswerCriteria;
import com.atlas.server.model.sql.QuestionCriteria;
import com.atlas.server.utils.AnswerNode;
import com.atlas.server.utils.ReplayNode;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.QuestionService;
import com.atlas.server.model.Question;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class QuestionServiceImpl extends LambkitModelServiceImpl<Question> implements QuestionService {

    private Question DAO = null;

    public Question dao() {
        if (DAO == null) {
            DAO = AopKit.singleton(Question.class);
        }
        return DAO;
    }

    @Override
    public Page all(Integer pageNum, Integer pageSize) {

        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }

        Page<Question> page = Question.service().dao().paginate(pageNum, pageSize, Question.sql().andDelEqualTo(0).example().setOrderBy("time desc"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Question question:page.getList()){
            Integer num=Db.queryInt("SELECT count(*) from at_answer  a where a.q_id="+question.getId()+"");
            UpmsUser upmsUser=UpmsUser.service().dao().findById(question.getUserId());
            question.put("auth",upmsUser.getRealname());
            if(num==0){
                question.put("num",0);
            }else {
                question.put("num",num);
            }
            question.put("time",formatter.format(question.getTime()));
        }
        return page;
    }

    @Override
    public Page allByUser(Integer pageNum, Integer pageSize) {
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

        if (pageNum == null || pageSize == null) {
            pageNum = 1;
            pageSize = 10;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Page<Question> page = Question.service().dao().paginate(pageNum, pageSize, Question.sql().andDelEqualTo(0).andUserIdEqualTo(upmsUser.getUserId().intValue()).example());
        for (Question question:page.getList()){
            Integer num=Db.queryInt("SELECT count(*) from at_answer  a where a.q_id="+question.getId()+"");
            UpmsUser user=UpmsUser.service().dao().findById(question.getUserId());
            question.put("auth",user.getRealname());
            if(num==0){
                question.put("num",0);
            }else {
                question.put("num",num);
            }
            question.put("time",formatter.format(question.getTime()));
        }
        return page;
    }

	@Override
	public boolean addQuestion(Question question) {
		String token = RequestManager.me().getRequest().getHeader("Authorization");

		JwtConfig config = Lambkit.config(JwtConfig.class);
		String tokenPrefix = config.getTokenPrefix();
		String authToken = token.substring(tokenPrefix.length());
		String username = JwtKit.getJwtUser(authToken);
		if (username == null) {
			return false;
		}
		System.out.println("username : " + username);
		UpmsUser upmsUser = UpmsUser.service().dao().findFirst(UpmsUser.sql().andUsernameEqualTo(username).example());
		if (upmsUser == null) {
			return false;
		}
		question.setUserId(upmsUser.getUserId().intValue());
		question.setTime(new Date());

		boolean result=question.save();
		if(result){
			return true;
		}else {
			return false;
		}

	}

	@Override
	public Question questionById(Integer id,Integer pageNum,Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;

        }
        if(pageSize==null){
            pageSize = 10;
        }
    	Question question=Question.service().dao().findById(id);

    	Integer num=Db.queryInt("select count(*) from at_answer a where a.q_id="+question.getId()+" ");
        AnswerCriteria sql=new AnswerCriteria();

    	Page<Answer> answerPage=Answer.service().dao().paginate(pageNum,pageSize,sql.andQIdEqualTo(question.getId()).example());

        for(Answer temp : answerPage.getList()){
            Integer tid = temp.getId();
            //找到是这个评论的所有回复
            System.out.println("tid:"+tid);
            List<Reply> answer = Reply.service().dao().find(Reply.sql().andTorridEqualTo(tid).andTorrEqualTo(1).example());

            //遍历每个第一层回复
            temp.put("answer",answer);
            temp.put("num",answer.size());
            for(Reply re:answer){
                List<Reply>  replayList = Reply.service().dao().find(Reply.sql().andTorridEqualTo(re.getId()).andTorrEqualTo(0).example());
                for (Reply reply:replayList){
                    UpmsUser upmsUser=UpmsUser.service().dao().findById(reply.getTouid());
                    reply.put("touheadurl",upmsUser.getAvatar());
                }
                re.put("replay",replayList);
            }
        }
        UpmsUser upmsUser=UpmsUser.service().dao().findById(question.getUserId());

		question.put("num",num);
        question.put("username",upmsUser.getRealname());
        question.put("auth",upmsUser.getAvatar());
        question.put("autograph",upmsUser.getStr("autograph"));
        question.put("topics",answerPage);
		return question;
	}



    //插入链表 参数分别代表需要待插入的节点list 和这些节点的父亲是谁
    public boolean AddReplayNode(List<Reply> relists, ReplayNode freplay){
        //为空就直接返回
        if(relists.size()==0)return false;
        //挨个遍历list中的节点信息，然后如果节点还有孩子就继续递归
        for(Reply re:relists){
            ReplayNode newreplaynode = new ReplayNode();
            newreplaynode.setReply(re);
            freplay.getListReply().add(newreplaynode);
            List<Reply> replayList = new ArrayList<>();
            replayList = Reply.service().dao().find(Reply.sql().andTorridEqualTo(re.getId()).andTorrEqualTo(0).example());
            //有孩子就继续递归，有没有孩子这里是统一进入递归才判断，也可以来个if else
            AddReplayNode(replayList,newreplaynode);
        }
        return false;
    }
    //展示出来 参数表示需要展示的节点list
    public void ShowReplayNodes(List<ReplayNode> replayNodes){
        if(replayNodes.size()==0)return;
        for(ReplayNode temp: replayNodes){
            System.out.println(temp.getReply().toString());
            ShowReplayNodes(temp.getListReply());
        }
    }


}
