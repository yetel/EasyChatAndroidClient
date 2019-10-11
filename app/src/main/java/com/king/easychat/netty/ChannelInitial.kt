package com.king.easychat.netty

import com.king.easychat.netty.codec.PacketDecoder
import com.king.easychat.netty.codec.PacketEncoder
import com.king.easychat.netty.codec.Spliter
import com.king.easychat.netty.handle.HeartBeatRespHandler
import com.king.easychat.netty.handle.LoginRespHandler
import com.king.easychat.netty.handle.MessageRespHandler
import com.king.easychat.netty.handle.RespHandler
import com.king.easychat.netty.packet.Packet
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChannelInitial : ChannelInitializer<SocketChannel>(){

    override fun initChannel(ch: SocketChannel?) {
        ch?.pipeline()?.let {
            it.addLast(Spliter())
                .addLast(PacketDecoder())
                .addLast(PacketEncoder())
                .addLast(LoginRespHandler())
                .addLast(MessageRespHandler())
                .addLast(HeartBeatRespHandler())
        }
    }

}