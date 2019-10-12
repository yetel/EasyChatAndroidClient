package com.king.easychat.netty.handle

import com.king.easychat.netty.packet.resp.AcceptGroupResp
import com.king.easychat.netty.packet.resp.AcceptResp
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import io.netty.channel.ChannelHandler

/**
 * @author Zed
 * date: 2019/10/12.
 * description:
 */
@ChannelHandler.Sharable
class AcceptRespHandler : RespHandler<AcceptResp>()