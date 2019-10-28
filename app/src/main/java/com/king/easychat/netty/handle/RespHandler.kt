package com.king.easychat.netty.handle

import com.king.easychat.app.Constants
import com.king.easychat.bean.Operator
import com.king.easychat.util.Event
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class RespHandler<T : Any> : SimpleChannelInboundHandler<T>(){
    override fun channelRead0(ctx: ChannelHandlerContext?, msg: T) {
        Timber.d(msg.toString())
        Event.sendEvent(msg)

        if(!ctx?.channel()!!.isActive){
            Event.sendEvent(Operator(Constants.EVENT_NETTY_DISCONNECT))
        }
    }

}

