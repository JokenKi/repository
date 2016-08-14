package com.joken.mqs.model;

import java.util.Map;

import com.joken.common.freemaker.FreemarkerRender;
import com.joken.common.utils.MapUtils;
import com.joken.mqs.constants.CabinetCMD;
import com.joken.mqs.constants.MqsConfig;

/**
 * 预约机柜的命令
 * @Auther Hanzibin
 * @date 3:44:01 PM,Apr 15, 2016
 */
public class MQCabinetReserveCmd extends MQCabinetCmd{

	private static final long serialVersionUID = 1L;
	
	private int serial;
	
	private Integer reserveId;
	
	public MQCabinetReserveCmd(String modelId, String cabinetId, Object content, 
			CabinetCMD command, int serial, Integer reserveId){
		this.modelId = modelId;
		this.cabinetId = cabinetId;
		this.content = content;
		this.command = command;
		this.serial = serial;
		this.reserveId = reserveId;
	}
	
	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public Integer getReserveId() {
		return reserveId;
	}

	public void setReserveId(Integer reserveId) {
		this.reserveId = reserveId;
	}

	public Object getContent() {
		return content;
	}

	public void setContentParamsMap(Object content) {
		this.content = content;
	}


	@Override
	public String toString(){
		Map<String, Object> params = MapUtils.initMap("command", getCommand());
		params.put("serial", getSerial());
		params.put("modelId", getModelId());
		params.put("cabinetId", getCabinetId());
		//__mqs__expire
		params.put("content", getContent());
		params.put("reserveId", getReserveId());
		params.put("messageId", getMessageId());
		if(command.val() == CabinetCMD.ReserveThirdPlatform.val()){
			return FreemarkerRender.renderToString(params, MqsConfig.MQS_CMD.val());
		}
		return null;
	}
}
