package py.dubbosupport;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class Main {
    static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
    static Map<String, String> root = new HashMap<String, String>();
    static String outputDir = "/Users/pengyu/develop/temp";
    static String projectName = "sample-provider";
    static String packageName = "com.unicompayment.basetools";

    static {
        cfg.setClassForTemplateLoading(Main.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        root.put("groupId", "com.unicompayment.basetools");
        root.put("artifactId", "dubbo-support");
    }

    public static void main(String[] args) throws IOException, TemplateException {
        buildPackage();
    }

    private static void buildPackage() throws IOException, TemplateException {
        Template temp = cfg.getTemplate("test.ftlh");
        String packageDirStr = outputDir + File.separator +
                projectName + File.separator + "src" + File.separator + "main" + File.separator + "java";
        for (String s : packageName.trim().split("\\.")) {
            packageDirStr += File.separator + s;
        }
        File packageDir = new File(packageDirStr);
        if (!packageDir.exists()) {
            packageDir.mkdirs();
        }
//        Writer out = new OutputStreamWriter(System.out);
//        temp.process(root, out);

    }
}
