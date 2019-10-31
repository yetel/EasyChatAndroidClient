package com.king.easychat.app.friend

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.AddUserReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UserProfileViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    val userLiveData = MutableLiveData<User>()

    /**
     * 获取好友信息
     */
    fun getUser(userId: String){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        mModel.getRetrofitService(ApiService::class.java)
            .getUser(token,userId)
            .enqueue(object : ApiCallback<Result<User>>(){
                override fun onResponse(call: Call<Result<User>>?, result: Result<User>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            userLiveData.value = it.data
                        }else{
                            sendMessage(it.desc)
                            updateStatus(StatusEvent.Status.FAILURE)
                        }

                    } ?: run{
                        updateStatus(StatusEvent.Status.FAILURE)
                        sendMessage(R.string.result_failure)
                    }
                }

                override fun onError(call: Call<Result<User>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }

    /**
     * 添加好友
     */
    fun addFriend(friendId: String){
        NettyClient.INSTANCE.sendMessage(AddUserReq(friendId))
    }
}