package com.joken.notice.message.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import com.joken.notice.message.model.ResultModel;

/**
 * 邮件发送工具类
 * <p>。
 * @version V0.0.1, Aug 20, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class SendEmailUtil {
	private final static Logger log = Logger.getLogger(SendEmailUtil.class);
	private static String host,from,username,pwd;
	
	
	private static SendEmailUtil instance = null;

	private SendEmailUtil() {

	}

	/**
	 * 初始化
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return SendEmailUtil
	 */
	public static SendEmailUtil getInstance() {
		if (instance == null) {
			host = PropertyGet.getApplicationProperty("email.host");
			from = PropertyGet.getApplicationProperty("email.from");
			username = PropertyGet.getApplicationProperty("email.username");
			pwd = PropertyGet.getApplicationProperty("email.passwd");
			instance = new SendEmailUtil();
			log.info("init success");
		}
		return instance;
	}

	/**
	 * 发送邮件方法
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param to 收件人
	 * @param cs 抄送
	 * @param ms 密送
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param fileList 附件路径列表
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	public ResultModel send(String to[], String cs[], String ms[], String subject,String content, String fileList[]) {
		log.info("to:"+Arrays.toString(to)+" ,cs:"+Arrays.toString(cs)+" ,ms:"+Arrays.toString(ms)+
				" ,subject:"+subject+" ,content:"+content+" ,fileList:"+Arrays.toString(fileList));
		if(!checkParams(to, cs, ms)){
			log.info("fail, no receiver");
			return new ResultModel(-3, "没有接收人");
		}
		Transport tran = null;
		try {
			Properties pro = new Properties();// System.getProperties();
			pro.put("mail.smtp.auth", "true");
			pro.put("mail.transport.protocol", "smtp");
			pro.put("mail.smtp.host", host);
			pro.put("mail.smtp.port", "25");
			// 建立会话
			Session session = Session.getInstance(pro);
			Message msg = new MimeMessage(session); // 建立信息
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			msg.setFrom(new InternetAddress(from)); // 发件人

			String toList = null;
			String toListcs = null;
			String toListms = null;
			// 发送
			if (!StringUtils.isEmpty(to)) {
				toList = formatMailList(to);
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toList)); // 收件人
			}
			// 抄送
			if (!StringUtils.isEmpty(cs)) {
				toListcs = formatMailList(cs);
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toListcs)); // 抄送人
			}
			// 密送
			if (!StringUtils.isEmpty(ms)) {
				toListms = formatMailList(ms);
				msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toListms)); // 密送人
			}
			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(subject); // 主题
			msg.setText(content); // 内容
			// 显示以html格式的文本内容
			messageBodyPart.setContent(content, "text/html;charset=utf-8");
			multipart.addBodyPart(messageBodyPart);

			// 保存附件
			if (fileList != null && fileList.length>0) {
				ResultModel result = addTach(fileList, multipart);
				if(result.getCode() != 0)
					return result;
			}
			msg.setContent(multipart);
			// 邮件服务器进行验证
			tran = session.getTransport("smtp");
			tran.connect(host, username, pwd);
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			log.info("send email success!");
			return new ResultModel();
		} catch (Exception e) {
			log.info("send throw exception: "+ e.getMessage());
			return new ResultModel(-2, "发送失败.");
		} finally {
            if(tran!=null){
                try {
                	tran.close();
                } catch (MessagingException e) {
                	log.info("tran.close throw exception: "+ e.getMessage());
                    return new ResultModel(-3, "发送失败");
                }
            }
        }
	}

	/**
	 * 参数检查
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param to 收件人
	 * @param cs 抄送
	 * @param ms 密送
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	private boolean checkParams(String to[], String cs[], String ms[]){
		if(StringUtils.isEmpty(to) && StringUtils.isEmpty(cs) && StringUtils.isEmpty(ms))
			return false;
		else
			return true;
	}
	
	/**
	 * 添加多个附件
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param fileList 文件列表
	 * @param multipart 邮件附件
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	private ResultModel addTach(String fileList[], Multipart multipart) {
		try{
			for (int index = 0; index < fileList.length; index++) {
				MimeBodyPart mailArchieve = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(fileList[index]);
				mailArchieve.setDataHandler(new DataHandler(fds));
				mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
				multipart.addBodyPart(mailArchieve);
			}
			return new ResultModel();
		}catch(Exception e){
			log.info("addTack throw exception: "+ e.getMessage());
			return new ResultModel(-1,"附件添加异常.");
		}
	}

	/**
	 * 转换格式
	 * @author Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param mailArray 邮件地址数组
	 * @return String 收件人地址
	 */
	private String formatMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		} else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}

}
