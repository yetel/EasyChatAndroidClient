package com.king.easychat.app.group

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.king.easychat.App
import com.king.easychat.bean.Group
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.bean.Resource
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupViewModel @Inject constructor(application: Application, model: GroupModel?) : BaseViewModel<GroupModel>(application, model){

    var groupsLiveData = MediatorLiveData<List<Group>>()

    var source : LiveData<Resource<List<Group>>>? = null

    override fun onCreate() {
        super.onCreate()
        getGroups()
    }

    fun retry(){
        getGroups()
    }

    /**
     * 获取群组列表
     */
    fun getGroups(){
        val token = getApplication<App>().getToken()
        mModel.getGroups(token!!)
        source?.let {
            groupsLiveData.removeSource(it)
        }
        source = mModel.groupResource
        groupsLiveData.addSource(source!!){
            if(it.isSuccess){
                groupsLiveData.postValue(it.data)
            }else {
                if(it.isFailure){
                    sendMessage(it.message)
                }
                groupsLiveData.addSource(mModel.getGroups()){groupsLiveData::postValue}
            }
        }

    }

}