package com.king.easychat.app.account

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.RegisterActivityBinding

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterActivity : BaseActivity<LoginViewModel, RegisterActivityBinding>(){
    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.register_activity
    }

}