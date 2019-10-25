package com.king.easychat.app.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.base.util.StringUtils
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.UserProfileActivity
import com.king.easychat.app.group.GroupProfileActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.bean.Search
import com.king.easychat.bean.User
import com.king.easychat.databinding.SearchUserActivityBinding
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.easychat.util.SystemBarHelper
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.about_activity.*
import kotlinx.android.synthetic.main.search_user_activity.*
import kotlinx.android.synthetic.main.search_user_activity.toolbar
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SearchActivity : BaseActivity<SearchViewModel,SearchUserActivityBinding>(){

    val mAdapter by lazy { BindingAdapter<Search>(R.layout.rv_search_user_item)}

    var curPage = 1

    var keyword: String? = null

    override fun initData(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel


        srl.setColorSchemeResources(R.color.colorAccent)
        srl.setOnRefreshListener { search(keyword) }

        clickRightClear(etSearch)
        etSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etSearch.isSelected = !TextUtils.isEmpty(s)
            }

        })

        etSearch.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                search(etSearch.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener{adapter,v,position->
            clickItem(mAdapter.getItem(position)!!)
        }
        registerStatusEvent {
            when(it){
                StatusEvent.Status.LOADING -> if(!srl.isRefreshing){
                    showLoading()
                }
                StatusEvent.Status.SUCCESS -> hideLoading()
                StatusEvent.Status.FAILURE -> hideLoading()
                StatusEvent.Status.ERROR -> hideLoading()
            }


        }

        mViewModel.searchsLiveData.observe(this, Observer {
            it?.let {
                mAdapter.replaceData(it)
            }
        })

    }

    private fun clickItem(data: Search){
        when(data.type){
            Search.USER_TYPE -> clickUser(data)
            Search.GROUP_TYPE -> clickGroup(data)
        }
    }

    private fun clickUser(data: Search){
        if(data.id == getApp().getUserId()){
            startActivity(UserInfoActivity::class.java)
        }else{
            val intent = newIntent(UserProfileActivity::class.java)
            intent.putExtra(Constants.KEY_ID,data.id)
            intent.putExtra(Constants.KEY_TITLE,data.getShowName())
            startActivity(intent)
        }
    }

    private fun clickGroup(data: Search){
        val intent = newIntent(GroupProfileActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.id)
        intent.putExtra(Constants.KEY_TITLE,data.getShowName())
        startActivity(intent)
    }

    override fun hideLoading() {
        super.hideLoading()
        srl.isRefreshing = false
    }

    override fun getLayoutId(): Int {
        return R.layout.search_user_activity
    }

    private fun search(keyword: String?){
        this.keyword = keyword
        if(StringUtils.isNotBlank(keyword)){
            mViewModel.search(keyword!!,curPage,Constants.PAGE_SIZE)
        }else{
            showToast(R.string.tips_search_key_is_empty)
            srl.isRefreshing = false
        }
    }

}