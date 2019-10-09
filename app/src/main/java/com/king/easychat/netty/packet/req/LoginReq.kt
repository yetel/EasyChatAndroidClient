package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
 class LoginReq(val userName: String,val password: String) : Packet() {

    override fun messageType(): Int {
        return MessageType.LOGIN_REQ
    }

    override fun toString(): String {
        return "LoginReq(userName='$userName', password='$password')"
    }


}