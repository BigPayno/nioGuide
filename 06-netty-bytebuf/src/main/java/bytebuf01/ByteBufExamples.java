package bytebuf01;

import io.netty.buffer.*;
import io.netty.util.ByteProcessor;

import java.nio.charset.Charset;

 
 
 
public class ByteBufExamples {

    public static void main(String[] args) {
        /* 随机读写，不移动指针 */
    	byteBufSetGet();
    	/* 顺序读写，顺序移动读写指针 */
        byteBufWriteRead();
    	/* 写入和读取int类型数据 */
        writeIntAndReadInt();
        /* 获取第一个\r的位置 */
        byteProcessor();
        /* slice(切分)操作 */
    	byteBufSlice();
        /* copy深拷贝操作 */
    	byteBufCopy();
        /* 复合缓冲区的操作 */
    	byteBufComposite();
        /* 堆buf操作 */
    	heapBuffer();
        /* 堆外buf */
        directBuffer();

    }

    /* 随机读写，不移动指针 */
    public static void byteBufSetGet() {
        // 设置编码
        Charset utf8 = Charset.forName("UTF-8");
        // 创建一个bytebuf，内容是 "Netty in Action rocks!"
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        // 打印第0个字节（N）
        System.out.println((char)buf.getByte(0));
        //获取读指针
        int readerIndex = buf.readerIndex();
        //获取写指针
        int writerIndex = buf.writerIndex();
        //打印出读指针和写指针
        System.out.println("readerIndex = " + readerIndex + "; writerIndex = " + writerIndex);
        // 改变第0个字节为B
        buf.setByte(0, (byte)'B');
        //再次打印第0个字节
        System.out.println((char)buf.getByte(0));
        //再次打印读写指针
        System.out.println("readerIndex = " + buf.readerIndex() + "; writerIndex = " + buf.writerIndex());
    }


    /* 顺序读写，顺序移动读写指针 */
    public static void byteBufWriteRead() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //未操作前获取并打印读写指针
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        System.out.println("readerIndex = " + readerIndex + "; writerIndex = " + writerIndex);

        // 顺序读取一个字节并打印出来（N），同时读指针右移一位
        System.out.println((char)buf.readByte());

        // 读操作后获取并打印读写指针
        readerIndex = buf.readerIndex();
        writerIndex = buf.writerIndex();
        System.out.println("readerIndex = " + readerIndex + "; writerIndex = " + writerIndex);

        // 顺序写入一个自己，同时写指针右移一位
        buf.writeByte((byte)'?');
        //打印读写指针
        System.out.println("readerIndex = " + buf.readerIndex() + "; writerIndex = " + buf.writerIndex());

        // 再次顺序读取一个字节，然后打印出读写指针
        buf.readByte();
        System.out.println("readerIndex = " + buf.readerIndex() + "; writerIndex = " + buf.writerIndex());
    }

    /* 写入和读取int类型数据 */
    public static void writeIntAndReadInt() {
        //创建一个长度为20的非池化ByteBuf
        ByteBuf buffer = Unpooled.buffer(20);
        int i = 0;
        //总长度为20，当剩余的可写长度大于等于4的时候，写入一个长度为4的int类型的数字
        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(i++);
        }
        //循环读取
        while (buffer.isReadable()) {
            System.out.println(buffer.readInt());
        }
    }

    /* 获取第一个\r的位置 */
    public static void byteProcessor() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buffer = Unpooled.copiedBuffer("Netty\r in Action rocks! ", utf8);
        int index = buffer.forEachByte(ByteProcessor.FIND_CR);
        System.out.println(index);
    }

    /* slice(切分)操作 */
    public static void byteBufSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //从0位置开始切分长度为15的ByteBuf
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(utf8));

        //把原来的buf的0位置写入一个数据，切分出来的也会改变，说明是浅拷贝
        buf.setByte(0, (byte)'J');
        System.out.println(sliced.toString(utf8));
    }

    /* copy深拷贝操作 */
    public static void byteBufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        //把原来从0位置开始长度为15的数据拷贝到一个新的ByteBuf中
        ByteBuf copy = buf.copy(0, 15);
        System.out.println(copy.toString(utf8));

        //把原来的写入一个字符，拷贝出来的没有变化，说明是深拷贝
        buf.setByte(0, (byte)'J');
        System.out.println(copy.toString(utf8));
    }

    /* 复合缓冲区的操作 */
    public static void byteBufComposite() {
        //创建一个复合缓冲区
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();

        //创建两个buf并加入复合缓冲区
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf headerBuf = Unpooled.copiedBuffer("Header", utf8);
        ByteBuf bodyBuf = Unpooled.copiedBuffer("This is body", utf8);
        messageBuf.addComponents(headerBuf, bodyBuf);

        //循环读取复合缓冲区
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString(utf8));
        }

        //移除复合缓冲区的第0个buf，也就是移除headerBuf，再打印就剩下一个
        messageBuf.removeComponent(0);
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString(utf8));
        }
    }

    /* 堆buf操作 */
    public static void heapBuffer() {
        //在堆上创建一个长度为16的buf
        ByteBuf heapBuf = Unpooled.buffer(16);

        //如果底层是数组就写入数据 这不是writeInt方法，所以可以写入16个数字
        if (heapBuf.hasArray()) {
            int i = 0;
            while (heapBuf.writableBytes() > 0) {
            	heapBuf.writeByte(i++);
            }

            //从buf中获取数组
            byte[] array = heapBuf.array();

            // 返回offset+读指针位置，此处为0
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();

            //获取buf中可读数据的长度 也就是16
            int length = heapBuf.readableBytes();

            //循环打印
            handleArray(array, offset, length);
        }
    }


    /* 堆外buf */
    public static void directBuffer() {
        // 在堆外创建一个长度为16的buf
        ByteBuf directBuf = Unpooled.directBuffer(16);    

        //堆外buf底层不是数组
        if (!directBuf.hasArray()) {
            int i = 0;
            while (directBuf.writableBytes() > 0) {
            	directBuf.writeByte(i++);
            }
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            handleArray(array, 0, length);
        }
    }

  


    private static void handleArray(byte[] array, int offset, int len) {
    	for(int i = 0; i<len; i++) {
    		System.out.println(array[offset+i]);
    	}
    }

}
