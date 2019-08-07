package nio.buffers;

import java.nio.ByteBuffer;

/**
 * 第1步：创建buffer
 *
 * capacity: 缓冲区的元素总数（不可修改）。
 *
 * position: 下一个要读写的元素位置（从0开始）。
 *
 * limit: 第一个不可读写的位置。
 *
 * mark:  用户选定的position的前一个位置或0。
 */
public class A_BufferCreate {
	public static void main(String[] args) {
		/**
		 * allocate是创建了自己的后援数组，
		 * 在缓冲区上调用array()方法也可获得后援数组的引用。
		 * 通过调用arrayOffset()方法，甚至可以获取缓冲区中第一个元素在后援数组的偏移量。
		 * 但是使用wrap创建的ByteBuffer，调用arrayOffset永远是0
		 */
		ByteBuffer buffer0 = ByteBuffer.allocate(10);
		if (buffer0.hasArray()) {
			System.out.println("buffer0 array: " + buffer0.array());
			System.out.println("Buffer0 array offset: " + buffer0.arrayOffset());

		}
		System.out.println("Position: " + buffer0.position());
		System.out.println("Limit: " + buffer0.limit());
		System.out.println("Capacity: " + buffer0.capacity());
		System.out.println("Remaining: " + buffer0.remaining());
		System.out.println();

		ByteBuffer buffer1 = ByteBuffer.allocateDirect(10);
		if (buffer1.hasArray()) {
			System.out.println("buffer1 array: " + buffer1.array());
			System.out.println("Buffer1 array offset: " + buffer1.arrayOffset());
		}
		System.out.println("Position: " + buffer1.position());
		System.out.println("Limit: " + buffer1.limit());
		System.out.println("Capacity: " + buffer1.capacity());
		System.out.println("Remaining: " + buffer1.remaining());
		System.out.println();

		/**
		 *  wrap只是简单地创建一个具有指向被包装数组的引用的缓冲区，
		 *  该数组成为后援数组。对后援数组中的数据做的任何修改都将改变缓冲区中的数据，反之亦然
		 */
		byte[] bytes = new byte[10];
		ByteBuffer buffer2 = ByteBuffer.wrap(bytes);
		if (buffer2.hasArray()) {
			System.out.println("buffer2 array: " + buffer2.array());
			System.out.println("Buffer2 array offset: " + buffer2.arrayOffset());

		}
		System.out.println("Position: " + buffer2.position());
		System.out.println("Limit: " + buffer2.limit());
		System.out.println("Capacity: " + buffer2.capacity());
		System.out.println("Remaining: " + buffer2.remaining());
		System.out.println();

		byte[] bytes2 = new byte[10];
		//指定范围
		ByteBuffer buffer3 = ByteBuffer.wrap(bytes2, 2, 3);
		if (buffer3.hasArray()) {
			System.out.println("buffer3 array: " + buffer3.array());
			System.out.println("Buffer3 array offset: " + buffer3.arrayOffset());
		}
		System.out.println("Position: " + buffer3.position());
		System.out.println("Limit: " + buffer3.limit());
		System.out.println("Capacity: " + buffer3.capacity());
		System.out.println("Remaining: " + buffer3.remaining());

	}
}
