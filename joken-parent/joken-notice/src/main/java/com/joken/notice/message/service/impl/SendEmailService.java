package com.joken.notice.message.service.impl;

import com.joken.notice.message.model.ResultModel;
import com.joken.notice.message.util.SendEmailUtil;

/**
 * 邮件发送
 * <p>。
 * @version V0.0.1, Aug 20, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class SendEmailService {
	
	/**
	 * 发送邮件
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param to 收件人
	 * @param cs 抄送
	 * @param ms 密送
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param fileList 附件路径列表
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	public static ResultModel send(String to[], String cs[], String ms[], String subject,String content, String fileList[]){
		return SendEmailUtil.getInstance().send(to, cs, ms, subject, content, fileList);
	}
	
	/**
	 * 发送邮件
	 * @author Hanzibin
	 * @param to 收件人
	 * @param cs 抄送
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param fileList 附件路径列表
	 * @return ResultModel
	 */
	public static ResultModel send(String to[], String cs[], String subject,String content, String fileList[]){
		return SendEmailUtil.getInstance().send(to, cs, null, subject, content, fileList);
	}
	
	/**
	 * 发送邮件
	 * @author Hanzibin
	 * @param to 收件人
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param fileList 附件路径列表
	 * @return ResultModel
	 */
	public static ResultModel send(String to[], String subject,String content, String fileList[]){
		return SendEmailUtil.getInstance().send(to, null, null, subject, content, fileList);
	}
	
	/**
	 * 发送邮件
	 * @author Hanzibin
	 * @param to 收件人
	 * @param subject 主题
	 * @param content 邮件内容
	 * @return ResultModel
	 */
	public static ResultModel send(String to[], String subject,String content){
		return SendEmailUtil.getInstance().send(to, null, null, subject, content, null);
	}
}
