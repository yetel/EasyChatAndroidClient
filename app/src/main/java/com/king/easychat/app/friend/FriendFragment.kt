package com.king.easychat.app.friend

import android.app.Activity
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
import com.king.easychat.app.search.SearchActivity
import com.king.easychat.bean.User
import com.king.easychat.databinding.FriendFragmentBinding
import com.king.frame.mvvmframe.base.livedata.StatusEvent
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
        ivLeft.setImageResource(R.drawable.btn_scan_selector)
        ivRight.setImageResource(R.drawable.btn_search_selector)
        ivLeft.setOnClickListener(this)
        ivRight.setOnClickListener(this)

        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel

        registerStatusEvent {
            if(it!=StatusEvent.Status.LOADING){
                srl.isRefreshing = false
                setEmpty()
            }
        }

        mViewModel.friendsLiveData.observe(this, Observer<List<User>>{
            it?.let {
                mAdapter.replaceData(it)
                getApp().friends = it
            }
        })

    }

    private fun setEmpty(){
        if(mAdapter.emptyView == null){
            mAdapter.setEmptyView(R.layout.layout_empty,rv)
        }
    }

    fun clickItem(data: User){
        val intent = newIntent(data.getShowName(),ChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.userId)
        intent.putExtra(Constants.KEY_IMAGE_URL,data.avatar)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.friend_fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQ_SEARCH -> mViewModel.retry()
            }
        }
    }

    private fun clickSearch(){
        startActivityForResult(SearchActivity::class.java,Constants.REQ_SEARCH)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ivLeft -> clickScan()
            R.id.ivRight -> clickSearch()
        }
    }

}