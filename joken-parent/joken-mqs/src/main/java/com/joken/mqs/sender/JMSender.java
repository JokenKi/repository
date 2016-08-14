package com.joken.mqs.sender;

import java.io.IOException;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.jms.pool.PooledSession;

import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.utils.StringUtils;
import com.joken.mqs.factory.MQSessonFactory;

/**
 * 消息发送端
 * 
 * @作者 王波洋
 * @创建日期 2015-10-20
 * @版本 V 1.0
 */
public class JMSender {

	private final static Logger logger = LoggerFactory.getLogger("JMSender");

	/**
	 * 订阅类型
	 * 
	 * @author 波洋
	 * 
	 */
	public enum DestinationType {
		/**
		 * Publish Subscribe messaging 发布订阅消息
		 * 并不保证publisher发布的每条数据，Subscriber都能接受到。
		 */
		TOPIC("TOPIC"),
		/**
		 * Point-to-Point 点对点 保证每条数据都能被receiver接收。
		 */
		QUEUE("QUEUE");

		private String val;

		private DestinationType(String val) {
			this.val = val;
		}

		public String getVal() {
			return this.val;
		}
	}

	public Session session;// 会话
	private Destination dest;// 订阅
	private MessageProducer producer;// 生产者
	private Boolean isCabinet = false;// 机柜订阅
	private Long timeToLive = 600000l;// 消息默认存活时间 配置文件
	private Long defaultDelay = 0l;
	MQSessonFactory sessonFactory;

	private JMSender() {
	}

	private JMSender(String destination, DestinationType destinationType,
			Boolean ifPersist) throws JMSException {
		if (session == null) {
			sessonFactory = MQSessonFactory.getIntance();
			session = sessonFactory.build();
		}
		if (destination.equals("joken.mq.service")) {
			isCabinet = true;
		}

		if (destinationType.getVal().equals("TOPIC")) {
			dest = new ActiveMQTopic(destination);
			producer = session.createProducer(dest);
		} else if (destinationType.getVal().equals("QUEUE")) {
			dest = new ActiveMQQueue(destination);
			producer = session.createProducer(dest);
		}
		if (ifPersist) {
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		} else {
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param text
	 *            消息内容
	 * @throws IOException
	 * @throws JMSException
	 */
	public void send(String text) throws IOException, JMSException {
		this.send(text, timeToLive, defaultDelay);
	}

	/**
	 * 发送消息
	 * 
	 * @param text
	 *            消息内容
	 * @param timeToliveMillis
	 *            消息存活时间
	 * @throws IOException
	 * @throws JMSException
	 */
	public void send(String text, Long timeToliveMillis,Long timeToDelayMillis) throws IOException,
			JMSException {
		if (StringUtils.isEmpty(text)) {
			return;
		}
		logger.info("消息队列开始：" + text + ",expire:" + timeToliveMillis);
		ActiveMQTextMessage mxt = new ActiveMQTextMessage();
		if (isCabinet) {
			mxt.setJMSType("cabinet_command");
		}
		mxt.setText(text);
		if (timeToliveMillis != null) {
			//10分钟后失效
			int toLive = 10 * 60 * 1000;
			mxt.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, timeToliveMillis - toLive);
			producer.setTimeToLive(toLive);
		}
		// 设置延迟时间
		if(!StringUtils.isEmpty(timeToDelayMillis) && timeToDelayMillis!=0l){
			mxt.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, timeToDelayMillis);
		}
		producer.send(mxt);
	}
	

	/**
	 * @author 王波洋
	 * @param destination
	 *            订阅
	 * @param destinationType
	 *            订阅类型
	 * @param ifPersist
	 *            是否持久化
	 * @throws JMSException
	 */
	public static JMSender getInstance(String destination,
			DestinationType destinationType, Boolean ifPersist)
			throws JMSException {
		return new JMSender(destination, destinationType, ifPersist);
	}

	/**
	 * 释放资源
	 */
	public void release() {
		PooledSession amSession = (PooledSession) session;
		try {
			logger.info("session close");
			amSession.close();
		} catch (JMSException e) {
			logger.info("session close", e);
			e.printStackTrace();
		}
		sessonFactory.release();
	}
}
