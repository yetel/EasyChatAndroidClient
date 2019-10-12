package com.king.easychat.netty.packet.resp

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class GroupMessageResp(val sender : String?,val senderName : String?,val message : String,val groupId: String, val messageType : Int, val isSender: Boolean = false) : Packet(), MultiItemEntity {


    companion object{
        val Left = 1
        val Right = 2
    }

    override fun getItemType(): Int {
        return if(isSender) Right else Left
    }

//    @Expose val msg = AES.decrypt(message,dateTime + "ab").toString()

    override fun messageType(): Int {
        return MessageType.GROUP_MESSAGE_RESP
    }

    fun getMsg(): String?{
        return AES.decrypt(message,"${dateTime}ab")
    }

    override fun toString(): String {
        return "GroupMessageResp(sender='$sender', senderName='$senderName', message='$message', msg='${getMsg()}', groupId='$groupId') ${super.toString()}"
    }

    fun isSelf(self: String): Boolean{
        return self == sender
    }

}
