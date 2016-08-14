package com.joken.mqs.model;

import java.util.Map;

import com.joken.common.freemaker.FreemarkerRender;
import com.joken.common.model.BaseModel;
import com.joken.common.utils.MapUtils;
import com.joken.common.utils.RandomUtils;
import com.joken.common.utils.StringUtils;
import com.joken.mqs.constants.CMDScope;
import com.joken.mqs.constants.CabinetCMD;
import com.joken.mqs.constants.MqExceptionEnum;
import com.joken.mqs.constants.MqsConfig;
import com.joken.mqs.util.MQRequestUtil;

/**
 * 机柜命令
 * @Auther Hanzibin
 * @date 3:58:27 PM,Apr 14, 2016
 */
public class MQCabinetCmd extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	protected String modelId,cabinetId,messageId;
	
	protected Object content;
	
	protected CabinetCMD command;
	
	protected MQCabinetCmd(){
	}
	
	public MQCabinetCmd(String modelId, String cabinetId, Object content, CabinetCMD command){
		this.modelId = modelId;
		this.cabinetId = cabinetId;
		this.content = content;
		this.command = command;
	}
	
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getCabinetId() {
		return cabinetId;
	}
	public void setCabinetId(String cabinetId) {
		this.cabinetId = cabinetId;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String getMessageId() {
		if(StringUtils.isEmpty(messageId)){
			return System.currentTimeMillis() + "" + RandomUtils.getRands(3, "d");
		}
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public int getCommand() {
		return command.val();
	}
	public void setCommand(CabinetCMD command) {
		this.command = command;
	}
	
	@Override
	public String toString(){
		if(!MQRequestUtil.checkCMD(this)){
			 try {
				throw new MQException(MqExceptionEnum.Not_Enough_Params);
			} catch (MQException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		Map<String, Object> params = MapUtils.initMap("command", getCommand());
		params.put("modelId", getModelId());
		params.put("cabinetId", getCabinetId());
		params.put("messageId", getMessageId());
		params.put("content", getContent());
		
		if(command.getScope() == CMDScope.ALL.val()){
			return FreemarkerRender.renderToString(params,
					MqsConfig.MQS_DOOR_CMD_ALL.val());
		}else{
			return FreemarkerRender.renderToString(params,
					MqsConfig.MQS_DOOR_CMD.val());
		}
	}
}
