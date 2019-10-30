package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AllowGroupResp(val groupId: String,val allow: Boolean) : Packet(){

    override fun packetType(): Int {
        return PacketType.ALLOW_GROUP_RESP
    }

    override fun toString(): String {
        return "AllowGroupResp(groupId='$groupId', allow=$allow)"
    }


}
