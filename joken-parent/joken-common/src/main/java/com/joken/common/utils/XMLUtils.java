package com.joken.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;
import com.joken.common.properties.SystemProperties;

/**
 * 操作xml的类
 * 
 * @author Administrator
 * 
 */
public final class XMLUtils {
	/**
	 * 日志类初始化
	 */
	private static Logger logger = LoggerFactory.getLogger(XMLUtils.class);

	/**
	 * 私有化构造
	 */
	private XMLUtils() {
	}

	/**
	 * 加载xml文件
	 * 
	 * @param file
	 *            需要加载XML路径
	 * @return Document
	 * @throws DocumentException
	 */
	public static Document loadDocument(String file) throws DocumentException {
		SAXReader reader = new SAXReader();
		return reader.read(file);
	}

	/**
	 * 创建document
	 * 
	 * @return Document
	 */
	public static Document createDocument() {
		return DocumentHelper.createDocument();
	}

	/**
	 * 根据根节点创建xml Document
	 * 
	 * @param root
	 *            根节点
	 * @return Document
	 */
	public static Document createDocument(Element root) {
		return DocumentHelper.createDocument(root);
	}

	/**
	 * 保存xml文件
	 * 
	 * @param dom
	 *            xml Document对象
	 * @param file
	 *            需要保存的文件路径
	 * @throws TransformerException
	 * @throws IOException
	 */
	public static void saveDocument(Document dom, String file)
			throws TransformerException, IOException {
		XMLWriter xmlWriter = null;
		try {
			OutputFormat fmt = OutputFormat.createPrettyPrint();
			fmt.setEncoding(SystemProperties.getEncoding());
			xmlWriter = new XMLWriter(
					new OutputStreamWriter(new FileOutputStream(file),
							SystemProperties.getEncoding()), fmt);
			xmlWriter.write(dom);
			xmlWriter.close();
		} catch (Exception e) {
			logger.error("XML解析错误", e.getStackTrace());
		} finally {
			if (xmlWriter != null) {
				xmlWriter.close();
			}
		}
	}

	/**
	 * 获取Document的转换对象
	 * 
	 * @param dom
	 *            Document对象
	 * @return Transformer
	 * @throws TransformerConfigurationException
	 */
	public static Transformer getTransformer(Document dom)
			throws TransformerConfigurationException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();

		return transformer;
	}

	/**
	 * 将Document对象转为字符串
	 * 
	 * @param doc
	 *            需要转换的Document对象
	 * @return String
	 */
	public static String xmlToStr(Document doc) {
		return doc.asXML();
	}

	/**
	 * 将XML格式字符串转为Document对象
	 * 
	 * @param text
	 *            需要转换的XML字符串
	 * @return Document
	 */
	public static Document strToXml(String text) {
		try {
			Document doc = DocumentHelper.parseText(text);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查找节点，并返回第一个符合条件节点
	 * 
	 * @param strXML
	 *            需要查找的字符串XML内容对象
	 * @param express
	 *            用于xpath查找的节点及属性
	 * @return 符合条件节点的第一个节点
	 */
	public static Node selectSingleNode(String strXML, String express) {
		Document dom = strToXml(strXML);
		if (dom == null) {
			return null;
		}
		return dom.selectSingleNode(express);
	}

	/**
	 * 查找节点，并返回第一个符合条件节点
	 * 
	 * @param dom
	 *            需要查找的XML内容对象
	 * @param express
	 *            用于xpath查找的节点及属性
	 * @return 符合条件节点的第一个节点
	 */
	public static Node selectSingleNode(Document dom, String express) {
		return dom.selectSingleNode(express);
	}

	/**
	 * 从指定XML字符中，获取指定条件节点的text内容
	 * 
	 * @param dom
	 *            需要查找的字符串XML Dom对象
	 * @param express
	 *            用于xpath查找的节点及属性
	 * @return 符合条件节点的第一个节点
	 */
	public static String selectSingleNodeText(Document dom, String express) {
		Node node = selectSingleNode(dom, express);
		if (node != null) {
			return node.getText();
		}
		return null;
	}

	/**
	 * 从指定XML字符中，获取指定条件节点的text内容
	 * 
	 * @param strXML
	 *            需要查找的字符串XML内容对象
	 * @param express
	 *            用于xpath查找的节点及属性
	 * @return 符合条件节点的第一个节点
	 */
	public static String selectSingleNodeText(String strXML, String express) {
		Node node = selectSingleNode(strXML, express);
		if (node != null) {
			return node.getText();
		}
		return null;
	}

	/**
	 * 获取Dom对象指定路径所有节点
	 * 
	 * @param dom
	 *            需要操作的Dom对象
	 * @param express
	 *            用于xpath查找的节点及属性
	 * @return List
	 */
	public static List<?> selectNode(Document dom, String express) {
		return dom.selectNodes(express);
	}

	/**
	 * 格式化xml文件
	 * 
	 * @param filepath
	 *            文件路径
	 */
	public static void format(String filepath) {
		File file = new File(filepath);
		SAXReader reader = new SAXReader();
		try {
			org.dom4j.Document news = reader.read(file);
			OutputFormat format = OutputFormat.createPrettyPrint();
			FileOutputStream out = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
			XMLWriter writer = new XMLWriter(osw, format);
			writer.write(news);
			writer.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
