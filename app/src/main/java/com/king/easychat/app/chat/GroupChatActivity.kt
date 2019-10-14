package com.king.easychat.app.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.GroupChatAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.bean.Group
import com.king.easychat.databinding.GroupChatActivityBinding
import com.king.easychat.netty.packet.resp.GroupMessageResp
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatActivity : BaseActivity<GroupChatViewModel, GroupChatActivityBinding>(){

    var group : Group? = null
    var groupId : String = ""

    val mAdapter by lazy { GroupChatAdapter() }

    var message : String? = null

    var curPage = 1

    override fun initData(savedInstanceState: Bundle?) {

        srl.setColorSchemeResources(R.color.colorAccent)
        tvSend.visibility = View.GONE

        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                updateBtnStatus(s.isEmpty())
            }

            override fun afterTextChanged(s: Editable) {

            }

        })

        registerSingleLiveEvent {
            when(it.what){
                Constants.EVENT_SUCCESS -> handleMessageResp(mViewModel.messageReq?.toGroupMessageResp(getApp().loginResp,true))
                Constants.REFRESH_SUCCESS -> srl.isRefreshing = false
            }
        }

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter

        mViewModel.groupMessageLiveData.observe(this, Observer {
            if(curPage == 1){
                mAdapter.replaceData(it)
            }else if(it.size >= Constants.PAGE_SIZE){
                mAdapter.addData(0,it)
                curPage++
            }

        })

        group = intent.getParcelableExtra(Constants.KEY_BEAN)
        group?.let {
            tvTitle.text = it.groupName
            groupId = it.groupId
            mViewModel.queryMessageByGroupId(getApp().getUserId(),groupId,curPage,Constants.PAGE_SIZE)
        }

    }

    override fun hideLoading() {
        super.hideLoading()
        srl.isRefreshing = false
    }

    fun updateBtnStatus(isEmpty: Boolean){
        if(isEmpty){
            if(tvSend.visibility == View.VISIBLE){
                tvSend.visibility = View.GONE
                ivAdd.visibility = View.VISIBLE
                ivAdd.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_in))
                tvSend.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_out))
            }
        }else if(tvSend.visibility == View.GONE){
            tvSend.visibility = View.VISIBLE
            ivAdd.visibility = View.GONE
            ivAdd.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_out))
            tvSend.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_in))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.group_chat_activity
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: GroupMessageResp){
        handleMessageResp(event)
    }

    fun handleMessageResp(resp: GroupMessageResp?){
        resp?.let {
            mAdapter.addData(it)
            mViewModel.saveGroupMessage(getApp().getUserId(),resp)
        }

    }


    fun clickSend(){
        message = etContent.text.toString()
        message?.let {
            mViewModel.sendMessage(groupId,it,0)
        }

    }

    override fun onClick(v: View){
        super.onClick(v)
        when(v.id){
            R.id.tvSend -> clickSend()
        }
    }

}