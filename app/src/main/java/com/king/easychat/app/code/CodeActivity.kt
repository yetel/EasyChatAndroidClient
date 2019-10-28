package com.king.easychat.app.code

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.CodeActivityBinding
import kotlinx.android.synthetic.main.code_activity.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class CodeActivity : BaseActivity<CodeViewModel, CodeActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.qrcode)

        var userId = intent.getStringExtra(Constants.KEY_ID)
        var avatar = intent.getStringExtra(Constants.KEY_IMAGE_URL)
        val type = intent.getIntExtra(Constants.KEY_TYPE,Constants.USER_TYPE)
        mViewModel.updateQRCode(context,userId,avatar,type,ivCode)

        tvTips.setText(if(type == Constants.GROUP_TYPE) R.string.tips_group_qrcode else R.string.tips_user_qrcode )
    }

    override fun getLayoutId(): Int {
        return R.layout.code_activity
    }

}