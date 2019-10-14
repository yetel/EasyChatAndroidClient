package com.king.easychat.app.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.ChatAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.bean.User
import com.king.easychat.databinding.ChatActivityBinding
import com.king.easychat.netty.packet.resp.MessageResp
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatActivity : BaseActivity<ChatViewModel, ChatActivityBinding>(){

    var user : User? = null
    var friendId : String = ""

    val mAdapter by lazy { ChatAdapter() }

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
                Constants.EVENT_SUCCESS -> handleMessageResp(mViewModel.messageReq?.toMessageResp(getApp().loginResp,true))
                Constants.REFRESH_SUCCESS -> srl.isRefreshing = false
            }
        }

        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter

        mViewModel.messageLiveData.observe(this, Observer {
            if(curPage == 1){
                mAdapter.replaceData(it)
            }else if(it.size >= Constants.PAGE_SIZE){
                mAdapter.addData(0,it)
                curPage++
            }
        })

        user = intent.getParcelableExtra(Constants.KEY_BEAN)
        user?.let {
            tvTitle.text = it.getShowName()
            friendId = it.userId
            mViewModel.queryMessageByFriendId(getApp().getUserId(),friendId,curPage,Constants.PAGE_SIZE)

        }


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
        return R.layout.chat_activity
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageResp){
        handleMessageResp(event)
    }

    fun handleMessageResp(resp: MessageResp?){
        resp?.let {
            mAdapter.addData(it)
            mViewModel.saveMessage(getApp().getUserId(),friendId,resp)
        }

    }


    fun clickSend(){
        message = etContent.text.toString()
        message?.let {
            mViewModel.sendMessage(friendId,it)
        }

    }

    override fun onClick(v: View){
        super.onClick(v)
        when(v.id){
            R.id.tvSend -> clickSend()
        }
    }

}