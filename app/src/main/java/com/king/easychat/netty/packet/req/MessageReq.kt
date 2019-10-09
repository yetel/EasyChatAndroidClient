package com.king.easychat.netty.packet.req

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class MessageReq(val receiver : String, val message : String) : Packet(){
    override fun messageType(): Int {
        return MessageType.SEND_MESSAGE_REQ
    }

}