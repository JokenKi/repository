package com.joken.mqs.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 机柜指令枚举类
 * 
 * @Auther Hanzibin
 * @date 3:35:34 PM,Apr 14, 2016
 */
public enum CabinetCMD {

	/**
	 * 普通模式
	 */
	NORMAL(-1, CMDScope.SINGLE.val()),
	/**
	 * 打开柜门
	 */
	Open(2, CMDScope.SINGLE.val()),

	/**
	 * 加热
	 */
	Heat(3, CMDScope.SINGLE.val()),

	/**
	 * 制冷
	 */
	Cool(5, CMDScope.SINGLE.val()),

	/**
	 * 22（获取加热状态）
	 */
	Get_Status_Heat(22, CMDScope.SINGLE.val()),
	// 23（获取制冷状态）
	Get_Status_Cool(23, CMDScope.SINGLE.val()),
	// 24（获取彩灯*状态）
	Get_Status_Color_Lights(24, CMDScope.SINGLE.val()),
	// 25（获取照明状态）
	Get_Status_Lights(25, CMDScope.SINGLE.val()),
	// 26（获取风扇状态）
	Get_Cabinet_Status_Fan(26, CMDScope.ALL.val()),
	// 27（获取消毒状态）
	Get_Cabinet_Status_Disinfection(27, CMDScope.ALL.val()),
	// 28（获取箱体温度）
	Get_Cabinet_Status_Temperature_Box(28, CMDScope.ALL.val()),
	// 29（获取底板温度）
	Get_Cabinet_Status_Temperature_Bottom(29, CMDScope.ALL.val()),

	/**
	 * 第三方预约
	 */
	ReserveThirdPlatform(43, CMDScope.SINGLE.val()),

	;

	private int value;

	private int scope;

	CabinetCMD(int value, int descrption) {
		this.value = value;
		this.scope = descrption;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public int val() {
		return value;
	}

	public int getScope() {
		return scope;
	}

	/**
	 * 获取状态的命令
	 * 
	 * @Auther Hanzibin
	 * @date 10:49:34 AM,Apr 15, 2016
	 * @return
	 */
	public static List<CabinetCMD> getStatusCmd() {
		List<CabinetCMD> list = new ArrayList<CabinetCMD>();
		for (CabinetCMD cmd : values()) {
			if (cmd.name().startsWith("Get")) {
				list.add(cmd);
			}
		}
		return list;
	}

	/**
	 * 根据命令值获取枚举集合
	 * 
	 * @param vals
	 *            需要获取的命令值
	 * @return List&lt;CabinetCMD>
	 */
	public static List<CabinetCMD> getCmdByVal(List<String> vals) {
		if (vals == null || vals.size() == 0) {
			return Collections.emptyList();
		}
		List<CabinetCMD> list = new ArrayList<CabinetCMD>(vals.size());
		for (CabinetCMD cmd : values()) {
			if (vals.indexOf(cmd.val()+"") != -1) {
				list.add(cmd);
			}
		}
		return list;
	}

}
