package com.king.easychat.config

import android.content.Context
import com.king.base.baseurlmanager.BaseUrlManager
import com.king.easychat.app.Constants
import com.king.frame.mvvmframe.config.FrameConfigModule
import com.king.frame.mvvmframe.di.module.ConfigModule

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ConfigModule : FrameConfigModule() {
    override fun applyOptions(context: Context, builder: ConfigModule.Builder?) {
        if(Constants.isDomain){
            val manager = BaseUrlManager(context)
            builder?.baseUrl(Constants.getBaseUrl(manager.baseUrl))
        }else{
            builder?.baseUrl(Constants.BASE_URL)
        }

    }

}