package py.dubbosupport;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;

/**
 * @author pengyu
 */
public class Generator {
    final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
    final ConfigObject co = new ConfigObject();
    final String DEFAULT_OUTPUT_DIR = System.getProperty("user.dir") + File.separator + "output" + File.separator;


    private Generator() throws MalformedURLException {
        loadConfig();
        loadFreemarkerConfiguration();
    }

    private void loadConfig() throws MalformedURLException {
        String customConfig = System.getProperty("user.dir") + File.separator + "customConfig.properties";
        ConfigUtil configUtil = null;
        File customConfigFile = new File(customConfig);
        if (customConfigFile.exists() && customConfigFile.isFile()) {
            configUtil = new ConfigUtil(customConfig);
            System.out.println("use custom config: " + customConfig);
        } else {
            configUtil = new ConfigUtil(null);
            System.out.println("use default config " );
        }
        co.setApplicationName(configUtil.getString("applicationName"));
        co.setArtifactId(configUtil.getString("artifactId"));
        co.setGroupId(configUtil.getString("groupId"));
        String outputDir = configUtil.getString("outputDir");
        co.setOutputDir((outputDir == null || outputDir.isEmpty()) ? DEFAULT_OUTPUT_DIR : outputDir);
        if (!co.getOutputDir().endsWith(File.separator)) {
            co.setOutputDir(co.getOutputDir() + File.separator);
        }
        co.setBasePackageName(configUtil.getString("basePackageName"));
        co.setBasePackageNamePath(packageToPath(configUtil.getString("basePackageName")));
        co.setProjectName(configUtil.getString("projectName"));
        co.setProviderPort(configUtil.getString("providerPort"));
        co.setVersion(configUtil.getString("version"));
        co.setZookeeper(configUtil.getString("zookeeper"));
        co.setSpringVersion(configUtil.getString("springVersion"));
        co.setDubboVersion(configUtil.getString("dubboVersion"));
        co.setJavaVersion(configUtil.getString("javaVersion"));
        co.setZookeeperVersion(configUtil.getString("zookeeperVersion"));
        co.setNexusRepoId(configUtil.getString("nexusRepoId"));
        co.setNexusReleaseUrl(configUtil.getString("nexusReleaseUrl"));
        co.setNexusSnapshotUrl(configUtil.getString("nexusSnapshotUrl"));
    }

    private void loadFreemarkerConfiguration() {
        cfg.setClassForTemplateLoading(Generator.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Generator main = new Generator();
        System.out.println("output directory: " + main.co.getOutputDir());
        main.buildJavaFiles();
        main.buildResourceFiles();
        main.copyFiles();
    }

    /**
     * 删除输出目录, 为避免误删, 此方法弃用
     */
    @Deprecated
    private void removeOutputProjectDir() {
        File file = new File(co.getOutputDir() + co.getProjectName() + File.separator);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 将包名转换为路径, 如 py.dubbosupport.container --> py/dubbosupport/container/
     *
     * @param packageName
     * @return
     */
    private String packageToPath(String packageName) {
        if (packageName == null) {
            return null;
        }
        String packageDirStr = packageName.replace(".", File.separator);
        if (!packageDirStr.endsWith(File.separator)) {
            packageDirStr += File.separator;
        }
        return packageDirStr;
    }

    /**
     * 将路径名转换为包名, 如 py/dubbosupport/container/ --> py.dubbosupport.container
     *
     * @param path
     * @return
     */
    private String pathToPackage(String path) {
        if (path == null) {
            return null;
        }
        String packageName = path.replace("/", ".");
        if (packageName.endsWith("\\.")) {
            packageName = packageName.substring(0, packageName.length() - 1);
        }
        if (packageName.startsWith("\\.")) {
            packageName = packageName.substring(1, packageName.length());
        }
        return packageName;
    }


    private void buildJavaFiles() throws IOException, TemplateException {
//        buildJavaFile("src/main/java/container/Container.java", "src/main/java/", "py.dubbosupport.container", "Container");
        for (String file : MetaConfig.JAVA_TEMPLATE_FILES) {
            if (file.startsWith(MetaConfig.JAVA_MAIN_TEMPLATES_DIR)) {
                buildJavaFile(file);
            } else if (file.startsWith(MetaConfig.JAVA_TEST_TEMPLATES_DIR)) {
                buildJavaFile(file);
            } else {
                throw new IllegalArgumentException("templateStr must starts with MetaConfig.JAVA_MAIN_TEMPLATES_DIR or MetaConfig.JAVA_TEST_TEMPLATES_DIR");
            }
        }
    }

    /**
     * 生成单个java文件
     *
     * @param templateStr   Freemarker模板, 相对路径 如 src/main/java/container/Container.java
     * @param outputJavaDir Java类输出路径, 相对路径, 如 src/main/java/
     * @param packageName   要输出的类的包名, 如 py.dubbosupport.container
     * @param javaName      类名, 如 Container
     * @throws IOException
     */
    private void buildJavaFile(String templateStr, String outputJavaDir, String packageName, String javaName) throws IOException, TemplateException {
        Template template = cfg.getTemplate(templateStr);
        // 创建package包对应的文件夹
        File targetPackageDir = new File(co.getOutputDir() + co.getProjectName() + File.separator + outputJavaDir + packageToPath(packageName));
        if (!targetPackageDir.exists()) {
            targetPackageDir.mkdirs();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(new File(targetPackageDir, javaName.endsWith(".java") ? javaName : (javaName + ".java"))));
        template.process(co, out);
    }


    /**
     * 生成单个java文件, 包名, 类名等信息会动态计算出来
     *
     * @param templateStr Freemarker模板, 相对路径 如 src/main/java/container/Container.java
     */
    private void buildJavaFile(String templateStr) throws IOException, TemplateException {
        boolean isTest = false;
        if (templateStr.startsWith(MetaConfig.JAVA_MAIN_TEMPLATES_DIR)) {
            isTest = false;
        } else if (templateStr.startsWith((MetaConfig.JAVA_TEST_TEMPLATES_DIR))) {
            isTest = true;
        } else {
            throw new IllegalArgumentException("templateStr must starts with MetaConfig.JAVA_MAIN_TEMPLATES_DIR or MetaConfig.JAVA_TEST_TEMPLATES_DIR");
        }
        // 去掉java目录的路径名, 如container/Container.java
        String templateStr1 = isTest ? templateStr.replace(MetaConfig.JAVA_TEST_TEMPLATES_DIR, "") : templateStr.replace(MetaConfig.JAVA_MAIN_TEMPLATES_DIR, "");
//        if (templateStr.startsWith(MetaConfig.JAVA_MAIN_TEMPLATES_DIR)) {
//            templateStr1 = templateStr.replace(MetaConfig.JAVA_MAIN_TEMPLATES_DIR, "");
//        } else if (templateStr.startsWith((MetaConfig.JAVA_TEST_TEMPLATES_DIR))) {
//            templateStr1 = templateStr.replace(MetaConfig.JAVA_TEST_TEMPLATES_DIR, "");
//        } else {
//            throw new IllegalArgumentException("templateStr must starts with MetaConfig.JAVA_MAIN_TEMPLATES_DIR or MetaConfig.JAVA_TEST_TEMPLATES_DIR");
//        }
        int lastSlash = templateStr1.lastIndexOf("/");
        String packageName = co.getBasePackageName() + "." + (lastSlash == -1 ? "" : pathToPackage(templateStr1.substring(0, lastSlash)));
        String javaName = lastSlash == -1 ? templateStr1 : templateStr1.substring(lastSlash + 1, templateStr1.length());
        System.out.println(String.format("build java file: %s)", templateStr));
        buildJavaFile(templateStr, isTest ? MetaConfig.OUTPUT_JAVA_TEST_DIR : MetaConfig.OUTPUT_JAVA_MAIN_DIR, packageName, javaName);
    }

    /**
     * @param templateStr       Freemarker模板, 相对路径 如 src/main/resources/provider.xml or src/main/assembly/full.xml
     * @param outputResourceDir 资源文件输出路径, 相对路径, 如 src/main/resources/ or src/main/assembly/
     * @param resourceName      资源文件名, 如 provider.xml or full.xml
     * @throws IOException
     * @throws TemplateException
     */
    private void buildResourceFile(String templateStr, String outputResourceDir, String resourceName) throws IOException, TemplateException {
        Template template = cfg.getTemplate(templateStr);
        // 创建资源文件所在的文件夹
        File targetResourceDir = new File(co.getOutputDir() + co.getProjectName() + File.separator + outputResourceDir);
        if (!targetResourceDir.exists()) {
            targetResourceDir.mkdirs();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(new File(targetResourceDir, resourceName)));
        template.process(co, out);
    }

    private void buildResourceFile(String templateStr) throws IOException, TemplateException {
        int lastSlash = templateStr.lastIndexOf("/");
        String outputResourceDir = lastSlash == -1 ? "" : templateStr.substring(0, lastSlash);
        String resourceFileName = lastSlash == -1 ? templateStr : templateStr.substring(lastSlash + 1, templateStr.length());
        System.out.println(String.format("build resource file: %s", templateStr));
        buildResourceFile(templateStr, outputResourceDir, resourceFileName);
    }

    private void buildResourceFiles() throws IOException, TemplateException {
        for (String resourceTemplateFile : MetaConfig.RESOURCE_TEMPLATE_FILES) {
            buildResourceFile(resourceTemplateFile);
        }
    }

    private void copyFiles() throws IOException {
        for (String file : MetaConfig.COPY_ONLY_FILES) {
            File target = new File(co.getOutputDir() + co.getProjectName() + File.separator + file);
            File targetParent = target.getParentFile();
            if (!targetParent.exists()) {
                targetParent.mkdirs();
            }
            System.out.println(String.format("copy file: %s --> %s",
                    "/templates/" + file, co.getProjectName() + File.separator + file));
            Files.copy(ResourcesUtil.getResourceAsStream("/templates/" + file),
                    new File(co.getOutputDir() + co.getProjectName() + File.separator + file).toPath());
        }
    }

}
