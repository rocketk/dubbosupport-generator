package py.dubbosupport;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by pengyu on 2015/5/19.
 */
public class ConfigUtil {
    private static ResourceBundle prop; // config.properties

    public ConfigUtil(String absoluteConfig) throws MalformedURLException {
        if (absoluteConfig == null || absoluteConfig.isEmpty()) {
            prop = ResourceBundle.getBundle("config");
            return;
        }
        int lastSlash = absoluteConfig.lastIndexOf(File.separator);
        File file = new File(absoluteConfig.substring(0, lastSlash));
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        prop = ResourceBundle.getBundle(absoluteConfig.substring(lastSlash + 1, absoluteConfig.lastIndexOf(".properties")), Locale.getDefault(), loader);
    }

    /**
     * 从conf.properties中取得配置信息
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        return prop.getString(key);
    }

    public static void main(String[] args) throws MalformedURLException {
        System.out.println(new ConfigUtil("D:\\develop\\github\\dubbosupport-generator\\target\\config.properties").getString("artifactId"));
        System.out.println(System.getProperty("user.dir"));
    }
}
