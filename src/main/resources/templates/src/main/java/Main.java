package py.dubbosupport.sample.provider.container;

import py.dubbosupport.container.Container;

/**
 * Created by pengyu on 2015/5/28.
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
