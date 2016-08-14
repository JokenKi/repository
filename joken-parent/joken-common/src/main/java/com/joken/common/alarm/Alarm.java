package com.joken.common.alarm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.joken.common.model.AlarmModel;

/**
 * 告警工具类
 * 告警触发时间,默认告警次数为3次,基数为2,增量为2  第一次调用ifAlarm返回true，2的2次方即4分钟后调用返回true，2的3次方即8分钟后调用返回true，其他情况一律为false
 * @author wangby
 */
public class Alarm {

	Map<Object, AlarmModel> alarmList = new ConcurrentHashMap<Object, AlarmModel>();
	
	/**
	 * 移除AlarmModel
	 * @param key
	 */
	public void remove(Object key){
		if (key == null) {
			return;
		}
		
		if(alarmList.containsKey(key)){
			alarmList.remove(key);
		}
	}

	/**
	 * 添加AlarmModel
	 * @param key
	 */
	public void addAlarm(Object key) {
		if (key == null) {
			return;
		}

		AlarmModel alarm = new AlarmModel();
		alarm.setKey(key);
		alarm.setLastTime(System.currentTimeMillis() / (1000 * 60));
		alarm.increaseNowTimes();
		alarmList.put(key, alarm);
	}

	/**
	 * 添加AlarmModel
	 * 
	 * @param key
	 *            键
	 * @param times
	 *            告警次数
	 * @param increase
	 *            增量
	 * @param base
	 *            基数
	 */
	public void addAlarm(Object key, Integer times, Integer increase,
			Integer base) {

		if (key == null) {
			return;
		}

		AlarmModel am = new AlarmModel();
		if (times != null) {
			am.setTimes(times);
		}

		if (increase != null) {
			am.setIncrease(increase);
		}

		if (base != null) {
			am.setBase(base);
		}
		am.setKey(key);
		am.setLastTime(System.currentTimeMillis() / (1000 * 60));
		alarmList.put(key, am);
	}
	
	/**
	 * 是否告警
	 * @param key
	 * @return boolean
	 */
	public Boolean ifAlarm(Object key) {
		if (!alarmList.containsKey(key)) {
			this.addAlarm(key);
			return true;
		}

		AlarmModel alarm = alarmList.get(key);
		
		if (alarm.getTimes() < alarm.getNowTimes()) {
			return false;
		}
		
		if ((System.currentTimeMillis() / (1000 * 60) - alarm.getLastTime()) > Math
				.pow(alarm.getBase(), (alarm.getNowTimes() + 1))) {
			alarm.increaseNowTimes();
			alarm.setLastTime(System.currentTimeMillis() / (1000 * 60));
			return true;
		}

		return false;
	}
}
