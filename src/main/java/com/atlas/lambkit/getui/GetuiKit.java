package com.atlas.lambkit.getui;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.jfinal.kit.StrKit;

public class GetuiKit {

	/**
	 * 测试
	 * @category 测试
	 * @param config
	 */
	@SuppressWarnings("deprecation")
	public static void test(GetuiConfig config) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());

		// 定义"点击链接打开通知模板"，并设置标题、内容、链接
		LinkTemplate template = new LinkTemplate();
		template.setAppId(config.getAppId());
		template.setAppkey(config.getAppKey());
		template.setTitle("请填写通知标题");
		template.setText("请填写通知内容");
		template.setUrl("http://getui.com");

		List<String> appIds = new ArrayList<String>();
		appIds.add(config.getAppId());

		// 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
		AppMessage message = new AppMessage();
		message.setData(template);
		message.setAppIdList(appIds);
		message.setOffline(true);
		message.setOfflineExpireTime(1000 * 600);

		IPushResult ret = push.pushMessageToApp(message);
		System.out.println(ret.getResponse().toString());
	}

	/**
	 * 向单个clientid或别名用户推送消息。
	 * @category 对单个用户推送消息
	 * @see 场景1：某用户发生了一笔交易，银行及时下发一条推送消息给该用户。
	 *      场景2：用户定制了某本书的预订更新，当本书有更新时，需要向该用户及时下发一条更新提醒信息。
	 *      这些需要向指定某个用户推送消息的场景，即需要使用对单个用户推送消息的接口。
	 * @param config
	 * @param client
	 */
	public static IPushResult pushSingle(GetuiConfig config, ClientModel client) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(client.getTemplate());
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(config.getAppId());
		if(StrKit.notBlank(client.getClientId())) {
			target.setClientId(client.getClientId());
		} else if(StrKit.notBlank(client.getAlias())) {
			target.setAlias(client.getAlias());
		}
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret == null) {
			System.out.println("服务器响应异常");
		}
		System.out.println("00000000000"+ret.getResponse().toString());
		return ret;
	}

	/**
	 * 用于一次创建提交多个单推任务。
	 * @category 批量单推功能
	 * @param config
	 * @param clients
	 */
	public static IPushResult bathPushSingle(GetuiConfig config, List<ClientModel> clients) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		IBatch batch = push.getBatch();
		IPushResult ret = null;
		try {

			for (ClientModel client : clients) {
				SingleMessage message = new SingleMessage();
				message.setData(client.getTemplate());
				message.setOffline(true);
				message.setOfflineExpireTime(1 * 1000);

				// 设置推送目标，填入appid和clientId
				Target target = new Target();
				target.setAppId(config.getAppId());
				if(StrKit.notBlank(client.getClientId())) {
					target.setClientId(client.getClientId());
				} else if(StrKit.notBlank(client.getAlias())) {
					target.setAlias(client.getAlias());
				}
				batch.add(message, target);
			}
			ret = batch.submit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 上传clientid或别名列表，对列表中所有clientid或别名用户进行消息推送，
	 * 如果仅对单个用户推送务必使用单推接口，否则会严重影响推送性能， 如果对少量甚至几个用户推送同样的消息，建议使用单推实现，性能会更高。
	 * @category 对指定列表用户推送消息
	 * @see 场景1，对于抽奖活动的应用，需要对已知的某些用户推送中奖消息，就可以通过clientid列表方式推送消息。
	 *      场景2，向新客用户发放抵用券，提升新客的转化率，就可以事先提取新客列表，将消息指定发送给这部分指定CID用户。
	 * @param config
	 * @param clientGroup
	 */
	public static IPushResult pushList(GetuiConfig config, ClientGroupModel clientGroup) {
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin_pushList_needDetails", "true");
		// 配置返回每个别名及其对应cid的用户状态，可选
		// System.setProperty("gexin_pushList_needAliasDetails", "true");
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		ListMessage message = new ListMessage();
		message.setData(clientGroup.getTemplate());
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 配置推送目标
		List<Target> targets = new ArrayList<Target>();
		
		if(clientGroup.getClientIds()!=null && clientGroup.getClientIds().size() > 0) {
			for (String clientId : clientGroup.getClientIds()) {
				Target target = new Target();
				target.setAppId(config.getAppId());
				target.setClientId(clientId);
				// target.setAlias(Alias1);
				targets.add(target);
			}
		} else if(clientGroup.getAliasList()!=null && clientGroup.getAliasList().size() > 0) {
			for (String alias : clientGroup.getAliasList()) {
				Target target = new Target();
				target.setAppId(config.getAppId());
				target.setAlias(alias);
				targets.add(target);
			}
		}
		// taskId用于在推送时去查找对应的message
		String taskId = push.getContentId(message);
		return push.pushMessageToList(taskId, targets);
	}

	/**
	 * 对单个指定应用的所有用户群发推送消息。
	 * @category 对指定应用群推消息
	 * @see 场景1，某app周年庆，群发消息给该app的所有用户，提醒用户参加周年庆活动。
	 * @param config
	 * @param acm
	 * @param taskGroupName
	 * @return
	 */
	public static IPushResult pushToApp(GetuiConfig config, AppConditionModel acm, String taskGroupName) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		AppMessage message = new AppMessage();
		message.setData(acm.getTemplate());
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(config.getAppId());
		message.setAppIdList(appIdList);
		// 推送给App的目标用户需要满足的条件
		// AppConditions cdt = new AppConditions();
		message.setConditions(acm.getConditions());
		if(StrKit.notBlank(taskGroupName)) {
			return push.pushMessageToApp(message, taskGroupName);
		} else {
			return push.pushMessageToApp(message);
		}
	}

	/**
	 * tag即为用户标签，个推提供了服务端和客户端接口，允许app针对每个clientid设置标签。用户的喜好、习惯、性别、年龄段等信息，这些信息均可以做为用户分组的标签。
	 * @category 对指定用户设置tag属性
	 * @see 
	 * 通过标签（tag）方式实现用户分组，将消息发给指定标签用户，更进一步筛选了用户，实现精细化运营。
	 * @param config
	 * @param clientId
	 * @param tagList
	 */
	public static IQueryResult setClientTag(GetuiConfig config, String clientId, List<String> tagList) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		/*
		 * List<String> tagList = new ArrayList<String>();
		 * tagList.add(String.valueOf("18-20")); tagList.add("杭州");
		 * tagList.add("美女");
		 */
		return push.setClientTag(config.getAppId(), clientId, tagList);
	}

	/**
	 * 获取指定用户的tag属性
	 * @category 获取指定用户的tag属性
	 * @see 
	 * 场景1：一个用户经常看电影，给该用户打一个“movie”标签，当有最新电影更新了，可给tag为movie的这一群用户推送消息。
	 * 场景2：音频播放器应用。对不同音乐类型喜好的人群推送不同类别的新音乐通知。
	 * @param config
	 * @param clientId
	 */
	public static IPushResult getUserTags(GetuiConfig config, String clientId) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.getUserTags(config.getAppId(), clientId);
	}

	/**
	 * 调用此接口可获取用户状态，如在线不在线，cid和appid是否对应，appkey是否正确等。
	 * @category 获取用户状态
	 * @param config
	 * @param clientId
	 */
	public static IQueryResult getClientIdStatus(GetuiConfig config, String clientId) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.getClientIdStatus(config.getAppId(), clientId);
	}

	/**
	 * 将指定cid列表中的用户加入黑名单
	 * @category 添加黑名单用户
	 * @param config
	 * @param cidList
	 */
	public static IPushResult addCidListToBlk(GetuiConfig config, List<String> cidList) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.addCidListToBlk(config.getAppId(), cidList);
	}

	/**
	 * 将指定cid列表中的用户加入/移除黑名单
	 * @category 移除黑名单用户
	 * @param config
	 * @param cidList
	 */
	public static IPushResult restoreCidListFromBlk(GetuiConfig config, List<String> cidList) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.restoreCidListFromBlk(config.getAppId(), cidList);
	}

	/**
	 * 调用此接口查询推送数据，可查询消息有效可下发总数，消息回执总数，用户点击数等结果。
	 * @category 获取推送结果
	 * @param config
	 * @param taskId
	 * @return
	 * {
    "result": "",
    "taskId": "",
    "GT": {//个推下发报表
        "sent": "",//成功下发数
        "displayed": "",//展示数
        "clicked": "",//点击
        "feedback": "",//到达
        "result": ""//成功(ok)或错误信息
    }，
    "APN": {//ios apn下发
        "sent": "",//下发
        "displayed": "",//apns展示
        "clicked": "",//点击
        "result": ""//成功(ok)或错误信息
    }
}
	 */
	public static IPushResult getPushResult(GetuiConfig config, String taskId) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.getPushResult(taskId);
		/*
		Map<String, Object> res = (Map<String, Object>) push.getPushResult(taskId).getResponse();
		for (Map.Entry<String, Object> entry : res.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		*/
	}
	
	/**
	 * 调用此接口批量查询推送数据，可查询消息有效可下发总数，消息回执总数，用户点击数结果。
	 * @category 批量获取推送结果
	 * @param config
	 * @param taskIdList
	 * @return
	 * {
  "result": "",
  "resultList":[
      {
        "taskId":"",
        "GT": {//个推下发报表
            "sent": "",//成功下发数
            "displayed": "",//展示数
            "clicked": "",//点击
            "feedback": "",//到达
            "result": ""//成功(ok)或错误信息
        },
        "APN": {//ios apns下发
            "sent": "",//下发
            "displayed": "",//apns展示
            "clicked": "",//点击
            "result": ""//成功(ok)或错误信息
        }
      }
  ]
}
	 */
	public static IPushResult getPushResultByTaskidList(GetuiConfig config, List<String> taskIdList) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.getPushResultByTaskidList(taskIdList);
	}
	
	/**
	 * 调用此接口可以获取某个应用单日的用户数据（用户数据包括：新增用户数，累计注册用户总数，在线峰值，日联网用户数）（目前只支持查询1天前的数据）
	 * @category 获取单日用户数据
	 * @param config
	 * @param date
	 * @return
	 * result	-	成功：ok
	 * data	-	查询数据对象
	 * appId	data	请求的AppId
	 * date	data	查询的日期（格式：yyyyMMdd）
	 * newRegistCount	data	新注册用户数
	 * registTotalCount	data	累计注册用户数
	 * activeCount	data	活跃用户数
	 * onlineCount	data	在线用户数使用方法
	 */
	public static IQueryResult queryAppUserDataByDate(GetuiConfig config, String date) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.queryAppUserDataByDate(config.getAppId(), date);
	}
	
	/**
	 * 调用此接口可以获取某个应用单日的推送数据（推送数据包括：发送总数，在线发送数，接收数，展示数，点击数）（目前只支持查询1天前的数据）
	 * @category 获取单日推送数据
	 * @param config
	 * @param date
	 * @return
	 * {
    "result": "",
    "data": {
        "appId":"",
        "date":""
    },
    "GT": {//个推下发报表
        "sent": "",//成功下发数
        "displayed": "",//展示数
        "clicked": "",//点击
        "feedback": "",//到达
        "result": ""//成功(ok)或错误信息
    },
    "APN": {//ios apns下发
        "sent": "",//下发
        "displayed": "",//apns展示
        "clicked": "",//点击
        "result": ""//成功(ok)或错误信息
    }
}
	 */
	public static IQueryResult queryAppPushDataByDate(GetuiConfig config, String date) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.queryAppPushDataByDate(config.getAppId(), date);
	}
	
	/**
	 * 通过接口查询当前时间一天内的在线数（十分钟一个点，一小时六个点）
	 * @category 获取24小时在线用户数
	 * @param config
	 * @return
	 * appId	String	用户appId
	 * onlineStatics	Map<String, long>	24小时用户在线数统计
	 */
	public static IQueryResult getLast24HoursOnlineUserStatistics(GetuiConfig config) {
		IGtPush push = new IGtPush(config.getHost(), config.getAppKey(), config.getMasterSecret());
		return push.getLast24HoursOnlineUserStatistics(config.getAppId());
	}
}
