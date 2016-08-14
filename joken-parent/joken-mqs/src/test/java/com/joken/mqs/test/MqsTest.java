package com.joken.mqs.test;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.jms.pool.PooledConnectionFactory;

import com.joken.base.spring.SpringContextUtil;
import com.joken.mqs.sender.JMSender;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class MqsTest 
    extends TestCase
{
	/**
	 * 测试连接
	 */
	public void test2() {
		JMSender sender;
		try {
			for(int i = 0;i<1000;i++){
				sender = JMSender.getInstance("joken.mq.service", JMSender.DestinationType.TOPIC, true);
				System.out.println(sender.session);
				try {
					sender.send("{'modelId':'1','cabinetId':'92','command':10,'content':'0#244|28|11:30:00','messageId':318230}");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//			sender.send("{'modelId':'1','cabinetId':'92','command':10,'content':'0#244|28|11:30:00','messageId':318230}");
		} catch (JMSException e){
			e.printStackTrace();
		}
	}
	private static PooledConnectionFactory pamf =  (PooledConnectionFactory) SpringContextUtil.getBean("pooledConnectionFactory");
	
	/**
	 * 初步测试
	 * @throws JMSException
	 */
	public void test() throws JMSException{
		ActiveMQConnectionFactory amf = (ActiveMQConnectionFactory) pamf.getConnectionFactory();
		amf.setUseAsyncSend(true);
		amf.setOptimizeAcknowledge(true);
		amf.setOptimizeAcknowledgeTimeOut(1000);
		amf.getRedeliveryPolicy().setRedeliveryDelay(1);
		amf.getRedeliveryPolicy().setInitialRedeliveryDelay(1);
		amf.getRedeliveryPolicy().setMaximumRedeliveries(-1);
		Connection cn = amf.createConnection();
		cn.start();
		if(cn instanceof ActiveMQConnection){
			ActiveMQConnection acn = (ActiveMQConnection)cn;
			System.out.println(acn.getStats());
			System.out.println(acn.getConnectionStats());
			System.out.println(acn.getBrokerInfo());
		}
		Session se = cn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		Topic topic = se.createTopic("joken.mq.service");
		MessageProducer producer = se.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		ActiveMQTextMessage mxt = new ActiveMQTextMessage();
		mxt.setExpiration(100000);
		mxt.setJMSType("cabinet_command");
		mxt.setText("{'modelId':'1','cabinetId':'92','command':10,'content':'0#244|28|11:30:00','messageId':318230}");
		producer.send(mxt);
	}
}
