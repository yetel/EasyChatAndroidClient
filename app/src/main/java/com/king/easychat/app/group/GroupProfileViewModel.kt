package com.king.easychat.app.group

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Group
import com.king.easychat.bean.Result
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.InviteGroupReq
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupProfileViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    val groupLiveData = MutableLiveData<Group>()

    /**
     * 获取群信息
     */
    fun getGroup(groupId: String){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        mModel.getRetrofitService(ApiService::class.java)
            .getGroup(token,groupId)
            .enqueue(object : ApiCallback<Result<Group>>(){
                override fun onResponse(call: Call<Result<Group>>?, result: Result<Group>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            groupLiveData.value = it.data
                        }else{
                            sendMessage(it.desc)
                            updateStatus(StatusEvent.Status.FAILURE)
                        }

                    } ?: run{
                        updateStatus(StatusEvent.Status.FAILURE)
                        sendMessage(R.string.result_failure)
                    }
                }

                override fun onError(call: Call<Result<Group>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }

    fun inviteGroup(groupId: String,userId: String){
        val users = ArrayList<String>()
        users.add(userId)
        NettyClient.INSTANCE.sendMessage(InviteGroupReq(userId,users))
    }
}