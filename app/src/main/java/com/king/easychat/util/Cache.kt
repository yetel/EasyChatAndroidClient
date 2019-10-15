package com.king.easychat.util

import android.content.Context
import com.king.base.util.SharedPreferencesUtils
import com.king.base.util.StringUtils
import com.king.easychat.app.Constants
import com.king.easychat.netty.packet.req.LoginReq

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object Cache {

    fun put(context: Context,data: LoginReq?){
        data?.let {
            SharedPreferencesUtils.put(context,Constants.KEY_USERNAME,it.userName)
            SharedPreferencesUtils.put(context,Constants.KEY_PASSWORD,it.password)
        }

    }

    fun getUsername(context: Context): String?{
        return SharedPreferencesUtils.getString(context,Constants.KEY_USERNAME)
    }

    fun getLoginReq(context: Context): LoginReq?{
        var loginReq : LoginReq? = null
        SharedPreferencesUtils.getSharedPreferences(context).run {
            var username: String? = getString(Constants.KEY_USERNAME,null)
            var password: String? = getString(Constants.KEY_PASSWORD,null)

            if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
                loginReq = LoginReq(username!!,password!!)
            }
        }

        return loginReq
    }
}