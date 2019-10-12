package com.king.easychat.netty.codec

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.req.*
import com.king.easychat.netty.packet.resp.*
import com.king.easychat.util.JsonUtil
import io.netty.buffer.ByteBuf

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

object PacketHelper {

    const val MAGIC_NUMBER = 0x12345678

    const val JSON = 1

    /**
     * 编码
     */
    @ExperimentalStdlibApi
    fun encode(byteBuf: ByteBuf, packet: Packet){
        val bytes = JsonUtil.toJsonBytes(packet)
        byteBuf.writeInt(MAGIC_NUMBER)//魔数
            .writeByte(packet.version)//版本
            .writeByte(JSON)//json
            .writeByte(packet.packetType())
            .writeInt(bytes.size)
            .writeBytes(bytes)

    }

    /**
     * 解码
     */
    fun decode(byteBuf: ByteBuf): Packet{
        byteBuf.skipBytes(4)//魔数
            .skipBytes(1)//版本
            .skipBytes(1)//json

        val packetType = byteBuf.readByte().toInt()
        val size = byteBuf.readInt()
        var bytes =  ByteArray(size)

        byteBuf.readBytes(bytes)

        val clazz = getPacketByPacketType(packetType)

        val packet = JsonUtil.fromJson(String(bytes),clazz)

        return packet
    }

    /**
     * 解码
     */
     fun <T : Packet> decode(json: String): T {
        val packetType = JsonUtil.getPacketType(json)
        val clazz = getPacketByPacketType(packetType)
        val packet  = JsonUtil.fromJson(json,clazz)
        packet as T
        return packet
    }


    /**
     * 通过消息类型获取数据包
     */
    fun getPacketByPacketType(packetType: Int): Class<out Packet> {

        when(packetType){
            PacketType.LOGIN_REQ -> return LoginReq::class.java
            PacketType.LOGIN_RESP -> return LoginResp::class.java
            PacketType.LOGOUT_REQ -> return LogoutReq::class.java
            PacketType.LOGOUT_RESP -> return LogoutResp::class.java
            PacketType.SEND_MESSAGE_REQ -> return MessageReq::class.java
            PacketType.SEND_MESSAGE_RESP -> return MessageResp::class.java
            PacketType.ADD_FRIEND_REQ -> return AddUserReq::class.java
            PacketType.ADD_FRIEND_RESP -> return AddUserResp::class.java
            PacketType.ADD_USER_SELF_RESP -> return AddUserSelfResp::class.java
            PacketType.CREATE_GROUP_REQ -> return CreateGroupReq::class.java
            PacketType.CREATE_GROUP_RESP -> return CreateGroupResp::class.java
            PacketType.INVITE_GROUP_REQ -> return InviteGroupReq::class.java
            PacketType.INVITE_GROUP_RESP -> return InviteGroupResp::class.java
            PacketType.INVITE_GROUP_SELF_RESP -> return InviteGroupSelfResp::class.java
            PacketType.GROUP_MESSAGE_REQ -> return GroupMessageReq::class.java
            PacketType.GROUP_MESSAGE_RESP -> return GroupMessageResp::class.java
            PacketType.ACCEPT_GROUP_REQ -> return AcceptGroupReq::class.java
            PacketType.ACCEPT_GROUP_RESP -> return AcceptGroupResp::class.java
            PacketType.ACCEPT_REQ -> return AcceptReq::class.java
            PacketType.ACCEPT_RESP -> return AcceptResp::class.java
            PacketType.REGISTER_REQ -> return RegisterReq::class.java
            PacketType.REGISTER_RESP -> return RegisterResp::class.java
            PacketType.UPDATE_PASSWD_REQ -> return UpdatePasswdReq::class.java
            PacketType.UPDATE_PASSWD_RESP -> return UpdatePasswdResp::class.java
            PacketType.MESSAGE_SELF_RESP -> return MessageSelfResp::class.java
            PacketType.HEART_BEAT_REQ -> return HeartBeatReq::class.java
            PacketType.HEART_BEAT_RESP -> return HeartBeatResp::class.java
        }
        return Packet::class.java
    }
}