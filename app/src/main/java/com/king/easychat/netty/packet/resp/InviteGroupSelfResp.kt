package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class InviteGroupSelfResp : Packet() {
    /** 发出邀请成功用户 */
    val successUser: List<String>? = null
    val failedUser: List<String>? = null
    /** 群名 */
    val groupName: String? = null
    /** 群Id */
    val groupId: String? = null

    override fun messageType(): Int {
        return MessageType.INVITE_GROUP_SELF_RESP
    }



}
