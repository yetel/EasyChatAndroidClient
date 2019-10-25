package com.king.easychat.netty.packet.req

import android.os.Parcelable
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import kotlinx.android.parcel.Parcelize

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Parcelize
data class LoginReq(val userName: String,val password: String, val sendCacheMessage: Boolean = false) : Packet(), Parcelable {


    override fun packetType(): Int {
        return PacketType.LOGIN_REQ
    }

    override fun toString(): String {
        return "LoginReq(userName='$userName', password='$password', sendCacheMessage=$sendCacheMessage)"
    }


}