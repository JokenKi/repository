package com.joken.notice.message;

import com.joken.notice.message.model.ResultModel;
import com.joken.notice.message.service.impl.SendEmailService;

/**
 * 发邮件demo
 * <p>。
 * @version V0.0.1, Aug 20, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class SendEmailDemo {
	public static void main(String args[]) {
		String to[] = { "hanzibin@joken.com.cn"};
		String subject = "测试最后一下";
		String content = "这是邮件内容，仅仅是测试，不需要回复";
		String[] fileList = new String[1];
		fileList[0] = "d:\\程序猿.jpg";
		
		ResultModel result = SendEmailService.send(to,subject, content, fileList);
		System.out.println(result.getCode());
		System.out.println(result.getMsg());
	}

}
