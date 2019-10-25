package com.king.easychat.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.king.easychat.netty.packet.resp.MessageResp
import kotlinx.android.parcel.Parcelize

/**
 * @author Zed
 * date: 2019/10/14.
 * description:
 */
@Entity(indices = [Index(value = ["sender"]), Index(value = ["receiver"])])
@Parcelize
open class MessageDbo(var userId : String, val sender : String?, val receiver: String?
                      ,val message : String, val send: Boolean = false
                      , val messageType : Int, var dateTime : String
                      ,val senderName : String?,var read: Boolean = false) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun toMessageResp(): MessageResp{
        var resp = MessageResp(sender,senderName,message,send,messageType)
        resp.dateTime = dateTime
        return resp
    }

    override fun toString(): String {
        return "MessageDbo(userId='$userId', sender=$sender, receiver=$receiver, message='$message', send=$send, messageType=$messageType, dateTime='$dateTime', senderName=$senderName, read=$read, id=$id)"
    }


}
