package com.king.easychat.app.home

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.HomeActivityBinding

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>() {


    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
    }


}