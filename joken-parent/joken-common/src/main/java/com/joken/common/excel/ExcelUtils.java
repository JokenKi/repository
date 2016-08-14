package com.joken.common.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joken.common.exception.BaseException;
import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.model.ResponseModel;
import com.joken.common.properties.MsgProperties;
import com.joken.common.utils.FileUtils;
import com.joken.common.utils.StringUtils;

/**
 * Excel操作工具类--<code>ExcelUtils</code>
 * 
 * @author 欧阳增高
 * @date 2012-6-5 上午01:31:07
 */
public class ExcelUtils {
	/**
	 * 日志
	 */
	private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

	/**
	 * 2003版本excel扩展名
	 */
	private final static String EXCEL_EXTENSION_FOR_2003 = "xls";

	/**
	 * 私有构造器
	 */
	private ExcelUtils() {
	}

	/**
	 * 将Excel数据导入到数据库,导入Excel需要按导入模板设置数据
	 * 
	 * @param excelPath
	 *            需要导入到数据库的excel文件
	 * @return 导入处理响应数据
	 */
	public static ResponseModel importExcel(String excelPath) {
		log.info("Excel数据导入，导入文件：" + excelPath);
		return importExcel(new File(excelPath));
	}

	/**
	 * 将Excel数据导入到数据库,导入Excel需要按导入模板设置数据
	 * 
	 * @param files
	 *            需要导入到数据库的excel文件
	 * @return 导入处理响应数据
	 */
	@SuppressWarnings("unchecked")
	public static ResponseModel importExcel(List<File> files) {
		int succ = 0, fail = 0;
		ResponseModel resp;
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (File file : files) {
			resp = importExcel(file);
			if (!resp.isSuccess()) {
				fail++;
				continue;
			}
			data.addAll((List<Map<String, Object>>) resp.getData());
			succ++;
		}
		if (succ == 0) {
			data.clear();
			return MsgProperties.getFailResp(MsgProperties.getSuccFailAmount(
					succ, fail));
		}
		resp = MsgProperties.getSuccessResp(MsgProperties.getSuccFailAmount(
				succ, fail));
		resp.setData(data);
		return resp;
	}

	/**
	 * 将Excel数据导入到数据库,导入Excel需要按导入模板设置数据
	 * 
	 * @param file
	 *            需要导入到数据库的excel文件
	 * @return 导入处理响应数据
	 */
	public static ResponseModel importExcel(File file) {
		InputStream input = null;
		try {
			if (file == null || !file.isFile()) {
				log.info("Excel数据文档不存在");
				return MsgProperties.getFailResp();
			}
			// 获取Excel文件工作簿
			input = new FileInputStream(file);
			Workbook wb;

			// 判断是2003还是2007及以上版本
			if (EXCEL_EXTENSION_FOR_2003.equalsIgnoreCase(FileUtils
					.getFileExtension(file.getPath().toString()))) {
				wb = new HSSFWorkbook(new BufferedInputStream(input));
			} else {
				wb = new XSSFWorkbook(new BufferedInputStream(input));
			}

			// 获取工作薄数量
			int sheetNum = wb.getNumberOfSheets();
			if (sheetNum == 0) {
				return MsgProperties.getRequestNoData();
			}

			JSONArray result = new JSONArray();

			Sheet sheet;
			// 遍历工作表集合数据
			for (int i = 0; i < sheetNum; i++) {
				sheet = wb.getSheetAt(i);
				result.addAll(readSheet(sheet));
			}
			return MsgProperties.getSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Excel数据导入", e);
			return MsgProperties.getFail(Arrays.toString(e.getStackTrace()));
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取sheet工作表数据
	 * 
	 * @param sheet
	 *            需要读取的sheet工作表
	 * @return 导入处理响应数据
	 * 
	 * @author inkcar
	 * @throws BaseException
	 * @date Aug 4, 2011
	 */
	private static JSONArray readSheet(Sheet sheet) throws BaseException {
		// 工作表总行数,总列数,导入数据开始行数
		int rowLength = sheet.getLastRowNum(), startRow = 2;

		// 获取工作表关联数据库表名或业务名称
		String tableName = sheet.getSheetName();
		if (StringUtils.isEmpty(tableName)) {
			throw new BaseException("Excel导入：" + tableName + "缺少导入业务名");
		}
		// 只有模板的字段定义行及不存在数据列
		if (rowLength < 2) {
			throw new BaseException("Excel导入：" + tableName + "无导入数据");
		}

		// 获取属性名列
		Row propertyRow = sheet.getRow(1);

		// 列数
		short colLength = propertyRow.getLastCellNum();
		// 列名信息
		String[] propertys = new String[colLength];

		int cellIndex = 0;

		// 将导入数据的列名缓存到Map中
		for (int i = 0; i < colLength; i++) {

			// 将字段转换为java命名规则实体属性名
			propertys[cellIndex] = StringUtils.firstLowerCase(StringUtils
					.Tokenizer(parseValue(propertyRow.getCell(i)).toString(),
							"_", true));

			cellIndex++;
		}// 结束字段列集遍历

		JSONArray data = new JSONArray();

		JSONObject tmp;
		// 遍历数据集合
		for (int i = startRow; i <= rowLength; i++) {
			tmp = getRowData(sheet.getRow(i), propertys);
			if (tmp == null) {
				continue;
			}
			data.add(tmp);
		}// 结束行集遍历

		return data;
	}

	/**
	 * 将一行excel数据转为json对象
	 * 
	 * @param row
	 *            需要转换的行数据
	 * @param propertys
	 *            数据列对应的属性名称
	 * @return 转换后的JSON对象
	 * @author 欧阳增高
	 * @date 2016-3-15 上午11:27:52
	 */
	private static JSONObject getRowData(Row row, String[] propertys) {
		if (row == null) {
			return null;
		}
		JSONObject data = new JSONObject(propertys.length);
		Object tmpContent;
		// 遍历标题及属性名列
		for (int i = 0, len = propertys.length; i < len; i++) {
			// 属性内容值
			tmpContent = parseValue(row.getCell(i));
			// 检查excel内容是否为空
			if (StringUtils.isEmpty(tmpContent)) {
				if (i == 0) {
					return null;
				}
				continue;
			}
			// 将值记录到map,用于自定义导入时的实体外属性值获取及异常返回
			data.put(propertys[i], tmpContent);

		}
		return data;
	}

	/**
	 * 获取指定行列的值
	 * 
	 * @param cell
	 *            需要获取值的cell对象
	 * @return 获取到的行列数据值
	 * @author 欧阳增高
	 * @date 2015-12-8 下午3:33:25
	 */
	private static Object parseValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		String result = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				return cell.getDateCellValue();
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil
						.getJavaDate(value);
				return date;
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:// String类型
			result = cell.getRichStringCellValue().toString();
			break;
		}
		return result;
	}
}
