package ${basePackageName}.container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Generator.
 */
public class Container {
	private final Logger logger = LoggerFactory.getLogger(${basePackageName}.container.Container.class);
	/**
	 * spring配置文件名必须是spring.xml，并且必须放在resources下面
	 */
	public static final String DEFAULT_SPRING_CONFIG = "classpath*:/spring.xml";
	private ClassPathXmlApplicationContext context;
	private String[] springConfigFiles;

	public Container() {
	}

	public Container(String... springConfigFiles) {
		this.springConfigFiles = springConfigFiles;
	}

	public void start() {
		if (springConfigFiles == null || springConfigFiles.length == 0) {
			context = new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG);
		} else {
			context = new ClassPathXmlApplicationContext(springConfigFiles);
		}
		context.start();
	}

	public void stop() {
		try {
			if (context != null) {
				context.stop();
				context.close();
				context = null;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}

	public String[] getSpringConfigFiles() {
		return springConfigFiles;
	}

	public void setSpringConfigFiles(String[] springConfigFiles) {
		this.springConfigFiles = springConfigFiles;
	}
}
