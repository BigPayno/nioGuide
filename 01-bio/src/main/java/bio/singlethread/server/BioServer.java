package bio.singlethread.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bio 单线程服务端
 *
 * @author guoyibin
 */
public class BioServer {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8080;

    public static void main(String[] args) {
        ServerSocket server;
        System.out.println("服务器在端口[" + PORT + "]等待客户请求......");
        try {
            server = new ServerSocket(PORT);
            int i = 0;
            while (true) {
                Socket clientRequest = server.accept();
                handleRequest(clientRequest, i++);
            }
        } catch (IOException e) {
            System.out.println("bio服务器退出！");
        }

    }

    public static void handleRequest(Socket clientSocket, int clientNo) {
        PrintStream os;
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            String inputLine;

            while ((inputLine = in.readLine()) != null) {

                // 输入'quit'退出
                if ("quit".equals(inputLine)) {
                    System.out.println("关闭与客户端[" + clientNo + "]......" + clientNo);
                    os.close();
                    in.close();
                    clientSocket.close();
                    break;
                } else {
                    //打印接收到的客户端的消息，并原样返回客户端
                    System.out.println("来自客户端[" + clientNo + "]的输入： " + inputLine);
                    os.println("来自服务器端的响应：" + inputLine);
                }
            }
        } catch (IOException e) {
            System.out.println("连接["+ clientNo +"]关闭！");
        }

    }
}
