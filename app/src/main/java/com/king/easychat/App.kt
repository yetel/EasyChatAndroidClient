package com.king.easychat

import android.content.Context
import com.king.easychat.app.Constants
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.di.component.DaggerApplicationComponent
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.frame.mvvmframe.base.BaseApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV
import timber.log.Timber
import java.io.File

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class App : BaseApplication() {

    var loginResp: LoginResp? = null

    var user: User? = null

    var firends: List<User>? = null
    var groups: List<Group>? = null

    var friendId: String? = null
    var groupId: String? = null

    override fun attachBaseContext(base: Context?) {
        //初始化打印日志
        var formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(5)
            .tag(Constants.TAG)
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (BuildConfig.DEBUG) {
                    Logger.log(priority, tag, message, t)
                }
            }
        })

        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)
        MMKV.initialize(getPath())
    }

    fun isLogin(): Boolean{
        return loginResp != null
    }

    fun getToken(): String{
        return loginResp?.token ?: ""
    }

    fun getUserId(): String{

        return loginResp?.userId ?: user?.userId ?: ""
    }

    fun getUsername(): String?{
        return loginResp?.userName ?:user?.userName ?: ""
    }

    fun getAvatar(): String?{
        return user?.avatar
    }

    fun getPath(): String{
        val path = getExternalFilesDir(Constants.DEFAULT_DIR)?.absolutePath
        val file = File(path)
        if(!file.exists()){
            file.mkdirs()
        }
        Timber.d("path:$path")
        return path!!
    }


    fun logout(){
        loginResp = null
        user = null
        stopHeartBeatService()
        NettyClient.INSTANCE.disconnect()

    }

    private fun stopHeartBeatService(){
        HeartBeatService.stopHeartBeatService(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        stopHeartBeatService()
        NettyClient.INSTANCE.disconnect()
    }

}