package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class GroupMessageResp: Packet() {
    /** 消息发送方 */
    val senderId: String? = null
    /** 消息发送人姓名 */
    val senderName: String? = null
    /** 消息接收方 */
    val groupId: String? = null
    /** 消息内容 */
    val message: String? = null
    /**
     * 消息类型
     */
    private val messageType = 0
    override fun messageType(): Int {
        return MessageType.GROUP_MESSAGE_RESP
    }

}
