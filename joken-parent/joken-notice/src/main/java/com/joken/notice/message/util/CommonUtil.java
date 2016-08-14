package com.joken.notice.message.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joken.common.utils.JSONUtils;
import com.joken.notice.message.model.Message;

/**
 * 消息相关的工具类
 * <p>。
 * @version V0.0.1, Aug 5, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class CommonUtil {

	/**
	 * 把用户列表转成字符串
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param list 字符串集合
	 * @param type 类型
	 * @return String 字符串
	 */
	public String formatListToString(List<String> list, String type){
		if(list == null || list.isEmpty())
			return null;
		StringBuffer toUser = new StringBuffer("");
		int i = 1;
		String user = null;
		
		int limitSize = list.size();
		if("TOPARTY".equals(type.toUpperCase()))
			limitSize = Integer.parseInt(PropertyGet.getApplicationProperty("TOPARTY_LIMIT_SIZE"));
		if("TOUSER".equals(type.toUpperCase()))
			limitSize = Integer.parseInt(PropertyGet.getApplicationProperty("TOUSER_LIMIT_SIZE"));
		
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		for(;i<list.size()+1;i++){
			user = list.get(i-1);
			toUser.append(user).append("|"); 
			if(i==limitSize)//成员ID最多1000个
				break;
		}
		return toUser.substring(0, toUser.length()-1);
	}

	/**
	 * 计算两小时候的时间
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return Date 时间 
	 */
	public static Date calculateExpires(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, 2);
		Date expireDate = cal.getTime();
		return expireDate;
	}
	
	/**
	 * 检查token是否过期
	 * 未过期返回true
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param tokenMap map
	 * @return boolean 
	 */
	public static boolean checkExpire(Map<String, Object> tokenMap){
		if(!tokenMap.containsKey("token") || !tokenMap.containsKey("expireDate"))
			return false;
		if(new Date().before((Date) tokenMap.get("expireDate"))){
			return true;
		}else
			return false;
	}
	
	/**
	 * 检查接口参数
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param entity Message实体类{@link Message}
	 * @return boolean
	 */
	public static boolean checkMessageEntity(Message entity){
		if(StringUtils.isEmpty(entity.getMsgtype())){
			return false;
		}
		
		if(StringUtils.isEmpty(entity.getAgentid())){
			return false;
		}
		
		if ("text".equals(entity.getMsgtype().toLowerCase()) && StringUtils.isEmpty(entity.getContent())) {
			return false;
		} else if (("image".equals(entity.getMsgtype().toLowerCase()) || "voice".equals(entity.getMsgtype().toLowerCase()) 
				|| "video".equals(entity.getMsgtype().toLowerCase()) || "file".equals(entity.getMsgtype().toLowerCase())
				) && StringUtils.isEmpty(entity.getMediaId())) {
			return false;
		} else if ("news".equals(entity.getMsgtype().toLowerCase()) && StringUtils.isEmpty(entity.getArticles())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 检查消息类型
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param entity Message实体类{@link Message}
	 * @return boolean
	 */
	public static boolean checkMessageType(Message entity){
		Map<String, Object> typeMap = new HashMap<String, Object>();
		Object obj = new Object();
		typeMap.put("text", obj);
		typeMap.put("image", obj);
		typeMap.put("voice", obj);
		typeMap.put("video", obj);
		typeMap.put("file", obj);
		typeMap.put("news", obj);
		if(typeMap.containsKey(entity.getMsgtype().toLowerCase()))
			return true;
		else
			return false;
	}
	
	/**
	 * TODO 针对不支持的消息类型,做处理
	 * 把message转成json字符串
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param message Message实体类{@link Message}
	 * @return json字符串
	 */
	public String formatMessagetoJsonString(Message message) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("agentid", message.getAgentid());
		dataMap.put("msgtype", message.getMsgtype());

		if (!StringUtils.isEmpty(message.getTouser()))
			dataMap.put("touser", message.getTouser());

		if (!StringUtils.isEmpty(message.getToparty()))
			dataMap.put("toparty", message.getToparty());

		if (!StringUtils.isEmpty(message.getTotag()))
			dataMap.put("totag", message.getTotag());
		
		if (!StringUtils.isEmpty(message.getSafe()))
			dataMap.put("safe", message.getSafe());
		
		Map<String, Object> specialDataMap;
		if ("text".equals(message.getMsgtype().toLowerCase())) {
			specialDataMap = new HashMap<String, Object>();
			specialDataMap.put("content", message.getContent());
			dataMap.put("text", specialDataMap);
		} else if ("image".equals(message.getMsgtype().toLowerCase())) {
			specialDataMap = new HashMap<String, Object>();
			specialDataMap.put("media_id", message.getMediaId());
			dataMap.put("image", specialDataMap);
		} else if ("voice".equals(message.getMsgtype().toLowerCase())) {
			specialDataMap = new HashMap<String, Object>();
			specialDataMap.put("media_id", message.getMediaId());
			dataMap.put("voice", specialDataMap);
		} else if ("video".equals(message.getMsgtype().toLowerCase())) {
			specialDataMap = new HashMap<String, Object>();
			specialDataMap.put("media_id", message.getMediaId());
			specialDataMap.put("title", message.getTitle());
			specialDataMap.put("description", message.getDescription());
			dataMap.put("video", specialDataMap);
		} else if ("file".equals(message.getMsgtype().toLowerCase())) {
			specialDataMap = new HashMap<String, Object>();
			specialDataMap.put("media_id", message.getMediaId());
			dataMap.put("file", specialDataMap);
		}
		return JSONUtils.toJSONString(dataMap).toString();
	}
}
