package com.king.easychat.app.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class BindingHolder(view: View) : BaseViewHolder(view) {

    var mBinding: ViewDataBinding? = null
    init {
        try {
            mBinding = DataBindingUtil.bind(view)!!
        } catch (e: Exception) {

        }

    }
}