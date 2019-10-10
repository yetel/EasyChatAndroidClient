package com.king.easychat.netty.codec

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet
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
            .writeByte(packet.messageType())
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

        val messageType = byteBuf.readByte().toInt()
        val size = byteBuf.readInt()
        var bytes =  ByteArray(size)

        byteBuf.readBytes(bytes)

        val clazz = getPacketByMessageType(messageType)

        val packet = JsonUtil.fromJson(String(bytes),clazz)

        return packet
    }

    /**
     * 解码
     */
     fun <T : Packet> decode(json: String): T {
        val messageType = JsonUtil.getMessageType(json)
        val clazz = getPacketByMessageType(messageType)
        val packet  = JsonUtil.fromJson(json,clazz)
        packet as T
        return packet
    }


    /**
     * 通过消息类型获取数据包
     */
    fun getPacketByMessageType(messageType: Int): Class<out Packet> {

        when(messageType){
            MessageType.LOGIN_REQ -> return LoginReq::class.java
            MessageType.LOGIN_RESP -> return LoginResp::class.java
            MessageType.LOGOUT_REQ -> return LogoutReq::class.java
            MessageType.LOGOUT_RESP -> return LogoutResp::class.java
            MessageType.SEND_MESSAGE_REQ -> return MessageReq::class.java
            MessageType.SEND_MESSAGE_RESP -> return MessageResp::class.java
            MessageType.ADD_FRIEND_REQ -> return AddUserReq::class.java
            MessageType.ADD_FRIEND_RESP -> return AddUserResp::class.java
            MessageType.ADD_USER_SELF_RESP -> return AddUserSelfResp::class.java
            MessageType.CREATE_GROUP_REQ -> return CreateGroupReq::class.java
            MessageType.CREATE_GROUP_RESP -> return CreateGroupResp::class.java
            MessageType.INVITE_GROUP_REQ -> return InviteGroupReq::class.java
            MessageType.INVITE_GROUP_RESP -> return InviteGroupResp::class.java
            MessageType.INVITE_GROUP_SELF_RESP -> return InviteGroupSelfResp::class.java
            MessageType.GROUP_MESSAGE_REQ -> return GroupMessageReq::class.java
            MessageType.GROUP_MESSAGE_RESP -> return GroupMessageResp::class.java
            MessageType.ACCEPT_GROUP_REQ -> return AcceptGroupReq::class.java
            MessageType.ACCEPT_GROUP_RESP -> return AcceptGroupResp::class.java
            MessageType.ACCEPT_REQ -> return AcceptReq::class.java
            MessageType.ACCEPT_RESP -> return AcceptResp::class.java
            MessageType.REGISTER_REQ -> return RegisterReq::class.java
            MessageType.REGISTER_RESP -> return RegisterResp::class.java
            MessageType.UPDATE_PASSWD_REQ -> return UpdatePasswdReq::class.java
            MessageType.UPDATE_PASSWD_RESP -> return UpdatePasswdResp::class.java
            MessageType.MESSAGE_SELF_RESP -> return MessageSelfResp::class.java
            MessageType.HEART_BEAT_REQ -> return HeartBeatReq::class.java
            MessageType.HEART_BEAT_RESP -> return HeartBeatResp::class.java
        }
        return Packet::class.java
    }
}