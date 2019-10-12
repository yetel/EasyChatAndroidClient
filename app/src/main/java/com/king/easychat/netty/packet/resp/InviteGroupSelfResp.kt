package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

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

    override fun packetType(): Int {
        return PacketType.INVITE_GROUP_SELF_RESP
    }



}
