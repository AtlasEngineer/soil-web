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

import com.atlas.server.model.Reply;
import com.atlas.server.service.ReplyService;
import com.atlas.server.utils.AnswerNode;
import com.atlas.server.utils.Co;
import com.atlas.server.utils.ReplayNode;
import com.jfinal.plugin.activerecord.Page;
import com.lambkit.Lambkit;
import com.lambkit.common.service.LambkitModelServiceImpl;
import com.lambkit.core.aop.AopKit;

import com.atlas.server.service.AnswerService;
import com.atlas.server.model.Answer;
import com.lambkit.module.upms.rpc.model.UpmsUser;
import com.lambkit.plugin.jwt.JwtConfig;
import com.lambkit.plugin.jwt.JwtKit;
import com.lambkit.web.RequestManager;

import javax.jms.Topic;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangyong 
 * @website: www.lambkit.com
 * @email: gismail@foxmail.com
 * @date 2020-04-10
 * @version 1.0
 * @since 1.0
 */
public class AnswerServiceImpl extends LambkitModelServiceImpl<Answer> implements AnswerService {
	
	private Answer DAO = null;
	
	public Answer dao() {
		if(DAO==null) {
			DAO = AopKit.singleton(Answer.class);
		}
		return DAO;
	}

	@Override
	public Co addAnswer(Integer q_id,String  content, String token) {


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
		Answer answer=new Answer();
		answer.setQId(q_id);
		answer.setUname(upmsUser.getRealname());
		answer.setUserId(upmsUser.getUserId().intValue());
		answer.setTime(new Date());
		answer.setDel(0);
		answer.setContent(content);
		answer.setUrl(upmsUser.getAvatar());
       boolean result=answer.save();
       if(result){
		   return Co.ok("data", Co.by("state", "ok").set("answer", answer));
	   }else {
       	return Co.fail("data", Co.by("state", "fail"));
	   }
	}

	@Override
	public List<AnswerNode> all() {
		List<Answer> topics = Answer.service().dao().findAll();//得到所有评论

		List<AnswerNode> topicNodes = new ArrayList<>();//装下所有评论的List
		for(Answer temp : topics){
			AnswerNode topicNode = new AnswerNode();
			topicNode.setAnswer(temp);//把每个Answer变成AnswerNode
			Integer tid = temp.getId();
			//找到是这个评论的所有回复
			List<Reply> thisreplays = new ArrayList<>();
			thisreplays = Reply.service().dao().find(Reply.sql().andTorridEqualTo(tid).andTorrEqualTo(1).example());
			//遍历每个第一层回复
			for(Reply re:thisreplays){
				ReplayNode replayNode = new ReplayNode();
				replayNode.setReply(re);
				topicNode.getReplays().add(replayNode);
				//得到回复的回复
				List<Reply>  replayList = Reply.service().dao().find(Reply.sql().andTorridEqualTo(re.getId()).andTorrEqualTo(0).example());
				//递归
				AddReplayNode(replayList,replayNode);
			}
			topicNodes.add(topicNode);
		}
		//输出
		for(AnswerNode tnode:topicNodes){
			//得到评论
			System.out.println(tnode.getAnswer().toString());
			ShowReplayNodes(tnode.getReplays());
		}
		System.out.println(topicNodes);

		return topicNodes;
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
