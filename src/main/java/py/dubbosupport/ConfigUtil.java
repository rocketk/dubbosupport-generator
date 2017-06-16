package py.dubbosupport;

import java.util.*;

/**
 * Created by pengyu on 2015/5/19.
 */
public class ConfigUtil {
	private static ResourceBundle prop; // config.properties
	private static Map<String, ResourceBundle> props = new HashMap<String, ResourceBundle>(); // other properties files
	static {
		prop = ResourceBundle.getBundle("config");
	}

	/**
	 * 从conf.properties中取得配置信息
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		if (prop == null) {
			prop = ResourceBundle.getBundle("config", Locale.getDefault());
			props.put("config", prop);
		}
		return prop.getString(key);
	}

//	public static String getString(String key, boolean dynamic) {
//		if (dynamic) {
//			refresh();
//		}
//		String value = prop.getString(key);
//		return value;
//	}

	public static void refresh() {
		ResourceBundle.clearCache();
		prop = ResourceBundle.getBundle("config");
		if (!props.isEmpty()) {
			props.clear();
		}
	}

	/**
	 * 从指定配置文件中取得配置信息
	 * @param key
	 * @param filename
	 * @return
	 */
	public static String getString(String key, String filename) {
		ResourceBundle oneProp = props.get(filename);
		if (oneProp == null) {
			oneProp = ResourceBundle.getBundle(filename);
			if (oneProp == null) {
				throw new IllegalArgumentException("配置文件“" + filename + "”不存在");
			}
			props.put(filename, oneProp);
		}
		return oneProp.getString(key);
	}

	/**
	 * 获取数值，给定key为null或空串时，返回0
	 * @param key
	 * @return
	 */
	public static int getInteger(String key) {
		if (prop == null) {
			prop = ResourceBundle.getBundle("config", Locale.getDefault());
			props.put("config", prop);
		}
		if ((key == null) || (key.equals("")))
			return 0;
		try {
			return Integer.parseInt(prop.getString(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 获取数值，给定key为null或空串时，返回0
	 * @param key
	 * @return
	 */
	public static int getInteger(String key, String filename) {
		ResourceBundle oneProp = props.get(filename);
		if (oneProp == null) {
			oneProp = ResourceBundle.getBundle(filename);
			if (oneProp == null) {
				throw new IllegalArgumentException("配置文件“" + filename + "”不存在");
			}
			props.put(filename, oneProp);
		}
		if ((key == null) || (key.equals("")))
			return 0;
		try {
			return Integer.parseInt(oneProp.getString(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static List<String> getKeys() {
		return getKeys("conf");
	}

	public static List<String> getKeys(String filename) {
		ResourceBundle oneProp = props.get(filename);
		if (oneProp == null) {
			oneProp = ResourceBundle.getBundle(filename);
			if (oneProp == null) {
				throw new IllegalArgumentException("配置文件“" + filename + "”不存在");
			}
			props.put(filename, oneProp);
		}
		Enumeration<String> keys = oneProp.getKeys();
		if (keys == null) {
			return null;
		}
		List<String> keysList = new ArrayList<String>();
		while (keys.hasMoreElements()) {
			keysList.add(keys.nextElement());
		}
		return keysList;
	}
}
