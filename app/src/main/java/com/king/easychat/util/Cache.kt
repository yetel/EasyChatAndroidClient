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

    fun put(data: RegisterReq?,token: String){
        data?.let {
            MMKV.defaultMMKV().encode(Constants.KEY_USERNAME,it.userName)
        }
        putToken(token)

    }

    fun put(data: LoginReq?,token: String){
        data?.let {
            MMKV.defaultMMKV().encode(Constants.KEY_USERNAME,it.userName)
        }
        putToken(token)

    }

    fun put(key: String,value: String){
        MMKV.defaultMMKV().encode(key,value)

    }

    fun putToken(token : String?){
        token?.let {
            put(Constants.KEY_TOKEN,it)
        }

    }

    fun clearToken(){
        MMKV.defaultMMKV().reKey(Constants.KEY_TOKEN)
    }

    fun getUsername(): String?{
        return  MMKV.defaultMMKV().decodeString(Constants.KEY_USERNAME)
    }

    fun getToken(): String?{
        return  MMKV.defaultMMKV().decodeString(Constants.KEY_TOKEN)
    }

    fun getLoginReq(): LoginReq?{
        var loginReq : LoginReq? = null
        MMKV.defaultMMKV().run {
            var username: String? = decodeString(Constants.KEY_USERNAME)
            var token: String? = decodeString(Constants.KEY_TOKEN)

            if(StringUtils.isNotBlank(token)){
                loginReq = LoginReq(token,username,null)
            }
        }

        return loginReq
    }
}