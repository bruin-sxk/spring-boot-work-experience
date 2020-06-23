package com.sxk.leetcode.duoxiancheng;

public class PrintInOrder {

	public static void main(String[] args) {
		Foo foo = new Foo();
		new Thread(()->{
			foo.one();
		}).start();
		new Thread(()->{
			foo.two();
		}).start();
		new Thread(()->{
			foo.three();
		}).start();
	}


	public static class Foo {
		private volatile int num = 0;
		public void one() {
			System.out.println("one");
			num++;
		}

		public void two() {
			while(num != 1){}
			System.out.println("two");
			num++;
		}

		public void three() {
			while(num != 2){}
			System.out.println("three");
		}
	}
}
