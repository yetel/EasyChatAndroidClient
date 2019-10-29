package com.king.easychat.app.friend

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.king.easychat.App
import com.king.easychat.bean.User
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.bean.Resource
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendViewModel @Inject constructor(application: Application, model: FriendModel?) : BaseViewModel<FriendModel>(application, model){

    var friendsLiveData = MediatorLiveData<List<User>>()

    var source : LiveData<Resource<List<User>>>? = null

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
        val token = getApplication<App>().getToken()
        mModel.getFriends(token!!)
        source?.let {
            friendsLiveData.removeSource(it)
        }
        source = mModel.friendResource
        friendsLiveData.addSource(source!!) {
            updateStatus(it.status)
            if(it.isSuccess){
                friendsLiveData.postValue(it.data)
            }else {
                if(it.isFailure){
                    sendMessage(it.message)
                }
                friendsLiveData.addSource(mModel.getUsers()) {friendsLiveData::postValue}
            }

        }

    }


}