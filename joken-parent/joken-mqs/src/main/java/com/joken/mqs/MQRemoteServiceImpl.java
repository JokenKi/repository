package com.joken.mqs;

import org.springframework.stereotype.Service;

import com.joken.api.MQRemoteService;
import com.joken.api.model.MQRequestAbstractModel;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.mqs.model.MQRequestModel;
import com.joken.mqs.sender.JMSender;
import com.joken.mqs.util.MQRequestUtil;

/**
 * mqs发送实现类
 * @Auther Hanzibin
 * @date 11:19:02 AM,Apr 15, 2016
 */
@Service("MQRemoteService")
public class MQRemoteServiceImpl implements MQRemoteService{

	private final static Logger logger = LoggerFactory.getLogger("JMSender");
	
	/**
	 * 发送mq消息
	 * @Auther Hanzibin
	 * @date 11:19:12 AM,Apr 15, 2016
	 * @param request {@link MQRequestModel} 机柜mq消息请求模型类
	 * @return boolean  发送状态
	 */
	public boolean send(MQRequestAbstractModel model){
		MQRequestModel request = (MQRequestModel) model;
		if(!MQRequestUtil.checkCMD(request.getCmdText())){
			logger.error("消息队列发送失败:cmdText参数不规范");
			return false;
		}
		JMSender js = null;
		try {
			logger.info("消息队列发送:" + request.getCmdText());
			int amount = 0;
			while (true) {
				js = JMSender.getInstance(request.getDestination(), request.getDestinationType(), request.getIfPersist());
				if (js != null || amount == 20) {
					break;
				}
				amount++;
				Thread.sleep(1000);
			}
			if (js == null) {
				logger.error("消息队列发送失败:" + request.getCmdText());
				return false;
			}
			js.send(request.getCmdText().toString(), request.getExpire(), request.getDelay());
			logger.info("消息队列发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("消息队列发送失败", e);
			return false;
		} finally {
			if (js != null) {
				js.release();
			}
		}
		return true;
	}
	
}
