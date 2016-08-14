package com.joken.mqs.factory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.jms.pool.PooledConnectionFactory;

import com.joken.base.spring.SpringContextUtil;
/**
 * 消息队列会话工厂类
* @作者 王波洋
* @创建日期 2015-10-20 
* @版本 V 1.0
 */
public class MQSessonFactory {
	private  PooledConnectionFactory pamf= null;//连接池工厂
	private  Connection conn;//连接
	private  Session session;//会话
	private MQSessonFactory(){}
	/**
	 * 获取工厂实例
	 * @return MQSessonFactory
	 * @author 波洋
	 * 2015-10-23
	 */
	public static MQSessonFactory getIntance(){
		return new MQSessonFactory();
	}
	
	/**
	 * 建立连接，获取session
	 * @return Session
	 * @throws JMSException
	 * @author 波洋
	 * 2015-10-23
	 */
	public  Session build() throws JMSException{
		if(pamf == null){
			pamf = (PooledConnectionFactory) SpringContextUtil.getBean("pooledConnectionFactory");
		}
		if(conn == null){
			conn = pamf.createConnection();
			conn.start();
		}
		if(session == null){
			session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		}
		return session;
	}
	
	/**
	 * 释放连接资源
	 * @author 波洋
	 * 2015-10-23
	 */
	public void release(){
		if(conn != null){
			try {
				conn.stop();
				conn.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
