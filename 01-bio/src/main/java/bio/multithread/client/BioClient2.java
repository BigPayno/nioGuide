package bio.multithread.client;

import bio.multithread.server.HostAndPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * bio单线程客户端
 *
 * @author guoyibin
 */
public class BioClient2 {
	public static void main(String[] args) {
		Socket client;
		PrintWriter out;
		BufferedReader in;


		try{
			client = new Socket(HostAndPort.HOST, HostAndPort.PORT);
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			System.out.println("连接到服务器......");
			System.out.println("请输入消息[输入\"quit\"退出]：");

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String userInput;

			while ((userInput = reader.readLine()) != null) {
				out.println(userInput);
				System.out.println(in.readLine());

				if ("quit".equals(userInput)) {
					System.out.println("关闭客户端......");
					out.close();
					in.close();
					reader.close();
					client.close();
					System.exit(1);
				}
				System.out.println("请输入消息[输入\"quit\"退出]：");
			}
		} catch (UnknownHostException e) {
			System.out.println("未知域名");
		} catch (IOException e) {
			System.out.println("io异常");
		}


	}

}
