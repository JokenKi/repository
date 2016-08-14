package com.joken.common.logger;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import com.joken.common.properties.SystemGlobal;
import com.joken.common.utils.StringUtils;

/**
 * 
 * 日志业务工厂类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class LoggerFactory {
	private static ConcurrentHashMap<Object, Object> maps = new ConcurrentHashMap<Object, Object>();

	// private static Properties prop = null;

	/**
	 * 私有构造
	 */
	private LoggerFactory() {
	}

	/**
	 * 获取指定名称的日志记录对象
	 * 
	 * @param name
	 *            需要记录日志的名称
	 * @return Logger
	 */
	public static synchronized Logger getLogger(String name) {
		// if (prop == null)
		// try {
		// String confDir = System.getProperty("conf.dir");
		// if (confDir == null || confDir.length() == 0)
		// confDir = System.getenv("conf.dir");
		// if (confDir == null || confDir.length() == 0) {
		// prop = FileUtils.readProperties(FileUtils
		// .getClassPathResourceUri("/log.properties"));
		// } else {
		// prop = new Properties();
		// File file = new File(confDir, "log.properties");
		// // File file = new File(confDir, "log.properties");
		// FileInputStream fis = new FileInputStream(file);
		// prop.load(fis);
		// fis.close();
		// fis = null;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// prop = null;
		// }
		Logger logger = (Logger) maps.get(name);
		if (logger == null) {
			String type = "CONSOLE";
			String level = "INFO";
			String dir = "";
			// type = prop
			// .getProperty((new StringBuilder(String.valueOf(name)))
			// .append(".type").toString());
			type = SystemGlobal.get((new StringBuilder(String.valueOf(name)))
					.append(".type").toString());
			if (StringUtils.isEmpty(type))
				type = SystemGlobal.get("log.root.type");
			level = SystemGlobal.get((new StringBuilder(String.valueOf(name)))
					.append(".level").toString());
			if (StringUtils.isEmpty(level))
				level = SystemGlobal.get("log.root.level");
			dir = SystemGlobal.get((new StringBuilder(String.valueOf(name)))
					.append(".dir").toString());
			if (StringUtils.isEmpty(dir))
				dir = SystemGlobal.get("log.root.dir");
			if (StringUtils.isEmpty(dir))
				dir = (new StringBuilder("..")).append(File.separator)
						.append("logs").toString();
			if (StringUtils.isEmpty(type))
				type = "CONSOLE";
			if (type.equalsIgnoreCase("FILE")) {
				FileLogger flogger = new FileLogger(name);
				flogger.setLogDir(dir);
				flogger.setLogLevel(level);
				logger = flogger;
			} else {
				ConsoleLogger clogger = new ConsoleLogger(name);
				clogger.setLogLevel(level);
				logger = clogger;
			}
			maps.put(name, logger);
		}
		return logger;
	}

	/**
	 * 获取指定类的日志记录实例
	 * 
	 * @param clz
	 *            需要记录日志的类
	 * @return Logger
	 */
	public static Logger getLogger(Class<?> clz) {
		return getLogger(clz.getName());
	}

}
