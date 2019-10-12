package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class AddUserSelfResp(val inviterId: String, val reason: String, val success: Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ADD_USER_SELF_RESP
    }

}
