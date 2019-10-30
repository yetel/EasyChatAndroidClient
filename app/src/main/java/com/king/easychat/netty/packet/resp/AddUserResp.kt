package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class AddUserResp(val inviterId: String,val inviterName: String) : Packet() {
    override fun packetType(): Int {
        return PacketType.ADD_FRIEND_RESP
    }

    override fun toString(): String {
        return "AddUserResp(inviterId='$inviterId', inviterName='$inviterName')"
    }


}
