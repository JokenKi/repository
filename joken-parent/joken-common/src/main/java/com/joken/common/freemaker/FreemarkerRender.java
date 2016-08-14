package com.joken.common.freemaker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joken.common.properties.SystemProperties;
import com.joken.common.utils.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * freeMarker模板渲染工具类
 * 
 * @version V1.0.0
 * 
 * @author 欧阳增高
 */
public final class FreemarkerRender {
	/**
	 * 模板配置
	 */
	private static Configuration TEMPLATE_CONFIG = null;

	/**
	 * 延迟更新时间
	 */
	private static final int TEMPLATE_UPDATE_DELAY = 1200;

	/**
	 * 禁止实例化
	 */
	private FreemarkerRender() {
	}

	/**
	 * 初始化模板配置
	 */
	static {
		initialize();
	}

	/**
	 * 初始化freemarker配置
	 * 
	 */
	private static void initialize() {
		try {
			if (TEMPLATE_CONFIG == null) {
				TEMPLATE_CONFIG = new Configuration();
				TEMPLATE_CONFIG
						.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
				TEMPLATE_CONFIG.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
				TEMPLATE_CONFIG.setTemplateUpdateDelay(TEMPLATE_UPDATE_DELAY);
				StringTemplateLoader stl = new StringTemplateLoader();
				TEMPLATE_CONFIG.setTemplateLoader(stl);
				TEMPLATE_CONFIG.setDefaultEncoding(SystemProperties
						.getEncoding());
				TEMPLATE_CONFIG.setLocale(new java.util.Locale("zh_CN"));
				TEMPLATE_CONFIG.setNumberFormat("0.##########");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重载模板配置
	 * 
	 * @param path
	 *            配置路径
	 */
	public static void resetClassTemplateLoader(String path) {
		TEMPLATE_CONFIG.setTemplateLoader(new ClassTemplateLoader(
				FreemarkerRender.class, path));
	}

	/**
	 * 重载模板配置
	 * 
	 * @param templateLoader
	 *            模板载入实例
	 */
	public static void resetTemplateLoader(TemplateLoader templateLoader) {
		TEMPLATE_CONFIG.setTemplateLoader(templateLoader);
	}

	/**
	 * 渲染后得到字符串
	 * 
	 * @param data
	 *            数据
	 * @param templateName
	 *            模板名称
	 * @return 渲染的数据
	 */
	public static String renderToName(Object data, String templateName) {
		return renderToNameWriter(data, templateName).toString();
	}

	/**
	 * 渲染后得到字符串
	 * 
	 * @param data
	 *            数据
	 * @param template
	 *            模板内容
	 * @return 渲染的数据
	 */
	public static String renderToString(Object data, String template) {
		return renderToStringWriter(data, template).toString();
	}

	/**
	 * 将数据写入指定名称的模板中
	 * 
	 * @param target
	 *            需要填充的数据
	 * @param templateName
	 *            模板名称
	 * @return StringWriter
	 * @author 欧阳增高
	 * @date 2016-3-15 上午11:31:06
	 */
	private static StringWriter renderToNameWriter(Object target,
			String templateName) {
		StringWriter writer = new StringWriter();
		try {
			Template tl = getTemplate(templateName);
			tl.process(target, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException ex) {
			ex.printStackTrace();
		}
		return writer;
	}

	/**
	 * 渲染后得到StringWrite对象
	 * 
	 * @param target
	 *            数据
	 * @param template
	 *            模板内容
	 * @return StringWriter
	 */
	private static StringWriter renderToStringWriter(Object target,
			String template) {
		StringWriter writer = new StringWriter();
		String key = Integer.toString(template.hashCode());
		try {
			if (TEMPLATE_CONFIG.getTemplateLoader().findTemplateSource(key) == null) {
				setTemplate(key, template);
			}
			Template tl = getTemplate(key);
			tl.process(target, writer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException ex) {
			ex.printStackTrace();
		}

		return writer;
	}

	/**
	 * 获取指定模板名称的模板
	 * 
	 * @param templateName
	 *            模板名称或内容，当为内容时以内容的hashCode为名称
	 * @return Template
	 * @throws IOException
	 */
	private static Template getTemplate(String templateName) throws IOException {
		Template tl = TEMPLATE_CONFIG.getTemplate(templateName,
				TEMPLATE_CONFIG.getLocale());
		return tl;
	}

	/**
	 * 设置模板
	 * 
	 * @param key
	 *            模板名称
	 * @param context
	 *            模板内容
	 */
	public static void setTemplate(String key, String context)
			throws IOException {
		StringTemplateLoader loader = (StringTemplateLoader) TEMPLATE_CONFIG
				.getTemplateLoader();
		loader.putTemplate(key, renderTemplate(context));
		TEMPLATE_CONFIG.setTemplateLoader(loader);
	}

	/**
	 * 模板参数前缀
	 */
	private static final String placeholderPrefix = "\\${";
	/**
	 * 模板参数后缀
	 */
	private static final String placeholderSuffix = "}";
	/**
	 * 默认占位符表达式
	 */
	private static final String DEFAULT_PARAMS_REGX = "\\<\\!\\[FTL\\[([a-zA-Z\\.]+)\\]\\]\\>";

	/**
	 * 规则正则解析表达式 -- 模板输入参数值
	 */
	private static final Pattern FREEMARKER_PARAMS_REGX = Pattern.compile(
			StringUtils.getValue(SystemProperties.getFreemarkerRegx(),
					DEFAULT_PARAMS_REGX), Pattern.CASE_INSENSITIVE);

	/**
	 * 渲染模板中的占位符参数
	 * 
	 * @param context
	 *            模板内容
	 * @return String
	 */
	private static String renderTemplate(String context) {
		StringBuffer sb = new StringBuffer("");

		Matcher m = FREEMARKER_PARAMS_REGX.matcher(context);
		while (m.find()) {
			m.appendReplacement(sb, placeholderPrefix + m.group(1)
					+ placeholderSuffix);
		}
		m.appendTail(sb);
		return sb.toString();
	}

	// public static void main(String[] args) {
	// String str =
	// "{<#if modelId?exists>\"modelId\":\"${modelId}\",</#if><#if cabinetId?exists>\"cabinetId\":\"7\",</#if><#if messageId?exists>\"messageId\":1,</#if>\"command\":${command}}";
	// Map<String, Object> map = MapUtils.initMap("command", "aaa");
	// System.out.println(FreemarkerRender.renderToString(map, str));
	// }
}
