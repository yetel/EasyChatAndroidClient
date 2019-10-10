package com.king.easychat.app.chat

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.ChatActivityBinding
import com.king.easychat.netty.packet.resp.MessageResp
import kotlinx.android.synthetic.main.chat_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatActivity : BaseActivity<ChatViewModel, ChatActivityBinding>(),View.OnClickListener{

    lateinit var mAdapter : BindingAdapter<MessageResp>

    var message : String? = null

    override fun initData(savedInstanceState: Bundle?) {

        registerSingleLiveEvent {
            when(it.what){
                Constants.EVENT_SUCCESS -> handleMessageResp(mViewModel.messageReq?.toMessageResp("1","jenly"))
            }
        }

        tvSend.setOnClickListener(this)
        mAdapter = BindingAdapter(R.layout.rv_chat_item)

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter
    }

    override fun getLayoutId(): Int {
        return R.layout.chat_activity
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageResp){
        handleMessageResp(event)
    }

    fun handleMessageResp(resp: MessageResp?){
        resp?.let {
            mAdapter.addData(it)
        }

    }


    fun clickSend(){

        message = etContent.text.toString()
        message?.let {
            mViewModel.sendMessage("7",it)
        }

    }

    override fun onClick(v: View){
        when(v.id){
            R.id.tvSend -> clickSend()
        }
    }

}