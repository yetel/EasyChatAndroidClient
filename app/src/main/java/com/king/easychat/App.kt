package com.king.easychat

import android.content.Context
import com.king.easychat.app.Constants
import com.king.easychat.bean.User
import com.king.easychat.di.component.DaggerApplicationComponent
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.frame.mvvmframe.base.BaseApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import timber.log.Timber
import java.io.File

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class App : BaseApplication() {

    var loginResp: LoginResp? = null

    var user: User? = null

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
    }

    fun isLogin(): Boolean{
        return loginResp != null
    }

    fun getToken(): String{
        return loginResp?.token ?: ""
    }

    fun getUserId(): String{

        return loginResp?.userId ?: ""
    }

    fun getUsername(): String ?{
        return loginResp?.userName
    }

    fun getAvatar(): String ?{
        return null
    }

    fun getPath(): String{
        val path = getExternalFilesDir("app_external_files_path")?.absolutePath + "/" +  Constants.DEFAULT_DIR
        val file = File(path)
        if(!file.exists()){
            file.mkdirs()
        }
        Timber.d("path:$path")
        return path
    }

}