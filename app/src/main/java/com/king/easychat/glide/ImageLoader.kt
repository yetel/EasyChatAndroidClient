package com.king.easychat.glide

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object ImageLoader {


    fun displayImage(iv: ImageView,url: String?,defaultImage: Int){
        url?.let {
            GlideApp.with(iv).load(url).error(defaultImage).into(iv)
        } ?: run {
            iv.setImageResource(defaultImage)
        }
    }

    fun displayImage(context: Context,iv: ImageView,url: String?,defaultImage: Int){
        url?.let {
            GlideApp.with(context).load(url).error(defaultImage).into(iv)
        } ?: run {
            iv.setImageResource(defaultImage)
        }
    }

    fun displayImage(fragment: Fragment,iv: ImageView,url: String?,defaultImage: Int){
        url?.let {
            GlideApp.with(fragment).load(url).error(defaultImage).into(iv)
        } ?: run {
            iv.setImageResource(defaultImage)
        }
    }

    fun displayImage(activity: Activity,iv: ImageView,url: String?,defaultImage: Int){
        url?.let {
            GlideApp.with(activity).load(url).error(defaultImage).into(iv)
        } ?: run {
            iv.setImageResource(defaultImage)
        }
    }

}