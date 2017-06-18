package py.dubbosupport;

/**
 * Created by pengyu on 2017/6/18.
 */
public class MetaConfig {
    public static final String JAVA_MAIN_TEMPLATES_DIR = "src/main/java/"; // java类模板在classpath中的位置
    public static final String JAVA_TEST_TEMPLATES_DIR = "src/test/java/"; // java类模板在classpath中的位置
    public static final String OUTPUT_JAVA_MAIN_DIR = "src/main/java/"; // java类输出目录
    public static final String OUTPUT_JAVA_TEST_DIR = "src/test/java/"; // java类输出目录
    public static final String[] JAVA_TEMPLATE_FILES = {
            JAVA_MAIN_TEMPLATES_DIR + "Main.java",
            JAVA_MAIN_TEMPLATES_DIR + "container/Container.java",
            JAVA_MAIN_TEMPLATES_DIR + "service/SimpleService.java",
            JAVA_MAIN_TEMPLATES_DIR + "service/impl/SimpleServiceImpl.java",
            JAVA_TEST_TEMPLATES_DIR + "SimpleServiceTest.java"
    };

    public static final String[] RESOURCE_TEMPLATE_FILES = {
            "pom.xml",
            "src/main/assembly/full.xml",
            "src/main/assembly/interface-only.xml",
            "src/main/mvn-scripts/deploy-interface-release.bat",
            "src/main/mvn-scripts/deploy-interface-snapshot.bat",
            "src/main/mvn-scripts/mvn-install.bat",
            "src/main/resources/application.properties",
            "src/main/resources/consumer.xml",
            "src/main/resources/log4j2.xml",
            "src/main/resources/provider.xml",
            "src/main/resources/spring.xml",
            "src/test/resources/consumer.xml",
            "src/test/resources/spring.xml"
    };

    public static final String[] COPY_ONLY_FILES = {
            "src/main/resources/META-INF/assembly/bin/log.sh",
            "src/main/resources/META-INF/assembly/bin/start.sh",
            "src/main/resources/META-INF/assembly/bin/status.sh",
            "src/main/resources/META-INF/assembly/bin/stop.sh"
    };

}
