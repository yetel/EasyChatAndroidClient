package com.king.easychat.app.group

import androidx.lifecycle.MutableLiveData
import com.king.easychat.api.ApiService
import com.king.easychat.app.base.MessageModel
import com.king.easychat.bean.Group
import com.king.easychat.bean.Result
import com.king.frame.mvvmframe.bean.Resource
import com.king.frame.mvvmframe.data.IDataRepository
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupModel @Inject constructor(repository: IDataRepository?): MessageModel(repository){


    val groupResource = MutableLiveData<Resource<List<Group>>>()

    /**
     * 获取群组列表
     */
    fun getGroups(token: String){
        groupResource.value = Resource.loading()
        getRetrofitService(ApiService::class.java)
            .getGroups(token)
            .enqueue(object : ApiCallback<Result<List<Group>>>(){
                override fun onResponse(call: Call<Result<List<Group>>>?, result: Result<List<Group>>?) {
                    result?.let {
                        if(it.isSuccess()){
                            groupResource.value = Resource.success(it.data)
                            saveGroups(it.data)
                            return
                        }else{
                            groupResource.value = Resource.failure(result.desc)
                        }

                    } ?: run {
                        groupResource.value = Resource.failure(null)
                    }


                }

                override fun onError(call: Call<Result<List<Group>>>?, t: Throwable?) {
                    groupResource.value = Resource.error(t)
                }

            })

    }

    fun saveGroups(users: List<Group>?){
        users?.let {
            GlobalScope.launch(Dispatchers.IO) {
                with(getGroupDao()){
                    deleteAll()
                    insert(it)
                }
            }
        }
    }
}