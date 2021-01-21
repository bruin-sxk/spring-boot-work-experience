package cn.suxingkang.io.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SelectorThread extends ThreadLocal<LinkedBlockingQueue<Channel>> implements Runnable {

	private Selector selector = null;

	private LinkedBlockingQueue<Channel> channels = get();

	public SelectorThread() {
		try {
			this.selector = Selector.open();
		} catch (IOException e) {
			// capture but do nothing
		}
	}

	@Override
	protected LinkedBlockingQueue<Channel> initialValue() {
		return new LinkedBlockingQueue<>();
	}

	@Override
	public void run() {

		try {
			while (true) {
				int canExecuteNum = selector.select();
				if (canExecuteNum > 0) {
					Set<SelectionKey> selectionKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectionKeys.iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();
						if (key != null) {
							if (key.isAcceptable()) {
								acceptHandler(key);
							} else if (key.isReadable()) {
								readableHandler(key);
							} else if (key.isWritable()) {

							}
						}
					}
				}
				if (!channels.isEmpty()) {
					SocketChannel sc = (SocketChannel) channels.take();
					System.out.println("chanel size" + channels.size());
					ByteBuffer buffer = ByteBuffer.allocate(4096);
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ, buffer);
					System.out.println(
							Thread.currentThread().getName() + "  : register client..." + sc.getRemoteAddress());
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}


	}

	private void readableHandler(SelectionKey key) {
		System.out.println(Thread.currentThread().getName() + "   readableHandler......");

		ByteBuffer buffer = (ByteBuffer) key.attachment();
		SocketChannel channel = (SocketChannel) key.channel();
		buffer.clear();
		try {
			while (true) {
				int read = channel.read(buffer);
				if (read > 0) {
					buffer.flip();
					while (buffer.hasRemaining()) {
						System.out.println(buffer);
						channel.write(buffer);
					}
					buffer.clear();
				} else if (read == 0) {
					break;
				} else {
					// 客户端断开了
					System.out.println("client: " + channel.getRemoteAddress() + "closed......");
					key.cancel();
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("client: closed......");
			key.cancel();
		}

	}

	private void acceptHandler(SelectionKey key) {
		System.out.println(Thread.currentThread().getName() + "   acceptHandler......");

		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		try {
			while (true) {
				SocketChannel accept = server.accept();
				accept.configureBlocking(false);
				// accept.register(selector,SelectionKey.OP_READ);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	public LinkedBlockingQueue<Channel> getChannels() {
		return channels;
	}

	public void setChannels(LinkedBlockingQueue<Channel> channels) {
		this.channels = channels;
	}
}
