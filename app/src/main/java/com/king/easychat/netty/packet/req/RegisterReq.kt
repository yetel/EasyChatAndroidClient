package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
 class RegisterReq(val userName: String, val password: String,val login: Boolean = true) : Packet() {

    override fun packetType(): Int {
        return PacketType.REGISTER_REQ
    }

    override fun toString(): String {
        return "RegisterReq(userName='$userName', password='$password', login=$login)"
    }


}