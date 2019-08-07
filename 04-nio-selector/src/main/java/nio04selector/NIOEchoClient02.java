package nio04selector;

public class NIOEchoClient02 {

	public static void main(String[] args) {
		new Thread(new NIOEchoClientHandler(NIOEchoServer.HOST, NIOEchoServer.PORT), "NIO-Client-002").start();
	}
}