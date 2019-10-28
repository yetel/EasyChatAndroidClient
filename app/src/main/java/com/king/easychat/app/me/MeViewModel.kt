package com.king.easychat.app.me

import android.app.Application
import android.content.ComponentName
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.BuildConfig
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.AppVersion
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MeViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    var userLiveData = MutableLiveData<User>()
    var versionLiveData = MutableLiveData<AppVersion>()


    override fun onCreate() {
        super.onCreate()
        getUser()
    }


    fun retry(){
        getUser()
    }

    /**
     * 获取用户信息
     */
    fun getUser(){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        val userId = getApplication<App>().getUserId()
        mModel.getRetrofitService(ApiService::class.java)
            .getUser(token,userId)
            .enqueue(object : ApiCallback<Result<User>>(){
                override fun onResponse(call: Call<Result<User>>?, result: Result<User>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            userLiveData.postValue(it.data)
                        }else{
                            updateStatus(StatusEvent.Status.FAILURE)
                        }

                    } ?: updateStatus(StatusEvent.Status.FAILURE)
                }

                override fun onError(call: Call<Result<User>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }


    /**
     * 版本检测
     */
    fun checkVersion(){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        val userId = getApplication<App>().getUserId()
        mModel.getRetrofitService(ApiService::class.java)
            .checkVersion(BuildConfig.VERSION_CODE.toString())
            .enqueue(object : ApiCallback<Result<AppVersion>>(){
                override fun onResponse(call: Call<Result<AppVersion>>?, result: Result<AppVersion>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            if(it.data!=null){
                                versionLiveData.postValue(it.data)
                            }else{
                                sendMessage(R.string.tips_latest_version)
                            }
                        }else{
                            updateStatus(StatusEvent.Status.FAILURE)
                            sendMessage(R.string.tips_latest_version)
                        }

                    } ?: updateStatus(StatusEvent.Status.FAILURE)
                }

                override fun onError(call: Call<Result<AppVersion>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }
}