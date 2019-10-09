package com.king.easychat.netty.codec

import com.king.easychat.netty.packet.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class PacketEncoder : MessageToByteEncoder<Packet>() {

    @ExperimentalStdlibApi
    override fun encode(ctx: ChannelHandlerContext?, msg: Packet?, out: ByteBuf?) {
        out?.let {
            msg?.run {
                PacketHelper.encode(it,this)
            }
        }

    }

}