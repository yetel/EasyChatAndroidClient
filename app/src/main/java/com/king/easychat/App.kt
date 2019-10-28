package com.king.easychat

import android.content.Context
import androidx.multidex.MultiDex
import com.king.easychat.app.Constants
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.di.component.DaggerApplicationComponent
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.util.Cache
import com.king.frame.mvvmframe.base.BaseApplication
import com.king.thread.nevercrash.NeverCrash
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV
import timber.log.Timber
import java.io.File

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class App : BaseApplication() {

    var loginResp: LoginResp? = null

    var user: User? = null

    var friends: List<User>? = null
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

        MultiDex.install(base)
        Beta.installTinker()

    }

    override fun onCreate() {
        super.onCreate()
        Bugly.init(this, Constants.BUGLY_APP_ID, BuildConfig.DEBUG)
        NeverCrash.init { t, e ->
            CrashReport.postCatchedException(e)
        }
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
        return loginResp?.token ?: Cache.getToken() ?: ""
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


    fun login(loginResp: LoginResp?){
        loginResp?.let {
            this.loginResp = it
            Cache.putToken(it.token)
        }

    }

    fun logout(context: Context){
        clear()
        Cache.clearToken()
        stopHeartBeatService(context)
        NettyClient.INSTANCE.disconnect()

    }

    private fun clear(){
        loginResp = null
        user = null
        friends = null
        groups = null
    }

    private fun stopHeartBeatService(context: Context){
        HeartBeatService.stopHeartBeatService(context)
    }

    override fun onTerminate() {
        super.onTerminate()
        stopHeartBeatService(this)
        NettyClient.INSTANCE.disconnect()
        clear()
    }

}