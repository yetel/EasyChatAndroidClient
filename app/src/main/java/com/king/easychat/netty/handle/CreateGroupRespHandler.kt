package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.resp.*
import io.netty.channel.ChannelHandler

/**
 * @author Zed
 * date: 2019/10/12.
 * description:
 */
@ChannelHandler.Sharable
class CreateGroupRespHandler : RespHandler<CreateGroupResp>()