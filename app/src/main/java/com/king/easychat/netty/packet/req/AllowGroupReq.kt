package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AllowGroupReq(val groupId: String,val userId: String,val allow: Boolean) : Packet(){

    override fun packetType(): Int {
        return PacketType.ALLOW_GROUP_REQ
    }

    override fun toString(): String {
        return "AllowGroupReq(groupId='$groupId', userId='$userId', allow=$allow)"
    }


}