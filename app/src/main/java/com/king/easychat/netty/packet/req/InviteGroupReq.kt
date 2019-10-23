package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class InviteGroupReq(val groupId : String, val users : List<String>) : Packet(){
    override fun packetType(): Int {
        return PacketType.INVITE_GROUP_REQ
    }

    override fun toString(): String {
        return "InviteGroupReq(groupId='$groupId', users=$users)"
    }

}