package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ApplyGroupSelfResp(val groupId: String,var reason: String?,val success: Boolean) : Packet(){

    override fun packetType(): Int {
        return PacketType.APPLY_GROUP_SELF_RESP
    }

    override fun toString(): String {
        return "ApplyGroupSelfResp(groupId='$groupId', reason=$reason, success=$success)"
    }


}