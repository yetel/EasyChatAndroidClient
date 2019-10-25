package com.king.easychat.app.me.user

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.api.ApiService
import com.king.easychat.bean.Result
import com.king.easychat.bean.User
import com.king.easychat.util.FileUtil
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import timber.log.Timber
import top.zibin.luban.Luban
import java.io.File
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChangeUserInfoViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    val userLiveData = MutableLiveData<User>()

    override fun onCreate() {
        super.onCreate()
    }


    fun updateNickname(nickname: String){
        updateUserInfo(nickname,null,null)
    }

    fun updateAvatar(file: File){
        updateUserInfo(null,file,null)
    }

    fun updateSignature(signature: String){
        updateUserInfo(null,null,signature)
    }


    /**
     * 更新用户信息
     */
    private fun updateUserInfo(nickname: String?,avatar: File?,signature: String?){

        val app = getApplication<App>()
        val token = app.getToken()
        showLoading()
        GlobalScope.launch(Dispatchers.Main){
            val imgBase64 = withContext(Dispatchers.IO){
                avatar?.run {
                    val filename = Luban.with(app)
                        .load(avatar)
                        .ignoreBy(80)
                        .setTargetDir(app.getPath())
                        .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                        .get(avatar?.absolutePath)
                    Timber.d("file:$filename")
                    FileUtil.imageToBase64(filename)
                }
            }
            val params = HashMap<String,String>()
            nickname?.let {
                params.put("nickName",it)
            }
            imgBase64?.let {
                params.put("avatar",it)
            }
            signature?.let {
                params.put("signature",it)
            }
            getRetrofitService(ApiService::class.java)
                .updateUserInfo(token,params)
                .enqueue(object: ApiCallback<Result<User>>(){
                    override fun onResponse(call: Call<Result<User>>?, result: Result<User>?) {
                        hideLoading()
                        result?.let {
                            if(it.isSuccess()){
                                userLiveData.value = it.data
                                app.user = it.data
                            }else{
                                sendMessage(it.desc)
                            }
                        } ?: run {
                            sendMessage(R.string.result_failure)
                        }

                    }

                    override fun onError(call: Call<Result<User>>?, t: Throwable) {
                        hideLoading()
                        sendMessage(t.message)
                    }

                })
        }
    }

    /**
     * 获取好友信息
     */
    fun updateFriendRemark(friendId: String,remark: String){
        updateStatus(StatusEvent.Status.LOADING)
        val token = getApplication<App>().getToken()
        mModel.getRetrofitService(ApiService::class.java)
            .updateFriendRemark(token,friendId,remark)
            .enqueue(object : ApiCallback<Result<User>>(){
                override fun onResponse(call: Call<Result<User>>?, result: Result<User>?) {
                    result?.let {
                        if(it.isSuccess()){
                            updateStatus(StatusEvent.Status.SUCCESS)
                            userLiveData.value = it.data
                        }else{
                            sendMessage(it.desc)
                            updateStatus(StatusEvent.Status.FAILURE)
                        }

                    } ?: run{
                        updateStatus(StatusEvent.Status.FAILURE)
                        sendMessage(R.string.result_failure)
                    }
                }

                override fun onError(call: Call<Result<User>>?, t: Throwable?) {
                    updateStatus(StatusEvent.Status.ERROR)
                    sendMessage(t?.message)
                }

            })

    }


}