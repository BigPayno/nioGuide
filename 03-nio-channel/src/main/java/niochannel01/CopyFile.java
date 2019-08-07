package niochannel01;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class CopyFile {
    static public void main(String args[]) throws Exception {

        String fromFile = "03-nio-channel/src/main/resources/from/a.txt";
        String toFile = "03-nio-channel/src/main/resources/to/a.txt";

        // 从流中获取通道
        FileInputStream fromInputStream = new FileInputStream(fromFile);
        FileOutputStream toOutPutStream = new FileOutputStream(toFile);

        FileChannel fromFileChannel = fromInputStream.getChannel();
        FileChannel toFileChannel = toOutPutStream.getChannel();

        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 读入之前要清空，清空后对于buffer来说变为写模式，可以写入数据到buffer
            buffer.clear();

            // position自动前进 从fromFileChannel中读取，写入到buffer
            int r = fromFileChannel.read(buffer);

            //读完了强制退出
            if (r == -1) {
                break;
            }

            // position = 0; limit=读到的字节数
            buffer.flip();

            // 从 buffer 中读  写入到 toFileChannel
            toFileChannel.write(buffer);
        }
    }
}
