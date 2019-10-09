package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class MessageResp : Packet() {
    val sender: String? = null
    val senderName: String? = null
    val message: String? = null

    override fun messageType(): Int {
        return MessageType.SEND_MESSAGE_RESP
    }



}
