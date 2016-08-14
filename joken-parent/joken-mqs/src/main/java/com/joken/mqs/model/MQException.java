package com.joken.mqs.model;

import com.joken.mqs.constants.MqExceptionEnum;

/**
 * mq 异常类
 * @Auther Hanzibin
 * @date 4:31:07 PM,Apr 14, 2016
 */
public class MQException extends Exception {

	private static final long serialVersionUID = 1L;
	
	MQException(MqExceptionEnum msg){
		super(msg.val());
	}

}
