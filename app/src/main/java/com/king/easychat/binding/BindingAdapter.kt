package com.king.easychat.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.king.base.util.TimeUtils
import com.king.easychat.R
import de.hdodenhof.circleimageview.CircleImageView

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */


@BindingAdapter(value = ["time"])
fun TextView.dateFormat(time: String?){
    time?.run {
        text = TimeUtils.formatDate(time,TimeUtils.FORMAT_Y_TO_M_EN)
    } ?: run {
        text = ""
    }

}

@BindingAdapter(value = ["imageUrl"])
fun ImageView.imageUrl(imageUrl: String?){
    imageUrl?.run {
        Glide.with(this@imageUrl).load(imageUrl).into(this@imageUrl)
    }

}

@BindingAdapter(value = ["avatar"])
fun ImageView.avatar(imageUrl: String?){
    imageUrl?.run {
        Glide.with(this@avatar).load(imageUrl).into(this@avatar).onLoadFailed(ContextCompat.getDrawable(this@avatar.context,R.drawable.default_avatar))
    }

}

@BindingAdapter(value = ["avatar"])
fun CircleImageView.avatar(avatar: String?){
    avatar?.run {
        Glide.with(this@avatar).load(avatar).into(this@avatar).onLoadFailed(ContextCompat.getDrawable(this@avatar.context,R.drawable.default_avatar))
    }

}