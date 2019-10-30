package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class AcceptResp(val inviterId: String, val invitedId: String,val invitedName: String, val success: Boolean) : Packet() {
    override fun packetType(): Int {
        return PacketType.ACCEPT_RESP
    }

    override fun toString(): String {
        return "AcceptResp(inviterId='$inviterId', invitedId='$invitedId', invitedName='$invitedName', success=$success)"
    }


}
