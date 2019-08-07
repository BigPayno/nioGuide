package nio.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 第4步，切分buffer
 *
 * 调用该方法得到的新缓冲区所操作的数组还是原始缓冲区中的那个数组，
 * 不过，通过slice创建的新缓冲区只能操作原始缓冲区中数组剩余的数据，
 * 即索引为调用slice方法时原始缓冲区的position到limit索引之间的数据，
 * 超出这个范围的数据通过slice创建的新缓冲区无法操作到
 */
public class D_BufferSlice {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		for(int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte) i);
		}
		//打印出写满数据的位置，position和limit一样
		printBuffer(buffer);
		//手动定位position和limit
		buffer.position(3).limit(7);
		printBuffer(buffer);

		//根据定位到的位置，切分buffer，position为0，limit和capacity都为原来的position和limit之间的长度
		ByteBuffer sliceBuffer = buffer.slice();
		printBuffer(sliceBuffer);

		//循环切分出来的新buffer，一个一个读取出来，然后乘以11，然后放到原位置
		for(int i = 0;i < sliceBuffer.capacity();i++) {
			byte b = sliceBuffer.get();
			b *= 11;
			sliceBuffer.put(i, b);
		}
		//打印循环完的切分buffer，position和limit都在最右边
		printBuffer(sliceBuffer);

		//手动定位buffer的开始和结束位置
		buffer.position(0).limit(buffer.capacity());
		//循环读取打印原来buffer中的每个数据，发现切分的buffer修改的同时，原来的buffer也修改了，说明slice也是浅拷贝
		while(buffer.hasRemaining()) {
			System.out.println(buffer.get());
		}
		//读取到了最后，打印
		printBuffer(buffer);

	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[position=" + buffer.position()
				+", limit = " + buffer.limit()
				+", capacity = " + buffer.capacity()
				+", array = " + buffer.toString()+"]");
	}
}
