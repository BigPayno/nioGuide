package nio04selector;

public class NIOEchoClient03 {

	public static void main(String[] args) {
		new Thread(new NIOEchoClientHandler(NIOEchoServer.HOST, NIOEchoServer.PORT), "NIO-Client-003").start();
	}
}