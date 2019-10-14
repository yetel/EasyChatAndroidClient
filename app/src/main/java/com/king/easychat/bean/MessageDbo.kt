package com.king.easychat.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author Zed
 * date: 2019/10/14.
 * description:
 */
@Entity(indices = [Index(value = ["sender"]), Index(value = ["receiver"])])
@Parcelize
open class MessageDbo(val sender : String?, val receiver: String?
                      ,val message : String, val send: Boolean = false
                      , val messageType : Int, var dateTime : String?
                      ,val senderName : String?) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
