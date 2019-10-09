package com.king.easychat.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.LengthFieldBasedFrameDecoder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class Spliter : LengthFieldBasedFrameDecoder(Int.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH) {

    companion object{
        const val LENGTH_FIELD_OFFSET = 7
        const val LENGTH_FIELD_LENGTH = 4
    }

    override fun decode(ctx: ChannelHandlerContext?, byteBuf: ByteBuf?): Any {

        val magicNumber = byteBuf?.getInt(byteBuf?.readerIndex())
        //如果魔数不匹配则直接关闭通道
        if(magicNumber != PacketHelper.MAGIC_NUMBER){
            ctx?.channel()?.close()
        }

        return super.decode(ctx, byteBuf)

    }
}