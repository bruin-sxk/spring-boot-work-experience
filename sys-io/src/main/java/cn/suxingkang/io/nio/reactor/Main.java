package cn.suxingkang.io.nio.reactor;

public class Main {


	public static void main(String[] args) {

		// 1.
		SelectorThreadGroup group = new SelectorThreadGroup(3);

		group.bind(9960);

	}



}
