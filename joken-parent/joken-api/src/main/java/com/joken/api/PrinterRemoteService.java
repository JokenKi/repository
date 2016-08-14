/**
 * 
 */
package com.joken.api;

/**
 * 远程调用服务接口
 * 
 * @author 欧阳增高
 * 
 */
public interface PrinterRemoteService {

	/**
	 * 远程调用接口
	 * 
	 * @param json
	 *            调用参数，以json数组格式字符串方式设置,要求格式如下:
	 *            <p>
	 *            [{"taskData":"任务内容","dtuCode":"D11101",
	 *            "taskTimestamp":1439961350562}]
	 *            <ul>
	 *            <li>taskData：[必填]打印任务内容</li>
	 *            <li>dtuCode：[必填]dtu编号</li>
	 *            <li>taskTimestamp：[必填]出餐点时间戳</li>
	 *            </ul>
	 *            </p>
	 * 
	 * @return json格式字符串
	 */
	String insertTask(String json);

	/**
	 * 更新任务状态
	 * 
	 * @param json
	 *            调用参数，以json字符串方式设置
	 *            <p>
	 *            {"dtuCode":"D11101",
	 *            "taskTimestamp":1439961350562,"taskState":2}
	 *            <ul>
	 *            <li>dtuCode：[必填]dtu编号</li>
	 *            <li>taskTimestamp：[必填]出餐点时间戳</li>
	 *            <li>taskState：[必填]状态[0:待下发,1:已下发,2:已完成,3:已失败]</li>
	 *            </ul>
	 *            </p>
	 * @return json格式字符串
	 */
	String updateTaskStatus(String json);

	/**
	 * 打印机在线状态更新
	 * 
	 * @param data
	 *            状态更新参数,从json格式字符串传递
	 *            <p>
	 *            {"dtuCode":"D11101", "isOnline":1}
	 *            <ul>
	 *            <li>dtuCode：[必填]dtu编号</li>
	 *            <li>isOnline：[必填]在线状态[0:离线,1:在线]</li>
	 *            </ul>
	 *            </p>
	 * @return String
	 */
	String updatePrinterOnline(String data);

	/**
	 * 打印机查询结果日志记录
	 * 
	 * @param data
	 *            状态更新参数,从json格式字符串传递
	 *            <p>
	 *            {"dtuCode":"D11101",
	 *            "statusCode":"0000","taskTimestamp":1439961350562}
	 *            <ul>
	 *            <li>dtuCode：[必填]dtu编号</li>
	 *            <li>statusCode：[必填]打外机查询返回状态码</li>
	 *            <li>taskTimestamp：出餐点时间戳,如果存在此参数，则当前状态为打印任务流程中的节点状态</li>
	 *            </ul>
	 *            </p>
	 * @return String
	 */
	String insertPrinterStateLog(String data);

	/**
	 * 向运维人员发送报警短信
	 * 
	 * @param dtuCode
	 *            报警打印机DTU编号
	 * @param msg
	 *            报警短信内容
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-9-11 下午8:51:58
	 */
	String insertServiceNotice(String dtuCode, String msg);
}
