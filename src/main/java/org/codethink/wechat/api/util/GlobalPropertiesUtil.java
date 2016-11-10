package org.codethink.wechat.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * 
 * @author CaiXiangNing
 * @date 上午11:39:35
 */
public class GlobalPropertiesUtil {

	static Logger logger = LoggerFactory.getLogger(GlobalPropertiesUtil.class);

	static GlobalPropertiesUtil instance;
	static String GLOBAL_PROPERTIES_FILE = "/global.properties";
	static Properties props;
	static File file;
	static long lastModifiedTime;

	private GlobalPropertiesUtil() {
		// String tmpFileName = IOUtils.getWebPath() + "WEB-INF" +
		// GLOBAL_PROPERTIES_FILE;
		String tmpFileName = GLOBAL_PROPERTIES_FILE;
		file = new File(tmpFileName);
		if (file.exists() == false)
			file = new File(GlobalPropertiesUtil.class.getResource(GLOBAL_PROPERTIES_FILE).getFile());
		props = new Properties();
		lastModifiedTime = file.lastModified();
		try {
			if (file != null && file.exists()) {
				props.load(new FileInputStream(file));
				logger.info("Global has been init use the properties file '" + GLOBAL_PROPERTIES_FILE + "'");
				logger.debug("the global props is " + props.toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.err);
			logger.error("error when init Global use the properties file '" + GLOBAL_PROPERTIES_FILE + "<" + file.getAbsolutePath() + ">'", e);
		} catch (NullPointerException e) {
			e.printStackTrace(System.err);
			logger.error("error when init Global use the properties file '" + GLOBAL_PROPERTIES_FILE + "<" + file.getAbsolutePath() + ">'", e);
		} catch (IOException e) {
			e.printStackTrace(System.err);
			logger.error("error when init Global use the properties file '" + GLOBAL_PROPERTIES_FILE + "<" + file.getAbsolutePath() + ">'", e);
		}
	}

	private static GlobalPropertiesUtil getInstance() {
		return instance == null ? instance = new GlobalPropertiesUtil() : instance;
	}

	public static void getInstance(String file) {
		GLOBAL_PROPERTIES_FILE = file;
		getInstance();
	}

	public static String getProperty(String key) {
		if (key == null)
			return null;

		getInstance();
		if (lastModifiedTime != file.lastModified()) {
			lastModifiedTime = file.lastModified();
			try {
				props.load(new FileInputStream(file));
				logger.info("Global has been reload use the properties file '" + GLOBAL_PROPERTIES_FILE + "'");
			} catch (IOException e) {
				e.printStackTrace(System.err);
				logger.error("error when reload Global use the properties file '" + GLOBAL_PROPERTIES_FILE + "'", e);
			}
		}

		String property = props.getProperty(key);
		if (property == null)
			return null;

		String value = null;
		try {
			value = new String(property.getBytes("ISO-8859-1"), "gb2312");
		} catch (Exception e) {
			e.printStackTrace(System.err);
			logger.error("error when get property's value from Global(encodeing)", e);
		}
		return value;
	}

}
