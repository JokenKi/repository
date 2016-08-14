package com.joken.common.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 告警模型类
 * 
 * @author wangby
 *
 */
public class AlarmModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002211892193903575L;

	/**
	 * 告警次数
	 */
	private Integer times = 3;

	private BigDecimal dicimal;

	public BigDecimal getDicimal() {
		return dicimal;
	}

	public void setDicimal(BigDecimal dicimal) {
		this.dicimal = dicimal;
	}

	private Date updateTime;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 递增量
	 */
	private Integer increase = 2;

	/**
	 * 标识
	 */
	private Object key;

	/**
	 * 基数
	 */
	private Integer base = 2;

	/**
	 * 当前告警次数
	 */
	private Integer nowTimes = 1;

	/**
	 * 上一次告警时间
	 */
	private Long lastTime;

	/**
	 * 当前告警次数累加1
	 */
	public void increaseNowTimes() {
		this.nowTimes += 1;
	}

	/**
	 * @return the times
	 */
	public Integer getTimes() {
		return times;
	}

	/**
	 * @param times
	 *            the times to set
	 */
	public void setTimes(Integer times) {
		this.times = times;
	}

	/**
	 * @return the increase
	 */
	public Integer getIncrease() {
		return increase;
	}

	/**
	 * @param increase
	 *            the increase to set
	 */
	public void setIncrease(Integer increase) {
		this.increase = increase;
	}

	/**
	 * @return the key
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Object key) {
		this.key = key;
	}

	/**
	 * @return the base
	 */
	public Integer getBase() {
		return base;
	}

	/**
	 * @param base
	 *            the base to set
	 */
	public void setBase(Integer base) {
		this.base = base;
	}

	/**
	 * @return the nowTimes
	 */
	public Integer getNowTimes() {
		return nowTimes;
	}

	/**
	 * @param nowTimes
	 *            the nowTimes to set
	 */
	public void setNowTimes(Integer nowTimes) {
		this.nowTimes = nowTimes;
	}

	/**
	 * @return the lastTime
	 */
	public Long getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime
	 *            the lastTime to set
	 */
	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

}
