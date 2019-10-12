package com.king.easychat.netty.packet.req

import com.google.gson.annotations.Expose
import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class GroupMessageReq(val groupId : String, @Expose val msg : String, val messageType : Int) : Packet(){

    val message = AES.encrypt(msg,"${dateTime}ab").toString()

    override fun messageType(): Int {
        return MessageType.GROUP_MESSAGE_REQ
    }

    fun toGroupMessageResp(loginResp: LoginResp?, isSender: Boolean): GroupMessageResp {
        return GroupMessageResp(loginResp?.userId,loginResp?.userName,message,groupId,messageType,isSender)
    }

    override fun toString(): String {
        return "GroupMessageReq(groupId='$groupId', msg='$msg', messageType=$messageType, message='$message')"
    }


}