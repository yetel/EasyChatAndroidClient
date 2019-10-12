package com.king.easychat.netty.packet.req

import android.os.Parcelable
import com.king.easychat.netty.MessageType
import com.king.easychat.netty.packet.Packet
import kotlinx.android.parcel.Parcelize

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Parcelize
data class LoginReq(val userName: String,val password: String) : Packet(), Parcelable {

    override fun messageType(): Int {
        return MessageType.LOGIN_REQ
    }

    override fun toString(): String {
        return "LoginReq(userName='$userName', password='$password')"
    }


}