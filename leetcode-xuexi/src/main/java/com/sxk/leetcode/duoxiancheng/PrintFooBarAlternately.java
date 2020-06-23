package com.sxk.leetcode.duoxiancheng;

import java.util.concurrent.Semaphore;

public class PrintFooBarAlternately {

	public static void main(String[] args) {
		FooBar fooBar = new FooBar(10);
		new Thread(() -> {
			try {
				fooBar.foo();
			} catch (InterruptedException e) {

			}
		}).start();
		new Thread(() -> {
			try {
				fooBar.bar();
			} catch (InterruptedException e) {

			}
		}).start();
	}

	static class FooBar {
		private int n;
		private Semaphore foo = new Semaphore(1);
		private Semaphore bar = new Semaphore(0);

		public FooBar(int n) {
			this.n = n;
		}

		public void foo() throws InterruptedException {

			for (int i = 0; i < n; i++) {
				foo.acquire();
				// printFoo.run() outputs "foo". Do not change or remove this line.
				System.out.print("foo");
				bar.release();
			}
		}

		public void bar() throws InterruptedException {

			for (int i = 0; i < n; i++) {
				bar.acquire();
				// printBar.run() outputs "bar". Do not change or remove this line.
				System.out.println("bar  ");
				foo.release();
			}
		}
	}
}
