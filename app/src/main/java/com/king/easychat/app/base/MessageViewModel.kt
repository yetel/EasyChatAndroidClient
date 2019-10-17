package com.king.easychat.app.base

import android.app.Application
import android.text.TextUtils
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.bean.Result
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.req.GroupMessageReq
import com.king.easychat.netty.packet.req.MessageReq
import com.king.easychat.util.FileUtil
import com.king.frame.mvvmframe.base.BaseViewModel
import com.king.frame.mvvmframe.http.callback.ApiCallback
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.await
import timber.log.Timber
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class MessageViewModel<M :MessageModel> @Inject constructor(application: Application, model: M?) : BaseViewModel<M>(application, model){


    var messageReq : MessageReq? = null

    var groupMessageReq : GroupMessageReq? = null


    fun getApp(): App{
        return getApplication()
    }

    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : ApiCallback<T>() {
                override fun onResponse(call: Call<T>?, result: T) {
                    if (result != null) continuation.resume(result)
                    else continuation.resumeWithException(RuntimeException("result is null"))
                }

                override fun onError(call: Call<T>?, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

    /**
     * 协程上传图片
     */
    private suspend fun uploadImage(file: File): Result<String>?{
        var token = getApp().getToken()
        var filename = file.absolutePath
        val suffix = filename.substring(filename.lastIndexOf("."))
        return withContext(Dispatchers.IO){
            val filename = Luban.with(getApp())
                .load(file)
                .ignoreBy(80)
                .setTargetDir(getApp().getPath())
                .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .get(file.absolutePath)
            Timber.d("file:$filename")
            val imageBase64 = FileUtil.imageToBase64(filename)
            mModel.updateImage(token!!,imageBase64,suffix).await()
        }

//        Luban.with(getApp())
//            .load(file)
//            .ignoreBy(80)
//            .setTargetDir(getApp().getPath())
//            .filter { path -> !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
//            .setCompressListener(object : OnCompressListener {
//                override fun onStart() {
//                    Timber.d("Start compressing...")
//                }
//
//                override fun onSuccess(file: File) {
//                    Timber.d("file:" + file.absolutePath)
//                    GlobalScope.launch(Dispatchers.Main){
//                        val imageBase64 = withContext(Dispatchers.IO){
//                            FileUtil.imageToBase64(file)
//                            mModel.updateImage(token!!,imageBase64,suffix).execute().body()
//                        }
//
//                        mModel.updateImage(token!!,imageBase64,suffix).enqueue(object: ApiCallback<Result<String>>(){
//
//                            override fun onResponse(call: Call<Result<String>>?, result: Result<String>?) {
//                                result?.let {
//                                    if(it.isSuccess()){
//
//                                        try{
//                                            if(isGroup){
//                                                sendGroupMessage(receiver,it.data!!,MessageType.IMAGE)
//                                            }else{
//                                                sendGroupMessage(receiver,it.data!!,MessageType.IMAGE)
//                                            }
//                                        }catch (e: Exception){
//                                            Timber.e(e)
//                                        }
//
//                                    }else{
//                                        sendMessage(it.desc)
//                                    }
//                                }
//                            }
//
//                            override fun onError(call: Call<Result<String>>?, t: Throwable?) {
//                                sendMessage(t?.message)
//                            }
//
//                        })
//                    }
//
//                }
//
//                override fun onError(e: Throwable) {
//                    Timber.e(e)
//                }
//            }).launch()

    }

    private fun getUploadImageUrl(result: Result<String>?): String?{
        var url : String? = null
        result?.let { it ->
            if(it.isSuccess()){
                url = it.data
                Timber.d("url:$url")
            }else{
                sendMessage(it.desc)
            }
        } ?: run{
            sendMessage(R.string.result_failure)
        }

        return url
    }

    /**
     * 发送消息
     */
    fun sendMessage(receiver: String,message: String,messageType: Int){
        GlobalScope.launch(Dispatchers.Main){
            if(messageType == MessageType.IMAGE){
                var result = uploadImage(File(message))
                var url = getUploadImageUrl(result)
                url?.let {
                    messageReq = MessageReq(receiver,it,messageType)
                }

            }else{
                messageReq = MessageReq(receiver,message,messageType)
            }

            messageReq?.let {
                NettyClient.INSTANCE.sendMessage(it)

                if(NettyClient.INSTANCE.isConnected()){
                    sendSingleLiveEvent(Constants.EVENT_SUCCESS)
                }

            }

        }
    }


    /**
     * 发送群消息
     */
    fun sendGroupMessage(groupId: String,message: String,messageType: Int){
        GlobalScope.launch(Dispatchers.Main) {
            if (messageType == MessageType.IMAGE) {
                var result = uploadImage(File(message))
                var url = getUploadImageUrl(result)
                url?.let {
                    groupMessageReq = GroupMessageReq(groupId, url, messageType)
                }
            } else {
                groupMessageReq = GroupMessageReq(groupId, message, messageType)
            }

            groupMessageReq?.let {
                NettyClient.INSTANCE.sendMessage(it)

                if (NettyClient.INSTANCE.isConnected()) {
                    sendSingleLiveEvent(Constants.EVENT_SUCCESS)
                }

            }
        }

    }

}