package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class HeartBeatReq : Packet(){
    override fun messageType(): Int {
        return MessageType.HEART_BEAT_REQ
    }

    override fun toString(): String {
        return "HeartBeatReq(messageType=MessageType.HEART_BEAT_REQ) ${super.toString()}"
    }


}