package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.resp.AllowGroupResp
import io.netty.channel.ChannelHandler

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ChannelHandler.Sharable
class AllowGroupRespHandler : RespHandler<AllowGroupResp>()