package com.king.easychat.app.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.easychat.BR

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open class BindingAdapter<T> : BaseQuickAdapter<T, BindingHolder> {

    constructor(layoutResId: Int, @Nullable data: List<T>) : super(layoutResId, data)

    constructor(@Nullable data: List<T>) : super(data)

    constructor(layoutResId: Int) : super(layoutResId)

    override fun convert(helper: BindingHolder, item: T) {
        helper.mBinding?.let {
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }

}