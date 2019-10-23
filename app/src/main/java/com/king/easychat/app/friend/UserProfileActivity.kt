package com.king.easychat.app.friend

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.UserProfileActivityBinding
import com.king.easychat.netty.NettyClient
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_profile_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UserProfileActivity : BaseActivity<UserProfileViewModel,UserProfileActivityBinding>(),View.OnClickListener{

    private lateinit var userId : String

    override fun initData(savedInstanceState: Bundle?) {

        tvTitle.text = intent.getStringExtra(Constants.KEY_TITLE)
        userId = intent.getStringExtra(Constants.KEY_ID)

        btnAdd.setOnClickListener(this)

        mViewModel.userLiveData.observe(this, Observer {
            it?.let {
                mBinding.data = it
                getApp().firends?.run {
                    if(contains(it)){
                        btnAdd.visibility = View.GONE
                    }
                }
            }
        })

        mViewModel.getUser(userId)
    }

    override fun getLayoutId(): Int {
        return R.layout.user_profile_activity
    }

    private fun clickAddFriend(){
        if(NettyClient.INSTANCE.isConnected()){
            mViewModel.addFriend(userId)
            showToast(R.string.success_operator)
            finish()
        }else{
            showToast(R.string.operator_failed)
        }

    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnAdd -> clickAddFriend()
        }
    }
}