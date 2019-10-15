package com.king.easychat.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Entity(indices = [Index(value = ["groupId"], unique = true)])
@Parcelize
class Group(val groupId: String,var groupName: String,var mainUserId: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "Group(groupId=$groupId, groupName=$groupName, mainUserId=$mainUserId)"
    }


}
