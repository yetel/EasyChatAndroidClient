package com.king.easychat.app.group

import android.app.Application
import androidx.lifecycle.MutableLiveData
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
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupMemberViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){


    val usersLiveData = MutableLiveData<List<User>>()
    lateinit var groupId : String

    fun retry(){
        getGroupMembers(groupId)
    }

    /**
     * 获取群成员信息
     */
    fun getGroupMembers(groupId: String){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        mModel.getRetrofitService(ApiService::class.java)
            .getGroupMembers(token,groupId)
            .enqueue(object : ApiCallback<Result<List<User>>>(){
                override fun onResponse(call: Call<Result<List<User>>>?, result: Result<List<User>>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            usersLiveData.value = it.data
                        }else{
                            sendMessage(it.desc)
                            updateStatus(StatusEvent.Status.FAILURE)
                        }

                    } ?: run{
                        updateStatus(StatusEvent.Status.FAILURE)
                        sendMessage(R.string.result_failure)
                    }
                }

                override fun onError(call: Call<Result<List<User>>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }
}