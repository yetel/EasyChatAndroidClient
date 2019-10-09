package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class MessageSelfResp : Packet() {
    var receiverId: String? = null
    var groupId: String? = null
    var success: Boolean = false
    var reason: String? = null

    override fun messageType(): Int {
        return MessageType.MESSAGE_SELF_RESP
    }



}
