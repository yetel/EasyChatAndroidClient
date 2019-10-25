package com.king.easychat.netty.packet.req

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SyncMessageReq : Packet(){

    override fun packetType(): Int {
        return PacketType.SYNC_MESSAGE_REQ
    }

    override fun toString(): String {
        return "SyncMessageReq(PacketType.SYNC_MESSAGE_REQ)"
    }

}