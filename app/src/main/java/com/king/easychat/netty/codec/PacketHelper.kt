package com.king.easychat.netty.codec

import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.LoginReq
import com.king.easychat.netty.packet.LoginResp
import com.king.easychat.netty.packet.Packet
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

        }
        return Packet::class.java
    }
}