package com.king.easychat.app.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.app.Constants
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.bean.Message
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.base.DataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeViewModel @Inject constructor(application: Application, model: HomeModel?) : MessageViewModel<HomeModel>(application, model){

    var lastMessageLiveData = MutableLiveData<List<Message>>()

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
}