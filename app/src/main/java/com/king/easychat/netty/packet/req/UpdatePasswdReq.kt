package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
 class UpdatePasswdReq(val oldPassword: String, val newPassword: String) : Packet() {

    override fun messageType(): Int {
        return MessageType.UPDATE_PASSWD_REQ
    }


}