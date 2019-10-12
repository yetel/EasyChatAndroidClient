package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class AddUserReq(val addUserId : String) : Packet(){
    override fun packetType(): Int {
        return PacketType.ADD_FRIEND_REQ
    }

}