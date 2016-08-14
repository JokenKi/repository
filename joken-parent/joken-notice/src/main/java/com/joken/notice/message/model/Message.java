package com.joken.notice.message.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.joken.notice.message.util.CommonUtil;


/**
 * 微信接口实体
 * <p>。
 * @version V0.0.1, Aug 5, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 用户列表  */
	private List<String> touserList;
	/** 用户  */
	private String touser;
	/** 部门列表  */
	private List<String> topartyList;
	/** 部门  */
	private String toparty;
	/** 标签列表  */
	private List<String> totagList;
	/** 标签  */
	private String totag;
	/** 消息类型,取值范围为text、image、voice、video、file、news */
	private String msgtype;
	/** 企业应用的id  */
	private String agentid;
	/** 是否是保密消息，0表示否，1表示是，默认0  */
	private String safe;
	/** 消息内容, msgtype为text时必填  */
	private String content;
	/** 媒体文件id, msgtype为image、voice、video、file时必填  */
	private String mediaId;
	/** 标题  */
	private String title;
	/** 描述  */
	private String description;
	/** 图文消息  */
	private List<Article> articles = new ArrayList<Article>();
	
	public Message(){
		this.agentid = "0";
		this.safe = "0";
		this.msgtype = "text";
	}
	
	/**
	 * 把收件人列表数组转成字符串
	 * @return String
	 */
	public String getTouser() {
		if(touserList != null && !touserList.isEmpty()){
			return new CommonUtil().formatListToString(touserList, "touser");
		}
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		if(topartyList !=null && !topartyList.isEmpty()){
			return new CommonUtil().formatListToString(topartyList, "topart");
		}
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getTotag() {
		if(totagList !=null && !totagList.isEmpty()){
			return new CommonUtil().formatListToString(totagList, "totag");
		}
		return totag;
	}
	public void setTotag(String totag) {
		this.totag = totag;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getSafe() {
		return safe;
	}
	public void setSafe(String safe) {
		this.safe = safe;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<String> getTouserList() {
		return touserList;
	}

	public void setTouserList(List<String> touserList) {
		this.touserList = touserList;
	}

	public List<String> getTopartyList() {
		return topartyList;
	}

	public void setTopartyList(List<String> topartyList) {
		this.topartyList = topartyList;
	}

	public List<String> getTotagList() {
		return totagList;
	}

	public void setTotagList(List<String> totagList) {
		this.totagList = totagList;
	}
	
	
}
