package com.king.easychat.app.group

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.king.base.util.StringUtils
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.code.CodeActivity
import com.king.easychat.bean.Group
import com.king.easychat.databinding.GroupProfileActivityBinding
import com.king.easychat.netty.NettyClient
import kotlinx.android.synthetic.main.group_profile_activity.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupProfileActivity : BaseActivity<GroupProfileViewModel,GroupProfileActivityBinding>(),View.OnClickListener{

    private lateinit var groupId : String
    private var title: String? = null

    private var group : Group? = null

    override fun initData(savedInstanceState: Bundle?) {
        title = intent.getStringExtra(Constants.KEY_TITLE)
        title?.let {
            tvTitle.text = it
        }
        groupId = intent.getStringExtra(Constants.KEY_ID)

        ivAvatar.setOnClickListener(this)
        btnAdd.setOnClickListener(this)

        mViewModel.groupLiveData.observe(this, Observer {
            it?.let {
                mBinding.data = it
                group = it
                if(StringUtils.isBlank(title)){
                    title = it.groupName
                    tvTitle.text = title
                }
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

    private fun clickAvatar(){
        group?.avatar?.let {
            startPhotoViewActivity(it,ivAvatar)
        }
    }

    private fun clickJoinGroup(){
        if(NettyClient.INSTANCE.isConnected()){
            mViewModel.applyGroupReq(groupId)
            showToast(R.string.success_operator)
            setResult(Activity.RESULT_OK)
            finish()
        }else{
            showToast(R.string.operator_failed)
        }

    }

    private fun clickQRCode(){
        var intent = newIntent(CodeActivity::class.java)
        intent.putExtra(Constants.KEY_ID,groupId)
        intent.putExtra(Constants.KEY_TYPE,Constants.GROUP_TYPE)
        group?.avatar?.let {
            intent.putExtra(Constants.KEY_IMAGE_URL,it)
        }

        startActivity(intent)

    }

    private fun clickGroupMember(){
        var intent = newIntent(title ?: getString(R.string.group_member),GroupMemberActivity::class.java)
        intent.putExtra(Constants.KEY_ID,groupId)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.ivAvatar -> clickAvatar()
            R.id.btnAdd -> clickJoinGroup()
            R.id.tvLabelQRCode -> clickQRCode()
            R.id.tvLabelMember -> clickGroupMember()
        }
    }
}