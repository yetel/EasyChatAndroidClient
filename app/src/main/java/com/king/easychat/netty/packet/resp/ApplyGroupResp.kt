package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ApplyGroupResp(val applyUserId: String,val applyUserName: String,val groupId: String,val groupName: String) : Packet(){

    override fun packetType(): Int {
        return PacketType.APPLY_GROUP_RESP
    }

    override fun toString(): String {
        return "ApplyGroupResp(applyUserId='$applyUserId', applyUserName='$applyUserName', groupId='$groupId')"
    }

}