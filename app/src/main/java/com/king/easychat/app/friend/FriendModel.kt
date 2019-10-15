package com.king.easychat.app.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.king.easychat.api.ApiService
import com.king.easychat.app.base.MessageModel
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.frame.mvvmframe.bean.Resource
import com.king.frame.mvvmframe.data.IDataRepository
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.*
import retrofit2.Call
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendModel @Inject constructor(repository: IDataRepository?) : MessageModel(repository){

    val friendResource = MutableLiveData<Resource<List<User>>>()

    fun getFriends(token: String){
        friendResource.value = Resource.loading()
        getRetrofitService(ApiService::class.java)
            .getFriends(token)
            .enqueue(object : ApiCallback<Result<List<User>>>(){
                override fun onResponse(call: Call<Result<List<User>>>?, result: Result<List<User>>?) {
                    result?.let {
                        if(it.isSuccess()){
                            friendResource.value = Resource.success(it.data)
                            saveUsers(it.data)
                            return
                        }else{
                            friendResource.value = Resource.failure(result.desc)
                        }

                    } ?: run {
                        friendResource.value = Resource.failure(null)
                    }


                }

                override fun onError(call: Call<Result<List<User>>>?, t: Throwable?) {
                    friendResource.value = Resource.error(t)
                }

            })

    }

    fun saveUsers(users: List<User>?){
        users?.let {
            GlobalScope.launch(Dispatchers.IO) {
                with(getUserDao()){
                    deleteAll()
                    insert(it)
                }
            }
        }

    }

    fun getUsers(): LiveData<List<User>>{
        return getUserDao().getAllUsers()
    }

}