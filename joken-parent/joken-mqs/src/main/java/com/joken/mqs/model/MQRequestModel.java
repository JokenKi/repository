package com.joken.mqs.model;

import com.joken.api.model.MQRequestAbstractModel;
import com.joken.common.utils.StringUtils;
import com.joken.mqs.constants.MqDestinationEnum;
import com.joken.mqs.constants.MqsConfig;
import com.joken.mqs.sender.JMSender.DestinationType;

/**
 * MQS请求模型
 * @Auther Hanzibin
 * @date 4:16:24 PM,Apr 14, 2016
 */
public class MQRequestModel extends MQRequestAbstractModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 命令内容
	 */
	private MQCabinetCmd cmdText; 
	/**
	 * 有效时间
	 */
	private Long expire; 
	private MqDestinationEnum destination; 
	private DestinationType destinationType; 
	private Boolean ifPersist;
	private Long delay;
	
	public MQRequestModel(MQCabinetCmd cmdText, Long expire, Long delay, MqDestinationEnum destination, DestinationType destinationType, Boolean ifPersist){
		this.cmdText = cmdText;
		this.expire = expire;
		this.destination = destination;
		this.destinationType = destinationType;
		this.ifPersist = ifPersist;
		this.delay = delay;
	}
	
	/**
	 * 初始化
	 * 订阅地址： joken.mq.service 
	 * 发送方式：  topic
	 * 是否持久化： 是
	 * @param cmdText
	 * @param expire
	 * @param delay
	 */
	public MQRequestModel(MQCabinetCmd cmdText, Long expire, Long delay){
		this.cmdText = cmdText;
		this.expire = expire;
		this.delay = delay;
	}
	
	public MQRequestModel(MQCabinetCmd cmdText, Long delay){
		this.cmdText = cmdText;
		this.delay = delay;
	}
	
	public MQRequestModel(MQCabinetCmd cmdText){
		this.cmdText = cmdText;
	}
	
	public MQCabinetCmd getCmdText() {
		return cmdText;
	}
	public void setCmdText(MQCabinetCmd cmdText) {
		this.cmdText = cmdText;
	}
	
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	public String getDestination() {
		if(StringUtils.isEmpty(destination)){
			return MqsConfig.MQ_Destination_Cabinet.val();
		}
		return destination.val();
	}
	public void setDestination(MqDestinationEnum destination) {
		this.destination = destination;
	}
	public DestinationType getDestinationType() {
		if(destinationType == null){
			return  DestinationType.TOPIC;
		}
		return destinationType;
	}
	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}
	public Boolean getIfPersist() {
		if(ifPersist == null)
			return true;
		return ifPersist;
	}
	public void setIfPersist(Boolean ifPersist) {
		this.ifPersist = ifPersist;
	}
	
	public void setExpire(String expire) {
		if(!StringUtils.isEmpty(expire)){
			this.expire = Long.parseLong(expire);
		}
	}
	
	public Long getExpire() {
		if(StringUtils.isEmpty(expire)){
			return Long.getLong(MqsConfig.MQ_SERVICE_EXPIRE.val());
		}
		return expire;
	}
	
	public void setDelay(Long delay) {
		this.delay = delay;
	}
	public void setDelay(String delay) {
		if(!StringUtils.isEmpty(delay)){
			this.delay = Long.parseLong(delay);
		}
	}
	
	public Long getDelay() {
		if(StringUtils.isEmpty(delay)){
			return 0l;
		}
		return delay;
	}
	
}
