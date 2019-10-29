package com.king.easychat.app.code

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.manager.RequestManagerRetriever
import com.bumptech.glide.request.RequestOptions
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.glide.GlideApp
import com.king.easychat.util.BitmapUtil
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.zxing.util.CodeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class CodeViewModel @Inject constructor(application: Application, model: BaseModel?) : DataViewModel(application, model){

    fun updateQRCode(context: Context,id: String,avatar: String?,type: Int,iv: ImageView){
        GlobalScope.launch(Dispatchers.Main) {
            var codeContent = ""
            if(type == Constants.GROUP_TYPE){
                codeContent = Constants.GROUP_CODE_PREFIX + id
            } else{
                codeContent = Constants.USER_CODE_PREFIX + id
            }
            val code = withContext(Dispatchers.IO){
                var logo : Bitmap? = null
                if(avatar != null){
                    var bmp: Bitmap? = null
                    var file = GlideApp.with(context).load(avatar).downloadOnly(100,100).get()
                    if(file != null){
                        bmp = BitmapFactory.decodeFile(file.absolutePath)
                    }else{
                        bmp = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
                    }

                    logo = BitmapUtil.getOvalBitmap(bmp)
                }

                CodeUtils.createQRCode(codeContent,400,logo)
            }

            GlideApp.with(context).load(code).into(iv)
        }
    }
}