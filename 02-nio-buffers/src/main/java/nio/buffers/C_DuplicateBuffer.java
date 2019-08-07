package nio.buffers;

import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * 第3步：复制buffer Duplicate表示浅拷贝，也就是只复制饮用，对象实例还是指向一个
 */
public class C_DuplicateBuffer {

	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(8);
		for(int i= 0 ; i < buffer.capacity() ; i++) {
			buffer.put(String.valueOf(i).charAt(0));
		}
		//写满八个数据，position与limit位置相同
		printBuffer(buffer);

		//调转读写状态，position回到最开始
		buffer.flip();
		printBuffer(buffer);

		//手动定位位置到3和6，mark一次，然后又单独定位position到5
 		buffer.position(3).limit(6).mark().position(5);
 		printBuffer(buffer);

 		//浅复制一份，包括原来的position和limit的位置也一起复制了过来
		CharBuffer dupeBuffer = buffer.duplicate();
		//原来的buffer清空，position和limit的位置复位
		buffer.clear();
 		printBuffer(buffer);
 		//拷贝出来的position和limit的位置还是最后清空前的位置
		printBuffer(dupeBuffer);

		//拷贝出来的也清空，position和limit的位置也回到最左和最右
 		dupeBuffer.clear();
		printBuffer(dupeBuffer);
		
	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[position=" + buffer.position()
				+", limit = " + buffer.limit()
				+", capacity = " + buffer.capacity()
				+", array = " + buffer.toString()+"]");
	}

}
