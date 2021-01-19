package cn.suxingkang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Objects;

public class SocketNio {


	public static void main(String[] args) throws IOException {

		LinkedList<SocketChannel> clients = new LinkedList<>();

		ServerSocketChannel open = ServerSocketChannel.open();
		// 绑定端口
		open.bind(new InetSocketAddress(9960));
		// 配置不阻塞 也就是 nio
		open.configureBlocking(false);

		System.out.println("服务器启动完成。。。");

		while (true) {

			SocketChannel accept = open.accept();

			if (Objects.nonNull(accept)) {
				accept.configureBlocking(false);
				int port = accept.socket().getPort();
				System.out.println("client..port: " + port);
				clients.add(accept);
			}

			ByteBuffer buffer = ByteBuffer.allocate(4096);

			for (SocketChannel client : clients) {
				int read = client.read(buffer);

				if (read > 0) {
					buffer.flip();

					byte[] bytes = new byte[buffer.limit()];
					buffer.get(bytes);

					String readStr = new String(bytes);
					System.out.println(client.socket().getPort() + ":-->" + readStr);
					buffer.clear();
				}
			}
		}
	}
}
