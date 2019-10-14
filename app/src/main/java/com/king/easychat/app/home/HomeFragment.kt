package com.king.easychat.app.home

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.ChatActivity
import com.king.easychat.app.chat.GroupChatActivity
import com.king.easychat.bean.Message
import com.king.easychat.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeFragment : BaseFragment<HomeViewModel,HomeFragmentBinding>(){

    val mAdapter by lazy { BindingAdapter<Message>(R.layout.rv_message_item) }


    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_message)
        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel
    }

    fun clickItem(data: Message){
        when(data.messageMode){
            Message.userMode -> startChatActivity(data)
            Message.groupMode -> startGroupChatActivity(data)
        }

    }

    fun startChatActivity(data: Message){
        val intent = Intent(context, ChatActivity::class.java)
//        intent.putExtra(Constants.KEY_BEAN,data)
        startActivity(intent)
    }

    fun startGroupChatActivity(data: Message){
        val intent = Intent(context, GroupChatActivity::class.java)
//        intent.putExtra(Constants.KEY_BEAN,data)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}