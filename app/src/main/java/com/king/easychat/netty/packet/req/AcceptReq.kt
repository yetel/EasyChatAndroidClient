package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class AcceptReq(val receiver : String, val accept : Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ACCEPT_REQ
    }

    override fun toString(): String {
        return "AcceptReq(receiver='$receiver', accept=$accept)"
    }

}