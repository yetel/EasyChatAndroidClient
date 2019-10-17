package com.king.easychat.netty.packet.resp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.king.easychat.bean.GroupMessageDbo
import com.king.easychat.bean.MessageDbo
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.util.AES
import kotlinx.android.parcel.Parcelize

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
@Entity(indices = [Index(value = ["groupId"])])
@Parcelize
class GroupMessageResp(val sender : String,val senderName : String?,val message : String,val groupId: String, val messageType : Int = 0, val isSender: Boolean = false) : Packet(), MultiItemEntity,
    Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object{
        val Left = 1
        val Right = 2
    }

    @Ignore
    override fun getItemType(): Int {
        return if(isSender) Right else Left
    }

//    @Expose val msg = AES.decrypt(message,dateTime + "ab").toString()

    override fun packetType(): Int {
        return PacketType.GROUP_MESSAGE_RESP
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


    fun toGroupMessageDbo(userId: String): GroupMessageDbo{
        val data = GroupMessageDbo(userId,groupId,sender,senderName,message,isSender,messageType,dateTime)
        data.dateTime = dateTime
        return data
    }

    fun isImage():Boolean{
        return messageType == MessageType.IMAGE
    }

}
