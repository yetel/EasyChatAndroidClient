package com.king.easychat.util

import com.king.base.util.StringUtils
import com.king.easychat.app.Constants
import com.king.easychat.netty.packet.req.LoginReq
import com.king.easychat.netty.packet.req.RegisterReq
import com.tencent.mmkv.MMKV

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object Cache {

    fun put(data: RegisterReq?){
        data?.let {
            MMKV.defaultMMKV().encode(Constants.KEY_USERNAME,it.userName)
            MMKV.defaultMMKV().encode(Constants.KEY_PASSWORD,it.password)
        }

    }

    fun put(data: LoginReq?){
        data?.let {
            MMKV.defaultMMKV().encode(Constants.KEY_USERNAME,it.userName)
            MMKV.defaultMMKV().encode(Constants.KEY_PASSWORD,it.password)
        }

    }

    fun getUsername(): String?{
        return  MMKV.defaultMMKV().decodeString(Constants.KEY_USERNAME)
    }

    fun getLoginReq(): LoginReq?{
        var loginReq : LoginReq? = null
        MMKV.defaultMMKV().run {
            var username: String? = decodeString(Constants.KEY_USERNAME)
            var password: String? = decodeString(Constants.KEY_PASSWORD)

            if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
                loginReq = LoginReq(username!!,password!!)
            }
        }

        return loginReq
    }
}