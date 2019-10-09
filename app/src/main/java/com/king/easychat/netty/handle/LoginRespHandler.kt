package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.LoginResp
import com.king.easychat.util.Event
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ChannelHandler.Sharable
class LoginRespHandler : SimpleChannelInboundHandler<LoginResp>(){

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: LoginResp?) {
        Timber.d(msg?.toString())
        msg?.takeIf { msg.success }?.apply {
            Event.sendEvent(msg)
        }
    }

}