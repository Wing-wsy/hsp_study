//package cn.itcast.netty.chardemo.protocol;
//
//import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
//
///**
// * @author wing
// * @create 2024/10/22
// */
//public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {
//
//    public ProcotolFrameDecoder() {
//        this(1024, 12, 4, 0, 0);
//    }
//
//    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
//        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
//    }
//}
