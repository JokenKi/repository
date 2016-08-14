/*
 * @(#)WechatSendMsgUtil.java	V0.0.1 Aug 4, 2015, 2:16:32 PM
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.joken.common.utils.JSONUtils;
import com.joken.notice.message.constants.ErrorCode;
import com.joken.notice.message.model.ResultModel;



/**
 * 微信获取token的接口
 * <p>。
 * @version V0.0.1, Aug 5, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class WechatTokenUtil {
	private final static Logger log = Logger.getLogger(WechatTokenUtil.class);
	
	/** 存放token和过期时间 */
	private static Map<String, Object> tokenMap = new HashMap<String, Object>();
	
	private static WechatTokenUtil wechatTokenUtil = null;
	
    private WechatTokenUtil(){
    	
    }  
    
    /**
     * 初始化方法
     * @return WechatTokenUtil
     */
    public static WechatTokenUtil getInstance(){
		if(wechatTokenUtil == null){
			synchronized (WechatTokenUtil.class) {
				if(wechatTokenUtil == null)
					wechatTokenUtil = new WechatTokenUtil();
			}
		}
		return wechatTokenUtil;
	}
    
    /**
     * 获取token
     * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return ResultModel 发送结果{@link ResultModel}
     */
    public ResultModel getToken(){
    	if(tokenMap.isEmpty() || !tokenMap.containsKey("token") || !CommonUtil.checkExpire(tokenMap)){
    		ResultModel result = null;
			//TODO 防止接口繁忙,若失败调用3次
    		for(int i=0;i<3;i++){
    			result = getTokenFromWechat();
				if(!result.getMsg().toLowerCase().equals("success"))
					continue;
				else
					break;
			}
    		return result;
    	}else{
    		return new ResultModel((String) tokenMap.get("token"));
    	}
    }
    
	/**
	 * 从微信获取token
     * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	private static synchronized ResultModel getTokenFromWechat() {
		if(!tokenMap.isEmpty() && tokenMap.containsKey("token") && CommonUtil.checkExpire(tokenMap)){
			return new ResultModel((String) tokenMap.get("token"));
		}
		
		HttpRequestHandler hrh = new HttpRequestHandler();
		hrh.setUrl(PropertyGet.getApplicationProperty("WECHAT_GET_TOKEN_URL"));
		String jsonStr = hrh.sendGetRequest();
		try {
			JSONObject resultMap = JSONUtils.parse(jsonStr);
			if(resultMap.containsKey("errcode")){//异常情况处理
				log.info("get token from wechat fail, errorcode:"+ resultMap.getInteger("errcode")+" ,errmsg:"+resultMap.getString("errmsg"));
				return new ResultModel((Integer)resultMap.get("errcode"), resultMap.get("errmsg").toString());
			}
			tokenMap.put("token", resultMap.getString("access_token"));
			tokenMap.put("expireDate", CommonUtil.calculateExpires());
			
			return new ResultModel(resultMap.getString("access_token"));
		} catch (Exception e) {
			log.info("get token from wechat throw exception :"+e.getMessage());
			return new ResultModel(ErrorCode.CATCH_EXCEPTION);
		}
	}

}
