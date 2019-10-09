package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.Packet
import com.king.easychat.util.Event
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class RespHandler<T : Packet> : SimpleChannelInboundHandler<T>(){
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: T) {
        msg.let {
            Timber.d(it.toString())
            Event.sendEvent(it)
        }
    }

}