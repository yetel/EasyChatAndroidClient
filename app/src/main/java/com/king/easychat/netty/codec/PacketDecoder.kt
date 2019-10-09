package com.king.easychat.netty.codec

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class PacketDecoder : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext?, byteBuf: ByteBuf?, out: MutableList<Any>?) {

        byteBuf?.let {
            out?.add(PacketHelper.decode(it))
        }

    }

}