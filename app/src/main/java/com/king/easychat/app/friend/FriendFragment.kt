package com.king.easychat.app.friend

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.ChatActivity
import com.king.easychat.bean.User
import com.king.easychat.databinding.FriendFragmentBinding
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendFragment : BaseFragment<FriendViewModel,FriendFragmentBinding>(), View.OnClickListener{

    val mAdapter by lazy { BindingAdapter<User>(R.layout.rv_friend_item) }

    companion object{
        fun newInstance(): FriendFragment {
            return FriendFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_friend)
        ivRight.setOnClickListener(this)

        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel

        mViewModel.friendsLiveData.observe(this, Observer<MutableList<User>>{
            it?.let {
                mAdapter.replaceData(it)
            }
            srl.isRefreshing = false
        })

    }

    fun clickItem(data: User){
        val intent = Intent(context,ChatActivity::class.java)
        intent.putExtra(Constants.KEY_BEAN,data)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.friend_fragment
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.ivRight -> startActivity(ChatActivity::class.java)
        }
    }

}