package cn.suxingkang.io.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class MyNettySimple {

	private AtomicInteger at = new AtomicInteger(0);

	/**
	 * netty 可读写 buffer 属性一览
	 */
	@Test
	public void myByteBuf() {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
		print(byteBuf);

		allocAndPrint(byteBuf);
		allocAndPrint(byteBuf);
		allocAndPrint(byteBuf);
		allocAndPrint(byteBuf);
		allocAndPrint(byteBuf);
		allocAndPrint(byteBuf);

	}

	private void allocAndPrint(ByteBuf byteBuf) {
		byteBuf.writeBytes(new byte[] {1, 2, 3, 4});
		print(byteBuf);
	}

	private void print(ByteBuf buf) {
		System.out.println("print number : " + at.incrementAndGet());
		System.out.println("buf.isReadable  -->" + buf.isReadable());
		System.out.println("buf.readerIndex  -->" + buf.readerIndex());
		System.out.println("buf.readableBytes  -->" + buf.readableBytes());
		System.out.println("buf.isWritable  -->" + buf.isWritable());
		System.out.println("buf.writerIndex  -->" + buf.writerIndex());
		System.out.println("buf.writableBytes  -->" + buf.writableBytes());
		System.out.println("buf.capacity  -->" + buf.capacity());
		System.out.println("buf.maxCapacity  -->" + buf.maxCapacity());
		System.out.println("buf.isDirect  -->" + buf.isDirect());
		System.out.println("=====================================================");
	}

	/**
	 * 客户端 连接服务器，获取服务器数据 1. 主动发数据 2. 服务器什么时候返回数据
	 */
	@Test
	public void clientHandWriteByNetty() throws InterruptedException {
		NioEventLoopGroup thread = new NioEventLoopGroup(1);
		// 客户端模式
		NioSocketChannel client = new NioSocketChannel();

		thread.register(client);

		// 响应式
		ChannelPipeline pipeline = client.pipeline();
		pipeline.addLast(new MyInHandler());

		ChannelFuture connect = client.connect(new InetSocketAddress("127.0.0.1", 9999));
		ChannelFuture sync = connect.sync();

		ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
		ChannelFuture send = client.writeAndFlush(buf);

		send.sync();

		sync.channel().closeFuture().sync();

		System.out.println("client close ...");

	}

	@Test
	public void clientNettyWayWrite() throws InterruptedException {

		NioEventLoopGroup group = new NioEventLoopGroup(1);

		Bootstrap bs = new Bootstrap();
		ChannelFuture connect =
				bs.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						ChannelPipeline pipeline = sc.pipeline();
						pipeline.addLast(new MyInHandler());
					}
				}).connect(new InetSocketAddress("127.0.0.1", 9999));

		Channel client = connect.sync().channel();

		ByteBuf byteBuf = Unpooled.copiedBuffer("hello server".getBytes());
		ChannelFuture cf = client.writeAndFlush(byteBuf);
		cf.sync();

		client.closeFuture().sync();

	}


}


/**
 * @ ChannelHandler.Sharable
 */
class MyInHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client  registed...");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client active...");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		CharSequence cs = buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8);
		System.out.println(cs);
		ctx.writeAndFlush(buf);
	}
}
