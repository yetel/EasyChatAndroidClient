package com.king.easychat.app.group

import android.os.Bundle
import android.view.View
import android.view.WindowId
import androidx.lifecycle.Observer
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.GroupProfileActivityBinding
import com.king.easychat.netty.NettyClient
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_profile_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupProfileActivity : BaseActivity<GroupProfileViewModel,GroupProfileActivityBinding>(),View.OnClickListener{

    private lateinit var groupId : String

    override fun initData(savedInstanceState: Bundle?) {

        tvTitle.text = intent.getStringExtra(Constants.KEY_TITLE)
        groupId = intent.getStringExtra(Constants.KEY_ID)

        btnAdd.setOnClickListener(this)

        mViewModel.groupLiveData.observe(this, Observer {
            it?.let {
                mBinding.data = it
                getApp().groups?.run {
                    if(contains(it)){
                        btnAdd.visibility = View.GONE
                    }
                }
            }
        })

        mViewModel.getGroup(groupId)
    }

    override fun getLayoutId(): Int {
        return R.layout.group_profile_activity
    }

    private fun clickAddFriend(){
        if(NettyClient.INSTANCE.isConnected()){
            mViewModel.inviteGroup(groupId,getApp().getUserId())
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