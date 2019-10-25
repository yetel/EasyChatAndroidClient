package com.king.easychat.netty.packet.resp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.king.easychat.bean.MessageDbo
import com.king.easychat.bean.RecentChat
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.util.AES
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
@Parcelize
class MessageResp(val sender : String?,val senderName : String?,val message : String, val isSender: Boolean = false, val messageType : Int) : Packet(), MultiItemEntity,
    Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object{
        const val Left = 1
        const val Right = 2
    }

    override fun getItemType(): Int {
        return if(isSender) Right else Left
    }

//    @Expose val msg = AES.decrypt(message,dateTime + "ab").toString()

    override fun packetType(): Int {
        return PacketType.SEND_MESSAGE_RESP
    }

    fun getMsg(): String?{
        return AES.decrypt(message,"${dateTime}ab")
    }

    override fun toString(): String {
        return "MessageResp(sender='$sender', senderName='$senderName', message='$message', msg='${getMsg()}') ${super.toString()}"
    }

    fun isSelf(self: String): Boolean{
        return self == sender
    }


    fun toMessageDbo(userId: String,friendId: String?,read: Boolean): MessageDbo{
        var receiver = if(isSender) friendId else sender
        var data = MessageDbo(userId,sender,receiver,message,isSender,messageType,dateTime,senderName,read)
        data.dateTime = dateTime
        return data
    }


    fun isImage():Boolean{
        return messageType == MessageType.IMAGE
    }

}
