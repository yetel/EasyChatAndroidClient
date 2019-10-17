package com.king.easychat.app.photo

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.PhotoViewActivityBinding
import com.king.easychat.temp.TempViewModel
import kotlinx.android.synthetic.main.photo_view_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class PhotoViewActivity : BaseActivity<TempViewModel, PhotoViewActivityBinding>() {

    val mAdapter by lazy { BindingAdapter<String>(R.layout.vp_photo_item) }

    override fun initData(savedInstanceState: Bundle?) {
        val list = intent.getStringArrayListExtra(Constants.KEY_LIST)
        vp.adapter = mAdapter
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position -> onBackPressed() }
        mAdapter.replaceData(list)
    }

    override fun getLayoutId(): Int {
        return R.layout.photo_view_activity
    }

}