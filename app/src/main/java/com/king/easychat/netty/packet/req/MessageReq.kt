package com.king.easychat.netty.packet.req

import com.google.gson.annotations.Expose
import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class MessageReq(val receiver : String,@Expose val msg : String) : Packet(){

    val message = AES.encrypt(msg,dateTime + "ab").toString()

    override fun messageType(): Int {
        return MessageType.SEND_MESSAGE_REQ
    }

    override fun toString(): String {
        return "MessageReq(receiver='$receiver', msg='$msg', message='$message') ${super.toString()}"
    }

    fun toMessageResp(loginResp: LoginResp?,isSender: Boolean): MessageResp{
        return MessageResp(loginResp?.userId,loginResp?.userName,message,isSender)
    }

}