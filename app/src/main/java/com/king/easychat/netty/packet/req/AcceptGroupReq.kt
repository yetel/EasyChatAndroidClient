package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class AcceptGroupReq(val groupId : String, val inviterId : String, val accept : Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ACCEPT_GROUP_REQ
    }

}