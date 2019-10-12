package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class RegisterResp : Packet() {
    var success: Boolean = false
    var reason: String? = null

    override fun packetType(): Int {
        return PacketType.REGISTER_RESP
    }



}
