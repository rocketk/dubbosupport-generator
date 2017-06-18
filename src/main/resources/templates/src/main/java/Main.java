package ${basePackageName};

import ${basePackageName}.container.Container;

/**
 * Created by Generator.
 */
public class Main {
	public static void main(String[] args) {
		Container container = new Container();
		container.start();
		synchronized (Main.class) {
			while (true) {
				try {
					Main.class.wait();
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
