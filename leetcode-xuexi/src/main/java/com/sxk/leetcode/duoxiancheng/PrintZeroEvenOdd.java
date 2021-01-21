package com.sxk.leetcode.duoxiancheng;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;

/**
 * 假设有这么一个类：
 *
 */
public class PrintZeroEvenOdd {


	public static void main(String[] args) {
		int n = 51;
		ZeroEvenOdd z = new ZeroEvenOdd(n);
		new Thread(() -> {
			try {
				z.zero(System.out::print);
			} catch (InterruptedException e) {
			}
		}).start();
		new Thread(() -> {
			try {
				z.even(System.out::print);
			} catch (InterruptedException e) {
			}
		}).start();
		new Thread(() -> {
			try {
				z.odd(System.out::print);
			} catch (InterruptedException e) {
			}
		}).start();
	}

}


class ZeroEvenOdd {

	private int n;

	private AtomicInteger ai = new AtomicInteger(0);

	private PrintEvent event =  new PrintEvent(0);

	private Queue<PrintEvent> zero = new LinkedList<>();
	private Queue<PrintEvent> even = new LinkedList<>();
	private Queue<PrintEvent> odd = new LinkedList<>();

	public ZeroEvenOdd(int n) {
		zero.add(new PrintEvent(0));
		this.n = n;
	}

	public void zero(IntConsumer printNumber) throws InterruptedException {
		while (true) {
			if (!zero.isEmpty()) {
				int v = ai.incrementAndGet();
				if (ai.get() > n){
					break;
				}
				PrintEvent take = zero.poll();
				printNumber.accept(take.getV());
				event.setV(v);
				if (v % 2 != 0) {
					odd.add(event);
				} else {
					even.add(event);
				}
			}
		}
	}

	public void even(IntConsumer printNumber) throws InterruptedException {
		while (ai.get() <= n) {
			if (!even.isEmpty()) {
				PrintEvent take = even.poll();
				printNumber.accept(take.getV());
				event.setV(0);
				zero.add(event);
			}
		}
	}

	public void odd(IntConsumer printNumber) throws InterruptedException {
		while (ai.get() <= n) {
			if (!odd.isEmpty()) {
				PrintEvent take = odd.poll();
				printNumber.accept(take.getV());
				event.setV(0);
				zero.add(event);
			}
		}
	}
}


class PrintEvent {
	private int v;

	public PrintEvent(int v) {
		this.v = v;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}
}

