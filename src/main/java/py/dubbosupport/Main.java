package py.dubbosupport;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class Main {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
    ConfigObject co = new ConfigObject();

    private Main() {
        loadConfig();
        loadFreemarkerConfiguration();
    }

    private void loadConfig() {
        co.setApplicationName(ConfigUtil.getString("applicationName"));
        co.setArtifactId(ConfigUtil.getString("artifactId"));
        co.setGroupId(ConfigUtil.getString("groupId"));
        co.setNexusUrl(ConfigUtil.getString("nexusUrl"));
        co.setOutputDir(ConfigUtil.getString("outputDir"));
        co.setPackageName(ConfigUtil.getString("packageName"));
        co.setProjectName(ConfigUtil.getString("projectName"));
        co.setProviderPort(ConfigUtil.getString("providerPort"));
        co.setVersion(ConfigUtil.getString("version"));
        co.setZookeeper(ConfigUtil.getString("zookeeper"));
    }
    private void loadFreemarkerConfiguration() {
        cfg.setClassForTemplateLoading(Main.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Main main = new Main();
    }

    private void buildPackage() throws IOException, TemplateException {
        Template temp = cfg.getTemplate("test.ftlh");
        String packageDirStr = co.outputDir + File.separator +
                co.projectName + File.separator + "src" + File.separator + "main" + File.separator + "java";
        for (String s : co.packageName.trim().split("\\.")) {
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
