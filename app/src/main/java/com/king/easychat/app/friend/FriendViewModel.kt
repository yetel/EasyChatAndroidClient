package com.king.easychat.app.friend

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.api.ApiService
import com.king.easychat.bean.User
import com.king.easychat.bean.Result

import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendViewModel @Inject constructor(application: Application, model: FriendModel?) : BaseViewModel<FriendModel>(application, model){

    var friendsLiveData = MutableLiveData<MutableList<User>>()

    override fun onCreate() {
        super.onCreate()
        getFriends()
    }

    fun retry(){
        getFriends()
    }

    /**
     * 获取好友列表
     */
    fun getFriends(){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().loginResp?.token
        mModel.getRetrofitService(ApiService::class.java)
            .getFriends(token!!)
            .enqueue(object : ApiCallback<Result<MutableList<User>>>(){
                override fun onResponse(call: Call<Result<MutableList<User>>>?, result: Result<MutableList<User>>?) {
                    result?.let {
                        updateStatus(StatusEvent.Status.SUCCESS)
                        friendsLiveData.postValue(it.data)
                    } ?: updateStatus(StatusEvent.Status.FAILURE)

                }

                override fun onError(call: Call<Result<MutableList<User>>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }


}