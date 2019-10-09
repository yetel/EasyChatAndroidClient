package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class HertBeatReq : Packet(){
    override fun messageType(): Int {
        return MessageType.HEAT_BEAT_REQ
    }

}