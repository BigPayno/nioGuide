package nio04selector;

public class NIOEchoClient01 {

	public static void main(String[] args) {
		new Thread(new NIOEchoClientHandler(NIOEchoServer.HOST, NIOEchoServer.PORT), "NIO-Client-001").start();
	}
}