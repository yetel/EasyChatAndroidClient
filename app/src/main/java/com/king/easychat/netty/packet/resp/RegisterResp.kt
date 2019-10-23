package com.king.easychat.netty.packet.resp

import com.king.easychat.netty.packet.PacketType

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
class RegisterResp constructor(userId: String,userName: String,token: String,success: Boolean,reason: String = "") : LoginResp(userId,userName,token,success,reason) {


    override fun packetType(): Int {
        return PacketType.REGISTER_RESP
    }



}
