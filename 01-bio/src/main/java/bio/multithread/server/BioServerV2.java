package bio.multithread.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * bio 多线程服务端2
 *
 * @author guoyibin
 */
public class BioServerV2 {

	public static void main(String[] args) {
		ServerSocket server;
		//设置两个线程，这样可以测试启动三个客户端时，第三个会阻塞
		Executor executor = Executors.newFixedThreadPool(2);
		int i = 0;
		System.out.println("服务器在端口[" + HostAndPort.PORT + "]等待客户请求......");
		try {
			server = new ServerSocket(HostAndPort.PORT);
			while (true) {
				Socket clientRequest = server.accept();
				executor.execute(new ServerHandler(clientRequest, i++));
			}
		} catch (IOException e) {
			System.out.println("bio服务器退出！");
		}
	}
}
