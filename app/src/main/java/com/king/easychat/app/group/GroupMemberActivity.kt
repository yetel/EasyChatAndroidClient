package com.king.easychat.app.group

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.UserProfileActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.bean.User
import com.king.easychat.databinding.GroupMemberActivityBinding
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupMemberActivity : BaseActivity<GroupMemberViewModel, GroupMemberActivityBinding>(){

    val mAdapter by lazy { BindingAdapter<User>(R.layout.rv_user_item) }


    override fun initData(savedInstanceState: Bundle?) {

        tvTitle.text = intent.getStringExtra(Constants.KEY_TITLE)

        mViewModel.groupId = intent.getStringExtra(Constants.KEY_ID)
        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel

        registerStatusEvent {
            if(it!= StatusEvent.Status.LOADING){
                srl.isRefreshing = false
                setEmpty()
            }
        }

        mViewModel.usersLiveData.observe(this, Observer<List<User>>{
            it?.let {
                mAdapter.replaceData(it)
                getApp().friends = it
            }
        })

        mViewModel.retry()
    }

    private fun setEmpty(){
        if(mAdapter.emptyView == null){
            mAdapter.setEmptyView(R.layout.layout_empty,rv)
        }
    }

    fun clickItem(data: User){
        if(data.userId == getApp().getUserId()){
            startActivity(UserInfoActivity::class.java)
        }else{
            val intent = newIntent(UserProfileActivity::class.java)
            intent.putExtra(Constants.KEY_ID,data.userId)
            intent.putExtra(Constants.KEY_TITLE,data.getShowName())
            startActivity(intent)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.group_member_activity
    }

}