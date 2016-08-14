package com.joken.notice.message.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.AxisEngine;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.soap.SOAPConstants;

import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.properties.SystemGlobal;
import com.joken.notice.message.constants.ErrorCode;
import com.joken.notice.message.model.ResultModel;

/**
 * 发送短信工具类
 * <p>
 * 。
 * 
 * @version V0.0.1, Aug 7, 2015
 * @author Hanzibin
 * @since V0.0.1
 */
public class SMSendUtil {
	private final static Logger log = LoggerFactory.getLogger("sms");

	private static SMSendUtil smsUtil = null;
	/** 返回码和对应的描述 */
	private static Map<String, String> errorCodeMsgMap = new HashMap<String, String>();
	/** 产品序列号 */
	private String softwareSerialNo;
	/** 产品密钥 */
	private String softwareKey;
	/** 短信接口地址 */
	private String hostUrl;
	/** xml命名空间 */
	private String targetNameSpace;
	/** 用来指定消息对应的方法 */
	private String messageName;

	static {
		if (smsUtil == null) {
			String serial = SystemGlobal.get("software.serialno");
			String key = SystemGlobal.get("software.key");
			String url = SystemGlobal.get("software.send.message.url");
			String namespace = SystemGlobal.get("software.target.namespace");
			String messagename = SystemGlobal.get("software.target.messagename");
			if(StringUtils.isEmpty(serial)){
				serial = PropertyGet.getApplicationProperty("software.serialno");
				key = PropertyGet.getApplicationProperty("software.key");
				url = PropertyGet.getApplicationProperty("software.send.message.url");
				namespace = PropertyGet.getApplicationProperty("software.target.namespace");
				messagename = PropertyGet.getApplicationProperty("software.target.messagename");
			}
			smsUtil = new SMSendUtil(serial,key,url,namespace,messagename);
		}
		if (errorCodeMsgMap.isEmpty())
			errorCodeMsgMap = PropertyGet
					.getApplicationAllProperty("properties/smscode.properties");
	}

	private SMSendUtil(String serialNo, String key, String hostUrl,
			String targetNameSpace, String messageName) {
		this.softwareSerialNo = serialNo;
		this.softwareKey = key;
		this.hostUrl = hostUrl;
		this.targetNameSpace = targetNameSpace;
		this.messageName = messageName;
		System.out.println("初始化,user:" + softwareSerialNo + ",key:"
				+ softwareKey + ",url:" + hostUrl);
		log.info("初始化,user:" + softwareSerialNo + ",key:" + softwareKey
				+ ",url:" + hostUrl);
	}

	/**
	 * 初始化的方法,防止频繁读取配置文件
	 * 
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 */
	public static SMSendUtil getInstance() {
		return smsUtil;
	}

	/**
	 * 短信发送接口
	 * 
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param mobiles
	 *            手机号列表
	 * @param smsContent
	 *            短信内容
	 * @param smsPriority
	 *            短信优先级 1-5,依次升高
	 * @return ResultModel 发送结果{@link ResultModel}
	 */
	public ResultModel sendSMS(String[] mobiles, String smsContent,
			int smsPriority) {
		log.info("mobiles.size:" + Arrays.toString(mobiles) + ", smsContent:"
				+ smsContent + ", smsPriority:" + smsPriority);
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(hostUrl);
			call.setOperation(initOperationDesc());
			call.setUseSOAPAction(true);
			call.setSOAPActionURI("");
			call.setEncodingStyle(null);
			call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
			call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
			call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
			call.setOperationName(new QName(targetNameSpace, messageName));
			Integer resultCode = (Integer) call.invoke(new Object[] {
					softwareSerialNo, softwareKey, "", mobiles, smsContent, "",
					"gbk", smsPriority, 0 });
			if (resultCode == 0) {
				log.info("send sm success");
				return new ResultModel();
			} else {
				log.info("send sm fail, code:" + resultCode);
				if (errorCodeMsgMap.isEmpty())
					return new ResultModel(resultCode, "调用失败");
				return new ResultModel(resultCode,
						errorCodeMsgMap.get(resultCode + ""));
			}
		} catch (Exception e) {
			log.info("send sm throw Exception :" + e.getMessage());
			return new ResultModel(ErrorCode.CATCH_EXCEPTION);
		}

	}

	/**
	 * 初始化接口名称及所需参数列表
	 * 
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return OperationDesc
	 */
	private static OperationDesc initOperationDesc() {
		OperationDesc oper = new OperationDesc();
		ParameterDesc param;
		QName stringQName = new QName("http://www.w3.org/2001/XMLSchema",
				"string");
		QName intQName = new QName("http://www.w3.org/2001/XMLSchema", "int");
		QName longQName = new QName("http://www.w3.org/2001/XMLSchema", "long");
		Byte paramDescIN = ParameterDesc.IN;
		Class<?> stringClass = java.lang.String.class;
		Class<?> stringListClass = java.lang.String[].class;
		Class<?> intClass = int.class;
		Class<?> longClass = long.class;
		oper.setName("sendSMS");
		param = createParam("arg0", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg1", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg2", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg3", paramDescIN, stringQName, stringListClass);
		oper.addParameter(param);
		param = createParam("arg4", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg5", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg6", paramDescIN, stringQName, stringClass);
		oper.addParameter(param);
		param = createParam("arg7", paramDescIN, intQName, intClass);
		oper.addParameter(param);
		param = createParam("arg8", paramDescIN, longQName, longClass);
		oper.addParameter(param);
		oper.setReturnType(intQName);
		oper.setReturnClass(int.class);
		oper.setReturnQName(new QName("", "return"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		return oper;
	}

	/**
	 * 生成参数
	 * 
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @param paramName
	 * @param paramDescIN
	 * @param qname
	 * @param cls
	 * @return ParameterDesc
	 */
	private static ParameterDesc createParam(String paramName,
			Byte paramDescIN, javax.xml.namespace.QName qname, Class<?> cls) {
		ParameterDesc param = new ParameterDesc(new QName("", paramName),
				paramDescIN, qname, cls, false, false);
		param.setOmittable(true);
		return param;
	}

	public String getSoftwareSerialNo() {
		return softwareSerialNo;
	}

	public void setSoftwareSerialNo(String softwareSerialNo) {
		this.softwareSerialNo = softwareSerialNo;
	}

	public String getSoftwareKey() {
		return softwareKey;
	}

	public void setSoftwareKey(String softwareKey) {
		this.softwareKey = softwareKey;
	}

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

}
