package com.king.easychat.app.photo

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.PhotoViewActivityBinding
import com.king.easychat.temp.TempViewModel
import com.king.easychat.util.SystemBarHelper
import com.king.frame.mvvmframe.base.DataViewModel
import kotlinx.android.synthetic.main.photo_view_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class PhotoViewActivity : BaseActivity<DataViewModel, PhotoViewActivityBinding>() {

    var size = 0

    companion object{
        const val IMAGE =  "image"
    }

    val mAdapter by lazy { BindingAdapter<String>(R.layout.vp_photo_item) }

    override fun initData(savedInstanceState: Bundle?) {
        ViewCompat.setTransitionName(vp, IMAGE)
        val list = intent.getStringArrayListExtra(Constants.KEY_LIST)
        list?.let {
            size = it.size
            if(it.size>1){
                tvPage.visibility = View.VISIBLE
            }else{
                tvPage.visibility = View.GONE
            }

        }
        vp.adapter = mAdapter
        vp.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updatePage(position+1,size)
            }
        })
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position -> onBackPressed() }
        mAdapter.replaceData(list)

    }


    override fun getLayoutId(): Int {
        return R.layout.photo_view_activity
    }

    private fun updatePage(position: Int,size: Int){
        tvPage.text = "$position/$size"
    }

}