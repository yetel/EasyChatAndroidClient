package com.king.easychat.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
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
class Group(val groupId: String,var groupName: String,var avatar: String?,var mainUserId: String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Ignore var memberNum: Int = 0

    fun getMemberNumber(): String{
        return memberNum.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        if (groupId != other.groupId) return false

        return true
    }

    override fun hashCode(): Int {
        return groupId.hashCode()
    }

    override fun toString(): String {
        return "Group(groupId='$groupId', groupName='$groupName', avatar=$avatar, mainUserId=$mainUserId, memberNum=$memberNum, id=$id)"
    }


}
