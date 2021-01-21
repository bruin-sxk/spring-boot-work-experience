package cn.suxingkang.io.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {


	private SelectorThread bossThread;
	private SelectorThread[] workerThreads;

	private AtomicInteger tid = new AtomicInteger(0);

	public SelectorThreadGroup(int workerThreadNum) {
		int count = Math.max(2, workerThreadNum);
		workerThreads = new SelectorThread[count];
		for (int i = 0; i < count; i++) {
			workerThreads[i] = new SelectorThread();
			new Thread(workerThreads[i]).start();
		}
	}


	public void bind(int port) {
		try {
			ServerSocketChannel open = ServerSocketChannel.open();
			open.bind(new InetSocketAddress(port));
			open.configureBlocking(false);
			System.out.println("服务器已启动，端口号：" + port);
			while (true) {
				SocketChannel client = open.accept();

				if (Objects.nonNull(client)) {
					pollDeal2Client(client);
				}
//				System.out.println("执行一次 accept 。。。");
			}
		} catch (IOException e) {
			//
		}
	}

	public void pollDeal2Client(SocketChannel channel) {
		int index = tid.incrementAndGet() % workerThreads.length;
		try {
			workerThreads[index].getChannels().put(channel);
			workerThreads[index].getSelector().wakeup();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
