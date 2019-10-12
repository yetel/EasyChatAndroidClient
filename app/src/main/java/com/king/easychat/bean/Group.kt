package com.king.easychat.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Parcelize
class Group(val groupId: String,var groupName: String,var mainUserId: String?) : Parcelable {

    override fun toString(): String {
        return "Group(groupId=$groupId, groupName=$groupName, mainUserId=$mainUserId)"
    }


}
