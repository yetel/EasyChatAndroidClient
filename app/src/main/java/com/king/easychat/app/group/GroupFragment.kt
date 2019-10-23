package com.king.easychat.app.group

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.GroupChatActivity
import com.king.easychat.bean.Group
import com.king.easychat.databinding.GroupFragmentBinding
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupFragment : BaseFragment<GroupViewModel,GroupFragmentBinding>(){

    val mAdapter by lazy { BindingAdapter<Group>(R.layout.rv_group_item) }

    companion object{
        fun newInstance(): GroupFragment{
            return GroupFragment()
        }


    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_group)

        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel

        mViewModel.groupsLiveData.observe(this, Observer<List<Group>>{
            it?.let {
                mAdapter.replaceData(it)
                getApp().groups = it
            }
            srl.isRefreshing = false
        })

    }

    fun clickItem(data: Group){
        val intent = newIntent(data.groupName,GroupChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.groupId)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.group_fragment
    }



}