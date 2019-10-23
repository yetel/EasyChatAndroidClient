package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.resp.HeartBeatResp
import com.king.easychat.netty.packet.resp.MessageResp
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ChannelHandler.Sharable
class HeartBeatRespHandler : RespHandler<HeartBeatResp>(){
//    override fun channelRead0(ctx: ChannelHandlerContext?, msg: HeartBeatResp) {
//        Timber.d(msg.toString())
//    }
}