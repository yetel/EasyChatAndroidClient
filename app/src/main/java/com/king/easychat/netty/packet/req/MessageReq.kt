package com.king.easychat.netty.packet.req

import com.google.gson.annotations.Expose
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class MessageReq(val receiver : String,@Expose val msg : String, val messageType : Int = 0) : Packet(){

    val message = AES.encrypt(msg,"${dateTime}ab").toString()

    override fun packetType(): Int {
        return PacketType.SEND_MESSAGE_REQ
    }

    fun toMessageResp(userId: String?,userName: String?,isSender: Boolean): MessageResp{
        val data = MessageResp(userId,userName,message,isSender,messageType)
        data.dateTime = dateTime
        return data
    }

    fun toMessageResp(loginResp: LoginResp?,isSender: Boolean): MessageResp{
        val data = MessageResp(loginResp?.userId,loginResp?.userName,message,isSender,messageType)
        data.dateTime = dateTime
        return data
    }


    override fun toString(): String {
        return "MessageReq(receiver='$receiver', msg='$msg', message='$message') ${super.toString()}"
    }
}