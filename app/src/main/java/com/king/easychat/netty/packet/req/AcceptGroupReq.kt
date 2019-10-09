package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class AcceptGroupReq(val groupId : String, val inviterId : String, val accept : Boolean) : Packet() {
    override fun messageType(): Int {
        return MessageType.ACCEPT_GROUP_REQ
    }

}