package bio.multithread.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * bio 多线程处理器
 *
 * @author guoyibin
 */
public class ServerHandler implements Runnable {
	Socket clientSocket = null;
	int clientNo = 0;

	ServerHandler(Socket socket, int i) {
		if (socket != null) {
			clientSocket = socket;
			clientNo = i;
			System.out.println("创建线程为[" + clientNo + "]号客户服务...");
		}
	}

	@Override
	public void run() {
		PrintStream os;
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				// 输入'quit'退出
				if (inputLine.equals("quit")) {
					System.out.println("关闭与客户端[" + clientNo + "]......" + clientNo);
					os.close();
					in.close();
					clientSocket.close();
					break;
				} else {
					System.out.println("来自客户端[" + clientNo + "]的输入： [" + inputLine + "]！");
					os.println("来自服务器端的响应：" + inputLine);
				}
			}
		} catch (IOException e) {
			System.out.println("连接["+clientNo+"]关闭！");
		}
	}
}
