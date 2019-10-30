package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ApplyGroupReq(val groupId: String) : Packet(){
    override fun packetType(): Int {
        return PacketType.APPLY_GROUP_REQ
    }

    override fun toString(): String {
        return "ApplyGroupReq(groupId='$groupId')"
    }

}