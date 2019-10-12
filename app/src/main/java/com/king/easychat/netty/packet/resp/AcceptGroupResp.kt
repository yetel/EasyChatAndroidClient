package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class AcceptGroupResp(val acceptUser: String, val groupId: String, val success: Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ACCEPT_GROUP_RESP
    }

}
