package nio.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 第2步：访问buffer
 */
public class B_BufferAccess {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		printBuffer(buffer);

		//写入数据，position右移
		buffer.put((byte)'H').put((byte)'e').put((byte)'l').put((byte)'l').put((byte)'0');
		printBuffer(buffer);

		//读写状态转换，原来是读就转为写，position和limit定位到未写部分的两端，原来是写就转为读，position和limit定位到已有内容的两端
		buffer.flip();
		printBuffer(buffer);

		//读取取buffer，position右移
		System.out.println("" + (char) buffer.get() + (char) buffer.get());
		printBuffer(buffer);

		//标记当前位置
		buffer.mark();
		printBuffer(buffer);

		//读取两个元素后，恢复到之前mark的位置处
		System.out.println("" + (char) buffer.get() + (char) buffer.get());
		printBuffer(buffer);

		buffer.reset();
		//buffer.rewind();

		printBuffer(buffer);


		// 压缩buffer，
		// 将 position 与 limit之间的数据复制到buffer的开始位置，复制后 position  = limit -position,limit = capacity
		// 但如果position 与limit 之间没有数据的话发，就不会进行复制
		buffer.compact();
		printBuffer(buffer);
		
		//清空buffer
		buffer.clear();
		printBuffer(buffer);

	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[position = " + buffer.position()
				+", limit=" + buffer.limit()
				+", capacity = " + buffer.capacity()
 				+", array = " + buffer.toString()+"]");
	}
}
