package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class InviteGroupResp : Packet() {
    /** 群名 */
    val groupName: String? = null
    /** 群Id */
    val groupId: String? = null
    /** 邀请方id */
    val inviteId: String? = null

    override fun messageType(): Int {
        return MessageType.INVITE_GROUP_RESP
    }



}
