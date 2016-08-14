package com.joken.common.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 
 * 文件输出日志实现类
 * 
 * @version V1.0.0, 2016-3-15
 * @author 欧阳增高
 * @since V1.0.0
 */
public class FileLogger extends LoggerImpl {

	private static Logger logger = Logger.getLogger("LocalLogger");
	/**
	 * 输出日志文件目录
	 */
	private String logDir;
	/**
	 * 最后输出的日期
	 */
	private String lastDate;
	/**
	 * 日期格式
	 */
	private SimpleDateFormat formater;
	/**
	 * 输出对象
	 */
	private PrintWriter out;

	/**
	 * 获取输出目录路径
	 * 
	 * @return 输出目录路径
	 */
	public String getLogDir() {
		return logDir;
	}

	/**
	 * 设置输出目录
	 * 
	 * @param logDir
	 *            输出目录
	 */
	public void setLogDir(String logDir) {
		this.logDir = logDir;
		StringBuilder sb = new StringBuilder(logDir);
		sb.append(File.separator).append(getName());
		File file = new File(sb.toString());
		// file.setWritable(true, false);
		if (!file.exists()){
			boolean restul = file.mkdirs();
			if(!restul){
				logger.info("目录创建失败：" + sb);
			}
		}
	}

	/**
	 * 构造
	 * 
	 * @param name
	 *            日志名称
	 */
	public FileLogger(String name) {
		super(name);
		formater = new SimpleDateFormat("yyyyMMdd");
		lastDate = "";
	}

	protected void process(String o) {
		try {
			if (o != null) {
				String ss = formater.format(new Date());
				if (ss.equalsIgnoreCase(lastDate) && out != null) {
					out.println(o.toString());
					out.flush();
				} else {
					if (out != null)
						out.close();
					StringBuilder sb = new StringBuilder(logDir);
					sb.append(File.separator).append(getName());
					sb.append(File.separator).append(ss).append(".txt");
					logger.info("目录创建失败：" + sb);
					File f = new File(sb.toString());
					f.createNewFile();
					FileOutputStream fout = new FileOutputStream(f, true);
					out = new PrintWriter(new PrintStream(fout));
					lastDate = ss;
					out.println(o.toString());
					out.flush();
				}
				o = null;
			}
		} catch (Exception e) {
			logger.info("目录创建失败：" + logDir);
			e.printStackTrace();
		}
	}
}
