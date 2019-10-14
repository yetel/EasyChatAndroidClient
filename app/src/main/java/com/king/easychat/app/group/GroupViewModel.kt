package com.king.easychat.app.group

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Result
import com.king.easychat.bean.Group
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
class GroupViewModel @Inject constructor(application: Application, model: GroupModel?) : BaseViewModel<GroupModel>(application, model){

    var groupsLiveData = MutableLiveData<MutableList<Group>>()

    override fun onCreate() {
        super.onCreate()
        getGroups()
    }

    fun retry(){
        getGroups()
    }


    /**
     * 获取好友列表
     */
    fun getGroups(){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().loginResp?.token
        mModel.getRetrofitService(ApiService::class.java)
            .getGroups(token!!)
            .enqueue(object : ApiCallback<Result<MutableList<Group>>>(){
                override fun onResponse(call: Call<Result<MutableList<Group>>>?, result: Result<MutableList<Group>>?) {
                    result?.let {
                        updateStatus(StatusEvent.Status.SUCCESS)
                        groupsLiveData.postValue(it.data)
                    } ?: updateStatus(StatusEvent.Status.FAILURE)
                }

                override fun onError(call: Call<Result<MutableList<Group>>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }
}