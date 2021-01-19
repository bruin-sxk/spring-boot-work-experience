package cn.suxingkang.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Objects;

public class SocketClient {


	public static void main(String[] args) throws IOException {

		Socket client = new Socket("10.20.100.73", 9960);

		client.setSendBufferSize(20);
		/*
		 *  tcp no delay
		 *  TcpNoDelay=false，为启用 nagle 算法，也是默认值。
		 *  Nagle 算法：主要是避免网络中的充塞小封包，提高网络的利用率
		 *  Delayed ACK ： 为了提高 tcp 的性能，应答数据时带上 ACK,可以避免糊涂窗口综合症，也可以一个 ack 确认多个段来节省开销。
		 *
		 *  实验 ：
		 *  假设一端发送数据并等待另一端应答，协议上分为头部和数据，发送的时候不幸地选择了write-write，然后再read，也就是先发送头部，再发送数据，最后等待应答。
		 *	发送端（客户端）
		 *	write(head);
		 *	write(body);
		 *	read(response);
		 *	接收端（服务端）
		 *	read(request);
		 *	process(request);
		 *	write(response);
		 *  这里假设head和body都比较小，当默认启用 nagle 算法，并且是第一次发送的时候，根据 nagle 算法，第一个段head可以
		 *  立即发送，因为没有等待确认的段；接收端（服务端）收到head，但是包不完整，继续等待 body 达到并延迟ACK；发送端
		 *  （客户端）继续写入body，这时候 nagle 算法起作用了，因为 head 还没有被ACK，所以body要延迟发送。这就造成了发送
		 *  端（客户端）和接收端（服务端）都在等待对方发送数据的现象：
		 *
		 *  发送端（客户端）等待接收端ACK head以便继续发送body；
		 *  接收端（服务端）在等待发送方发送body并延迟ACK，悲剧的无以言语。
		 *  这种时候只有等待一端超时并发送数据才能继续往下走。
		 */
		client.setTcpNoDelay(true);

		OutputStream out = client.getOutputStream();

		InputStream in = System.in;

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		while (true) {
			String line = reader.readLine();
			if (Objects.nonNull(line)) {
				byte[] bytes = line.getBytes();
				for (byte aByte : bytes) {
					out.write(aByte);
				}
			}
		}
	}
}
