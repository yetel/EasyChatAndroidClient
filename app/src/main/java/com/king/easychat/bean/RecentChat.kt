package com.king.easychat.bean

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Entity(indices = [Index(value = ["chatId"], unique = true)])
class RecentChat(val userId: String,val chatId: String,var showName: String?,var avatar: String?,var dateTime: String){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "RecentChat(userId='$userId', chatId='$chatId', showName=$showName, avatar=$avatar, dateTime='$dateTime', id=$id)"
    }


}