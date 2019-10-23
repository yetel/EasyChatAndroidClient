package com.king.easychat.app.account

import android.app.Application
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import timber.log.Timber
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UpdatePwdViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    /**
     * 更新用户信息
     */
     fun updateUserPassword(oldPassword: String, newPassword: String){

        val app = getApplication<App>()
        val token = app.getToken()
        updateStatus(StatusEvent.Status.LOADING)
        getRetrofitService(ApiService::class.java)
            .updateUserPassword(token,oldPassword,newPassword)
            .enqueue(object: ApiCallback<Result<User>>(){
                override fun onResponse(call: Call<Result<User>>?, result: Result<User>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                        }else{
                            sendMessage(it.desc)
                            Timber.d(it.desc)
                            updateStatus(StatusEvent.Status.FAILURE)
                        }
                    } ?: run {
                        updateStatus(StatusEvent.Status.FAILURE)
                        sendMessage(R.string.result_failure)
                    }

                }

                override fun onError(call: Call<Result<User>>?, t: Throwable) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t.message)
                }

            })
    }

}