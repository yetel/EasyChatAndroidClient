package com.king.easychat.netty.packet.req

import com.google.gson.annotations.Expose
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/10/09.
 * description:
 */
class GroupMessageReq(val groupId : String, @Expose val msg : String, val messageType : Int = 0) : Packet(){

    val message = AES.encrypt(msg,"${dateTime}ab").toString()

    override fun packetType(): Int {
        return PacketType.GROUP_MESSAGE_REQ
    }

    fun toGroupMessageResp(userId: String,userName: String?, isSender: Boolean): GroupMessageResp {
        val data = GroupMessageResp(userId,userName,message,groupId,messageType,isSender)
        data.dateTime = dateTime
        return data
    }

    fun toGroupMessageResp(loginResp: LoginResp?, isSender: Boolean): GroupMessageResp {
        val data = GroupMessageResp(loginResp?.userId!!,loginResp?.userName,message,groupId,messageType,isSender)
        data.dateTime = dateTime
        return data
    }

    override fun toString(): String {
        return "GroupMessageReq(groupId='$groupId', msg='$msg', messageType=$messageType, message='$message')"
    }


}