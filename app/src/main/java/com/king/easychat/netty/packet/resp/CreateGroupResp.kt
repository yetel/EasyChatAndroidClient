package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class CreateGroupResp(val groupName : String, val groupId: String, val reason: String, val success: Boolean) : Packet() {
    override fun messageType(): Int {
        return MessageType.CREATE_GROUP_RESP
    }

}
