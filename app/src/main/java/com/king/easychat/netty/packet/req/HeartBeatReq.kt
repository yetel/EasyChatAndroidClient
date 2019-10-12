package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class HeartBeatReq : Packet(){
    override fun packetType(): Int {
        return PacketType.HEART_BEAT_REQ
    }

    override fun toString(): String {
        return "HeartBeatReq(packetType=PacketType.HEART_BEAT_REQ) ${super.toString()}"
    }


}