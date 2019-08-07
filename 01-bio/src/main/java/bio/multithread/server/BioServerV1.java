package bio.multithread.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * bio 多线程服务端1
 *
 * @author guoyibin
 */
public class BioServerV1 {

    public static void main(String[] args) {
		ServerSocket server;
		System.out.println("服务器在端口[" + HostAndPort.PORT + "]等待客户请求......");
		int i = 0;
		try {
			server = new ServerSocket(HostAndPort.PORT);
			while (true) {
				Socket clientRequest = server.accept();
				new Thread(new ServerHandler(clientRequest, i++)).start();
			}
		} catch (IOException e) {
			System.out.println("bio服务器退出！");
		}
     }
}
