package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

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

    override fun packetType(): Int {
        return PacketType.INVITE_GROUP_RESP
    }



}
