package com.joken.common.excel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.joken.common.freemaker.FreemarkerRender;
import com.joken.common.utils.FileUtils;
import com.joken.common.utils.StringUtils;

/**
 * Excel导出工具
 * 
 * @version V1.0.0, 2016-3-15
 * @author 杨艳芳
 * @since V1.0.0
 */
public class ExcelExpUtil {
	/**
	 * 通过模板的名称，模板的数据，模板的位置，拿到一个填充好的工作簿对象
	 * 
	 * @param name
	 *            模板的名称
	 * @param data
	 *            模板的数据
	 * @param path
	 *            模板的位置
	 * @return Excel文件对象
	 * @date 2016-3-15 上午11:21:53
	 */
	@SuppressWarnings("unchecked")
	public static File excelExp(String name, Object data, String path) {
		String path1 = path + "\\" + name + ".xml";
		String context = null;
		// 设置当前模板缓存
		try {
			// 拿到模板里面的内容
			String conString = com.joken.common.utils.FileUtils.readText(path1);
			FreemarkerRender.setTemplate(name, conString);
			// 组装模板渲染数据
			Map<String, Object> target = new HashMap<String, Object>();

			target.put("data", data);
			// 渲染模板，拿到的context是最后生成的全部数据，是字符串类型的数据，把拿到的这个格式的数据放到名字是这个的模板里
			context = "<" + FreemarkerRender.renderToName(target, name);
			context = context.replaceAll("&", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SAXBuilder builder = new SAXBuilder();
		XSSFWorkbook wb = null;
		Document parse = null;
		try {
			parse = builder.build(new ByteArrayInputStream(context
					.getBytes("utf-8")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		XSSFSheet sheet = null;
		List<Integer> wantSumColumn = new ArrayList<Integer>();
		try {
			wb = new XSSFWorkbook();
			// 创建工作簿
			sheet = wb.createSheet("sheet0");
			// 获取xml文件根节点
			Element root = parse.getRootElement();
			// 获取模板名称
			int rownum = 0;
			int column = 0;
			// 设置列宽
			Element colgroup = root.getChild("colgroup");
			List<Element> cols = colgroup.getChildren("col");
			for (int i = 0; i < cols.size(); i++) {
				Element col = cols.get(i);
				Attribute width = col.getAttribute("width");
				String unit = width.getValue().replaceAll("[0-9,\\.]", "");
				String value = width.getValue().replaceAll(unit, "");
				int v = 0;
				if (StringUtils.isBlank(unit) || "px".endsWith(unit)) {
					v = Math.round(Float.parseFloat(value) * 37F);
				} else if ("em".endsWith(unit)) {
					v = Math.round(Float.parseFloat(value) * 267.5F);
				}
				sheet.setColumnWidth(i, v);
			}
			// 设置标题
			Element title = root.getChild("title");
			List<Element> trs = title.getChildren("tr");
			XSSFCellStyle cellStyle = null;
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				List<Element> tds = tr.getChildren("td");
				XSSFRow row = sheet.createRow(rownum);
				cellStyle = wb.createCellStyle();
				cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				for (column = 0; column < tds.size(); column++) {
					Element td = tds.get(column);
					XSSFCell cell = row.createCell(column);
					Attribute rowSpan = td.getAttribute("rowspan");
					Attribute colSpan = td.getAttribute("colspan");
					Attribute value = td.getAttribute("value");
					if (value != null) {
						String val = value.getValue();
						cell.setCellValue(val);
						int rspan = rowSpan.getIntValue() - 1;
						int cspan = colSpan.getIntValue() - 1;
						// 设置字体
						XSSFFont font = wb.createFont();
						font.setFontHeight((short) 200);
						cellStyle.setFont(font);
						cellStyle.setAlignment(HorizontalAlignment.CENTER);
						cell.setCellStyle(cellStyle);
						// 合并单元格居中
						sheet.addMergedRegion(new CellRangeAddress(rspan,
								rspan, 0, cspan));
					}
				}
				rownum++;
			}
			// 设置表头
			Element thead = root.getChild("thead");
			trs = thead.getChildren("tr");
			// 想要求和的列
			for (int i = 0; i < trs.size(); i++) {
				Element tr = trs.get(i);
				XSSFRow row = sheet.createRow(rownum);
				List<Element> ths = tr.getChildren("th");
				for (column = 0; column < ths.size(); column++) {
					Element th = ths.get(column);
					Attribute valueAttr = th.getAttribute("value");
					if (i != 0) {
						Attribute valueType = th.getAttribute("type");
						XSSFCell cell = row.createCell(column);
						if (valueAttr != null) {
							cellStyle
									.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
							cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
							cell.setCellStyle(cellStyle);
							String value1 = valueType.getValue();
							if (value1.equals("double")) {
								if (valueAttr.getValue() == null
										|| valueAttr.getValue().equals("")) {

									cell.setCellValue(0);
								} else {
									double value = valueAttr.getDoubleValue();
									cell.setCellValue(value);
								}
								wantSumColumn.add(column);
							} else if (value1.equals("string")) {
								String value = valueAttr.getValue();
								cell.setCellValue(value);
							}
						}
					} else {
						XSSFCell cell = row.createCell(column);
						if (valueAttr != null) {
							cellStyle
									.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
							cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
							cell.setCellStyle(cellStyle);
							String value = valueAttr.getValue();
							cell.setCellValue(value);
						}
					}
				}
				rownum++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[] l = { 0, 1, 10, 11 };
		wb = rowSpan(l, wb);

		sumOneColumn(wantSumColumn, wb);
		// path=path.substring(0, 18);
		String xls = path + "\\" + FileUtils.getRandsFileName("xlsx");
		return expExcel(xls, wb);
	}

	/**
	 * 通过一个目录和工作簿创建一个excel文件
	 * 
	 * @param realPath
	 *            存放目录
	 * @param wb
	 *            工作簿 对象
	 * @return excel文件对象
	 * @date 2016-3-15 上午11:23:10
	 */
	private static File expExcel(String realPath, XSSFWorkbook wb) {
		// String realPath=path+".xlsx";
		File file2 = new File(realPath);
		FileOutputStream stream;
		try {
			file2.createNewFile();
			// 将excel内容存盘
			stream = org.apache.commons.io.FileUtils.openOutputStream(file2);
			wb.write(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file2;

	}

	/**
	 * 合并列单元格
	 * 
	 * @param XSSFWorkbook
	 *            wb 要合并单元格的工作簿
	 * 
	 * @param XSSFWorkbook
	 *            wb 要合并单元格的列
	 * @return 合并后的XSSFWorkbook
	 */
	private static XSSFWorkbook rowSpan(int[] l, XSSFWorkbook wb) {
		XSSFSheet sheet0 = wb.getSheet("Sheet0");
		int lastRowNum = sheet0.getLastRowNum();

		// 外层循环遍历所有的列
		for (int i = 0; i < l.length; i++) {
			int column = l[i];
			int start = 2;
			int end = 3;
			while (end < lastRowNum + 1) {
				XSSFRow row2 = sheet0.getRow(start);
				XSSFCell cell = row2.getCell(column);
				String value0 = cell.getStringCellValue();
				XSSFRow row3 = sheet0.getRow(end);
				XSSFCell cell1 = row3.getCell(column);
				String value1 = cell1.getStringCellValue();
				if (value0.equals(value1)) {
					if ((end - 1) == (lastRowNum - 1)) {
						sheet0.addMergedRegion(new CellRangeAddress(start, end,
								column, column));
						end = end + 1;
					} else {
						end = end + 1;
					}
				} else {
					if (!(start == (end - 1))) {
						sheet0.addMergedRegion(new CellRangeAddress(start,
								end - 1, column, column));
					}
					start = end;
					end = end + 1;
				}

			}

		}
		return wb;
	}

	/**
	 * 合并列单元格
	 * 
	 * @param wantSumColumn
	 *            <Integer> wantSumColumn 要求和的列
	 * 
	 * @param wb
	 *            wb 要求和的工作簿
	 * @return 合并后的XSSFWorkbook
	 */
	public static XSSFWorkbook sumOneColumn(List<Integer> wantSumColumn,
			XSSFWorkbook wb) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < wantSumColumn.size(); i++) {
			Integer l = wantSumColumn.get(i);
			if (!list.contains(l)) {
				list.add(l);
			}
		}
		XSSFSheet sheet = wb.getSheet("Sheet0");
		int lastRowNum = sheet.getLastRowNum();
		XSSFRow row = sheet.createRow(lastRowNum + 1);
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("总计：");
		Map<Integer, Character> map = new HashMap<Integer, Character>();
		map.put(1, 'A');
		map.put(2, 'B');
		map.put(3, 'C');
		map.put(4, 'D');
		map.put(5, 'E');
		map.put(6, 'F');
		map.put(7, 'G');
		map.put(8, 'H');
		map.put(9, 'I');
		map.put(10, 'J');
		map.put(11, 'K');
		map.put(12, 'L');
		map.put(13, 'M');
		map.put(14, 'N');
		map.put(15, 'O');
		map.put(16, 'P');
		map.put(17, 'Q');
		map.put(18, 'R');
		map.put(19, 'S');
		map.put(20, 'T');
		map.put(21, 'U');
		map.put(22, 'V');
		map.put(23, 'W');
		map.put(24, 'X');
		map.put(25, 'Y');
		map.put(26, 'Z');
		for (int i = 0; i < list.size(); i++) {
			XSSFCell cell1 = row.createCell(list.get(i));
			char c = map.get(list.get(i) + 1);
			String expression = "SUM(" + c + (2) + ":" + c + (lastRowNum + 1)
					+ ")";
			cell1.setCellFormula(expression);
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
			cell.setCellStyle(cellStyle);
			cell1.setCellStyle(cellStyle);
		}
		return wb;
	}
}
