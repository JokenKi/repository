/**
 * 
 */
package com.joken.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Properties;

import com.joken.common.properties.SystemProperties;

/**
 * 文件通用操作类--<code>FileUtils</code>
 * 
 * @author 欧阳增高
 */
public class FileUtils {

	/**
	 * 构造方法
	 */
	private FileUtils() {

	}

	/**
	 * 检查文件或文件夹是否在存
	 * 
	 * @param path
	 *            文件或文件夹路径
	 * @return boolean
	 */
	public static final boolean exists(String path) {
		if (path == null || "".equals(path)) {
			return false;
		}
		path = decode(path);
		File file = new File(path);
		return file.exists();
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            文件内容
	 * @return 创建成功返回文件大小
	 */
	public static final long create(String filePath, String content) {
		return create(filePath, content, SystemProperties.getEncoding());
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            文件内容
	 * @param encoding
	 *            文件编码
	 * @return 创建成功返回文件大小
	 */
	public static final long create(String filePath, String content,
			String encoding) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				String folderPath = file.getParent();
				if (!createFolders(folderPath)) {
					return -1;
				}
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			}
		}
		PrintWriter myFile;
		try {
			myFile = new PrintWriter(file, encoding);
			myFile.println(content);
			myFile.close();
			return file.length();// file.getTotalSpace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 根据文件数据流创建文件
	 * 
	 * 
	 * @param filePath
	 *            文件保存路径
	 * @param fileName
	 *            文件名
	 * @param stream
	 *            文件数据流
	 * @return 所创建文件大小,返回-1时创建文件失败
	 */
	public static final long create(String filePath, String fileName,
			InputStream stream) {
		if (stream == null || filePath == null || "".equals(filePath)
				|| fileName == null || "".equals(fileName)) {
			return -1;
		}
		String endChar = String.valueOf(filePath.charAt(filePath.length() - 1));
		if (!"/".equals(endChar) && !"\\".equals(endChar)) {
			filePath += "/";
		}
		return create(filePath + fileName, stream);
	}

	/**
	 * 根据文件数据流创建文件
	 * 
	 * @param fileName
	 *            需要创建文件地址
	 * @param stream
	 *            文件数据流
	 * @return 所创建文件大小,返回-1时创建文件失败
	 */
	public static final long create(String fileName, byte[] buf) {
		InputStream stream = new ByteArrayInputStream(buf);
		return create(fileName, stream);
	}

	/**
	 * 根据文件数据流创建文件
	 * 
	 * @param fileName
	 *            需要创建文件地址
	 * @param stream
	 *            文件数据流
	 * @return 所创建文件大小,返回-1时创建文件失败
	 */
	public static final long create(String fileName, InputStream stream) {
		long result = -1;
		if (stream == null || fileName == null || "".equals(fileName)) {
			return result;
		}

		File saveFile = new File(fileName);
		if (saveFile.exists()) {
			saveFile.delete();
		} else {
			String folderPath = saveFile.getParent();
			if (!createFolders(folderPath)) {
				return -1;
			}
		}
		OutputStream out;
		try {
			out = new FileOutputStream(saveFile);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			stream.close();
			result = saveFile.length();// saveFile.getTotalSpace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 以数据流方式获取文件内容
	 * 
	 * @param file
	 *            文件地址
	 * @return 文件数据流
	 */
	public static final InputStream readStream(String file) {
		file = decode(file);
		InputStream fs = null;
		try {
			fs = new FileInputStream(file);
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				if (fs == null) {
					fs = FileUtils.class.getResourceAsStream(file);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return fs;
	}

	/**
	 * 以Properties方式获取文件内容
	 * 
	 * @param file
	 *            文件地址
	 * @return 文件属性对象
	 */
	public static final Properties readProperties(String file) {
		file = decode(file);
		Properties properties = null;
		try {
			InputStream stream = readStream(file);
			if (stream == null) {
				return null;
			}
			properties = new Properties();
			properties.load(stream);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 以byte[]方式获取文件数据
	 * 
	 * @param file
	 *            文件地址
	 * @return 文件byte[]或null
	 */
	public static final byte[] readByte(String file) {
		file = decode(file);
		if (!exists(file)) {
			return null;
		}
		try {
			FileInputStream fs = new FileInputStream(file);
			byte[] buffer = new byte[fs.available()];
			fs.read(buffer);
			fs.close();
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 以字符串方式获取文件内容
	 * 
	 * @param file
	 *            文件地址
	 * @return 文件字符串形式内容
	 */
	public static final String readText(String file) {
		return readInputStream(file);
	}

	/**
	 * 从输入流中获取文件内容
	 * 
	 * @param path
	 *            输入流
	 * @return String
	 */
	public static final String readInputStream(String path) {
		try {
			InputStream input = readStream(path);
			if (input == null) {
				return null;
			}
			InputStreamReader isr = new InputStreamReader(input,
					SystemProperties.getEncoding());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder str = new StringBuilder("");
			try {
				String data = "";
				int index = 0;
				while ((data = br.readLine()) != null) {
					if (index == 0) {
						data = data.substring(1);
					}
					str.append(data).append("\r\n");
					index++;
				}
			} catch (Exception e) {
				str.append(e.toString());
			} finally {
				br.close();
				isr.close();
			}
			return str.toString();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            文本文件完整绝对路径及文件名
	 * @return Boolean 成功删除返回true遭遇异常返回false
	 */
	public static final boolean delFile(String filePath) {
		boolean bea = false;
		try {
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				bea = myDelFile.delete();
			}
		} catch (Exception e) {
		}
		return bea;
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return boolean
	 */
	public static final boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (String element : tempList) {
			String tmpPath = path.endsWith(File.separator) ? path + element
					: path + File.separator + element;
			temp = new File(tmpPath);
			if (temp.isDirectory()) {
				delFolder(tmpPath);
			}
			temp.delete();
		}
		return true;
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param filepath
	 *            文件路径
	 * @return 文件大小,文件不存在时返回-1
	 */
	public static final long getFileSize(String filepath) {
		if (filepath == null || "".equals(filepath)) {
			return -1;
		}
		File f = new File(filepath);
		if (f.exists()) {
			return f.length();// f.getTotalSpace();
		}
		return -1;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 *            文件名称
	 * @return String
	 */
	public static String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return "";
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return boolean
	 */
	public static final boolean delFolder(String folderPath) {
		try {
			File folder = new File(folderPath);
			if (!folder.exists()) {
				return false;
			}
			if (folder.isDirectory()) {
				delAllFile(folderPath); // 删除完里面所有内容
			}
			folder.delete(); // 删除空文件夹
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 创建多级目录
	 * 
	 * @param folderPath
	 *            目录路径
	 * @return 目录存在或创建目录成功返回true,反之false
	 */
	public static final boolean createFolders(String folderPath) {
		if (folderPath == null || "".equals(folderPath)) {
			return false;
		}
		String newFolderPath = folderPath.replaceAll("\\\\", "/").replaceAll(
				"//", "/");
		String[] arrPath = newFolderPath.split("/");
		StringBuffer sbPath = new StringBuffer("");
		for (String path : arrPath) {
			sbPath.append("/");
			if (path == null || "".equals(path) || path.indexOf(".") != -1) {
				continue;
			}
			sbPath.append(path.trim());
			boolean su = createFolder(sbPath.toString());
			if (!su) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 创建新目录
	 * 
	 * @param folderPath
	 *            目录路径
	 * @return 目录存在或创建目录成功返回true,反之false
	 */
	public static final boolean createFolder(String folderPath) {
		if (folderPath == null || "".equals(folderPath)
				|| "/".equals(folderPath) || ":".equals(folderPath)) {
			return false;
		}
		try {
			File folder = new File(folderPath);
			folder.setWritable(true, false);
			if (!folder.exists()) {
				folder.mkdir();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 文件路径URL转码格式解码
	 * 
	 * @param file
	 *            文件路径
	 * @return String
	 * 
	 */
	public static final String decode(String file) {
		try {
			return URLDecoder.decode(file, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取随机保存目录
	 * 
	 * @param isRand
	 *            是否需要创建a-z随机字母目录结构,true为创建
	 * @return String
	 */
	public static String getRandsFolder(boolean isRand) {
		// 获取上传目录根路径
		StringBuffer savePath = new StringBuffer("/");

		// 获取yyyyMMddHHmmss格式日期时间值
		String dateTime = DateUtils.getORADateTime();
		// 拼接随机路径
		savePath.append(dateTime.substring(0, 6)).append("/");
		if (isRand) {
			savePath.append(RandomUtils.getRands(1, "l")).append("/");
		}
		return savePath.toString();
	}

	/**
	 * 获取上传附件文件名
	 * 
	 * @param extendsName
	 *            上传文件扩展名
	 * @return String
	 */
	public static String getRandsFileName(String extendsName) {
		return DateUtils.getORADateTime() + RandomUtils.getRands(3, "u") + "."
				+ extendsName;
	}

	/**
	 * 
	 * @param path
	 *            原文件夹路径
	 * @param des
	 *            目标文件夹路劲
	 */
	public static void copyFolder(String path, String des) {
		try {
			// 如果文件夹不存在 则建立新文件夹
			if (!(new File(des)).exists()) {
				(new File(des)).mkdirs();
			}
			File a = new File(path);
			if (a.exists()) {
				String[] file = a.list();
				File temp = null;
				for (int i = 0; file != null && i < file.length; i++) {
					if (path.endsWith(File.separator)) {
						temp = new File(path + file[i]);
					} else {
						temp = new File(path + File.separator + file[i]);
					}
					if (temp.isFile()) {
						FileInputStream input = new FileInputStream(temp);
						create(des + "/" + (temp.getName()).toString(), input);
					}
					if (temp.isDirectory()) {// 如果是子文件夹
						copyFolder(path + "/" + file[i], des + "/" + file[i]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到应用class path下指定资源的文件全路径
	 * 
	 * @param file
	 *            文件路径
	 * @return String
	 */
	public static String getClassPathResourceUri(String file) {
		return FileUtils.class.getResource(file).toString()
				.replaceFirst("file:/", "");
	}

	/**
	 * 得到文件的最后修改时间
	 * 
	 * @param file
	 *            文件对象
	 * @return String
	 */
	public static String getModifiedTime(File file) {
		Calendar cal = Calendar.getInstance();
		long time = file.lastModified();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cal.setTimeInMillis(time);
		return formatter.format(cal.getTime());
	}

	/**
	 * 格式化文件大小（KB）
	 * 
	 * @param size
	 *            需要格式化的数字
	 * @return String
	 */
	public static String formatFileSize(long size) {
		String fileSize = " KB";
		NumberFormat nf = NumberFormat.getInstance();
		fileSize = nf.format(size / 1024 == 0 ? 1 : size / 1024) + fileSize;
		return fileSize;
	}

	/**
	 * 将文件列表按时间正序排列
	 * 
	 * @param fileArrays
	 *            文件列表
	 * @return File[]
	 */
	public static File[] sortByLastModified(File[] fileArrays) {
		Arrays.sort(fileArrays, new CompratorByLastModified());
		return fileArrays;
	}

	/**
	 * 文件按时间正序排列比较器
	 * 
	 * @author Administrator
	 * 
	 */
	private static class CompratorByLastModified implements Comparator<File> {
		public int compare(File f1, File f2) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0) {
				return 1;
			} else if (diff == 0) {
				return 0;
			} else {
				return -1;
			}
		}

		@Override
		public boolean equals(Object obj) {
			return true;
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				System.out.println("复制文件完成！");
				inStream.close();
				if (fs != null) {
					fs.close();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建文件目录
	 * 
	 * @param f_path
	 *            需要创建的全路径
	 * @return int
	 */
	public static int createFileDir(String f_path) {
		File oa_file = null;
		int nResult = 0;
		try {
			oa_file = new File(f_path);
			if (!oa_file.isDirectory()) { // 判断文件路径是否存在
				oa_file.mkdirs(); // 创建文件夹
				nResult = 1;
			} else { // 文件路径存在
				nResult = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nResult;
	}

	/**
	 * 得到指定目录符合正则表达式的文件列表
	 * 
	 * @param dirPath
	 *            指定目录
	 * @param regex
	 *            正则表达式
	 * @return File[]
	 * @throws Exception
	 */
	public static File[] listFileByRegex(String dirPath, String regex)
			throws Exception {
		if (!exists(dirPath)) {
			throw new Exception("指定的目录【" + dirPath + "】不存在。");
		}

		File dir = new File(dirPath);
		if (!dir.isDirectory()) {
			throw new Exception("指定的目录【" + dirPath + "】不是目录。");
		}

		return dir.listFiles(getFileRegexFilter(regex));
	}

	/**
	 * 文件名过滤器（正则表达式）
	 * 
	 * @param regex
	 *            正则表达式
	 * @return FilenameFilter
	 */
	private static FilenameFilter getFileRegexFilter(String regex) {
		final String regex_ = regex;
		return new FilenameFilter() {
			public boolean accept(File file, String name) {
				boolean ret = file.isDirectory() && name.matches(regex_);
				return ret;
			}
		};
	}
}
