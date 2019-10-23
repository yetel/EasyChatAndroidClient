package com.king.easychat.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.api.ApiService
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.bean.Message
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeViewModel @Inject constructor(application: Application, model: HomeModel?) : MessageViewModel<HomeModel>(application, model){

    var lastMessageLiveData = MutableLiveData<List<Message>>()

    var userLiveData = MutableLiveData<User>()

    override fun onCreate() {
        super.onCreate()
    }



    fun retry(){
        queryMessageList(getApp().getUserId(),1,Constants.PAGE_SIZE)
    }




    /**
     * 查询最近聊天记录
     */
    fun queryMessageList(userId : String, currentPage : Int, pageSize : Int){
        GlobalScope.launch(Dispatchers.IO) {
            var list = mModel.queryMessageList(userId,pageSize)
            lastMessageLiveData.postValue(list)
        }
        sendSingleLiveEvent(Constants.REFRESH_SUCCESS)
    }

    /**
     * 获取好友列表
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
                            sendMessage(it.desc)
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
}