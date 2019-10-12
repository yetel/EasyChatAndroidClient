package com.king.easychat.app.chat

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.ChatAdapter
import com.king.easychat.app.adapter.GroupChatAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.bean.Group
import com.king.easychat.bean.User
import com.king.easychat.databinding.ChatActivityBinding
import com.king.easychat.databinding.GroupChatActivityBinding
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
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

    override fun initData(savedInstanceState: Bundle?) {

        group = intent.getParcelableExtra(Constants.KEY_BEAN)
        group?.let {
            tvTitle.setText(it.groupName)
            groupId = it.groupId
        }


        registerSingleLiveEvent {
            when(it.what){
                Constants.EVENT_SUCCESS -> handleMessageResp(mViewModel.messageReq?.toGroupMessageResp(getApp().loginResp,true))
            }
        }

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter
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