package nio04selector;

public class NIOEchoServer {

	public static final int PORT = 8080;
	public static final String HOST = "127.0.0.1";

	public static void main(String[] args) {
		NIOEchoServerHandler timeServer = new NIOEchoServerHandler(PORT);
		new Thread(timeServer, "NIO-SERVER-001").start();
	}
}