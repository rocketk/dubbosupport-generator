package py.dubbosupport;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author pengyu
 */
public class Generator {
    final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
    final ConfigObject co = new ConfigObject();
    final String DEFAULT_OUTPUT_DIR = System.getProperty("user.dir") + File.separator;


    private Generator() {
        loadConfig();
        loadFreemarkerConfiguration();
    }

    private void loadConfig() {
        co.setApplicationName(ConfigUtil.getString("applicationName"));
        co.setArtifactId(ConfigUtil.getString("artifactId"));
        co.setGroupId(ConfigUtil.getString("groupId"));
        String outputDir = ConfigUtil.getString("outputDir");
        co.setOutputDir((outputDir == null || outputDir.isEmpty()) ? DEFAULT_OUTPUT_DIR : outputDir);
        if (!co.getOutputDir().endsWith(File.separator)) {
            co.setOutputDir(co.getOutputDir() + File.separator);
        }
        co.setBasePackageName(ConfigUtil.getString("basePackageName"));
        co.setBasePackageNamePath(packageToPath(ConfigUtil.getString("basePackageName")));
        co.setProjectName(ConfigUtil.getString("projectName"));
        co.setProviderPort(ConfigUtil.getString("providerPort"));
        co.setVersion(ConfigUtil.getString("version"));
        co.setZookeeper(ConfigUtil.getString("zookeeper"));
        co.setSpringVersion(ConfigUtil.getString("springVersion"));
        co.setDubboVersion(ConfigUtil.getString("dubboVersion"));
        co.setJavaVersion(ConfigUtil.getString("javaVersion"));
        co.setZookeeperVersion(ConfigUtil.getString("zookeeperVersion"));
        co.setNexusRepoId(ConfigUtil.getString("nexusRepoId"));
        co.setNexusReleaseUrl(ConfigUtil.getString("nexusReleaseUrl"));
        co.setNexusSnapshotUrl(ConfigUtil.getString("nexusSnapshotUrl"));
    }

    private void loadFreemarkerConfiguration() {
        cfg.setClassForTemplateLoading(Generator.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
    }

    public static void main(String[] args) throws IOException, TemplateException {
        Generator main = new Generator();
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
        String packageDirStr = packageName.replaceAll("\\.", File.separator);
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
        String packageName = path.replaceAll("/", "\\.");
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
        Template template = cfg.getTemplate(replaceWithFileSeparator(templateStr));
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
        Template template = cfg.getTemplate(replaceWithFileSeparator(templateStr));
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

    /**
     * 将unix路径名称中的"/"用运行时系统(windows, linux, mac os)的路径分隔符替代
     *
     * @param path
     * @return
     */
    private String replaceWithFileSeparator(String path) {
        if (path == null) {
            return null;
        }
        return path.trim().replaceAll("/", File.separator);
    }

}
