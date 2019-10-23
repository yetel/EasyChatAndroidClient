package com.king.easychat.app.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Result
import com.king.easychat.bean.Search
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
class SearchViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application,model){

    val searchsLiveData = MutableLiveData<List<Search>>()

    fun search(keyword: String,curPage: Int,pageSize: Int){
        val token = getApplication<App>().getToken()
        updateStatus(StatusEvent.Status.LOADING)
        getRetrofitService(ApiService::class.java)
            .search(token,keyword,curPage,pageSize)
            .enqueue(object : ApiCallback<Result<List<Search>>>(){
                override fun onResponse(call: Call<Result<List<Search>>>?, result: Result<List<Search>>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            searchsLiveData.value = it.data
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

                override fun onError(call: Call<Result<List<Search>>>?, t: Throwable) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t.message)
                }

            })

    }
}