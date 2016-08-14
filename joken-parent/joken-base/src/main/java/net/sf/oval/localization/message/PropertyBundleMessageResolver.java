/*******************************************************************************
 * Portions created by Sebastian Thomschke are copyright (c) 2005-2015 Sebastian
 * Thomschke.
 *
 * All Rights Reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastian Thomschke - initial implementation.
 *******************************************************************************/
package net.sf.oval.localization.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Default implementation that resolves messages based on the registered
 * resource bundles.
 * 
 * @author Sebastian Thomschke
 */
public class PropertyBundleMessageResolver implements MessageResolver {
	public static final PropertyBundleMessageResolver INSTANCE = new PropertyBundleMessageResolver();

	/**
	 * 验证字段对照
	 */
	// private static final String CONFIG_NAME = "ValidationField.properties";

	// private ResourceBundle bundle;
	/**
	 * 配置对象
	 */
	private Properties CONFIGS_PROPERTIES = null;

	private PropertyBundleMessageResolver() {
		loadProperties();
	}

	@Override
	public String getMessage(String key) {
		if (CONFIGS_PROPERTIES == null) {
			return null;
		}
		return CONFIGS_PROPERTIES.getProperty(key);
	}

	/**
	 * 将指定配置载入到集合
	 * 
	 * @param propertiesPath
	 *            配置文件路径
	 */
	private void loadProperties() {
		InputStream iStream = null;
		try {
			String configPath = "ValidationField.properties";
			ClassLoader loader = PropertyBundleMessageResolver.class
					.getClassLoader();
			// 首先获取根目录上的配置
			iStream = loader.getResourceAsStream(configPath);
			if (iStream == null) {
				iStream = loader.getResourceAsStream("/" + configPath);
				if (iStream == null) {
					String confDir = System.getProperty("conf.dir");
					if (confDir != null && !confDir.isEmpty()) {
						iStream = loader.getResourceAsStream(confDir + "/"
								+ configPath);
					}
				}
			}
			if (iStream == null) {
				iStream = PropertyBundleMessageResolver.class
						.getResourceAsStream("/net/sf/oval/" + configPath);
			}
			if (iStream == null) {
				iStream = ClassLoader.getSystemResourceAsStream(configPath);
			}
			if (iStream != null) {
				CONFIGS_PROPERTIES = new Properties();
				CONFIGS_PROPERTIES.load(iStream);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
