package com.king.easychat.app.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.ChatAdapter
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.UserProfileActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.app.photo.PhotoViewActivity
import com.king.easychat.databinding.ChatActivityBinding
import com.king.easychat.netty.packet.MessageType
import com.king.easychat.netty.packet.resp.MessageResp
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatActivity : BaseActivity<ChatViewModel, ChatActivityBinding>(){

    var friendId : String = ""
    var showName : String? = null
    var avatar : String? = null

    val mAdapter by lazy { ChatAdapter(avatar,getApp().getAvatar()) }

    var message : String? = null

    var curPage = 1

    var isAutoScroll = true

    override fun initData(savedInstanceState: Bundle?) {
        tvSend.visibility = View.GONE
        srl.setColorSchemeResources(R.color.colorAccent)
        srl.setOnRefreshListener {
            isAutoScroll = false
            mViewModel.queryMessageByFriendId(getApp().getUserId(),friendId,curPage,Constants.PAGE_SIZE)
        }


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
                Constants.EVENT_SUCCESS -> {
                    etContent.text = null
                    handleMessageResp(mViewModel.messageReq?.toMessageResp(getApp().loginResp,true))
                }
                Constants.REFRESH_SUCCESS -> srl.isRefreshing = false
            }
        }

        avatar = intent.getStringExtra(Constants.KEY_IMAGE_URL)
        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL,R.drawable.line_drawable_xh_none))
        rv.adapter = mAdapter

        mAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.ivContent -> startPhotoViewActivity(mAdapter.getItem(position)?.getMsg()!!,view)
                    R.id.ivAvatar -> clickUser(mAdapter.getItem(position)!!)
                }
            }

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager = recyclerView.layoutManager
                if(layoutManager is LinearLayoutManager){
                    var lastPosition = layoutManager.findLastVisibleItemPosition()

                    if(lastPosition == layoutManager.itemCount - 1){
                        isAutoScroll = true
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        mViewModel.messageLiveData.observe(this, Observer {
            if(curPage == 1){
                mAdapter.curTime = System.currentTimeMillis()
                mAdapter.replaceData(it)
            }else if(curPage>1){
                mAdapter.addData(0,it)
            }

            if(mAdapter.itemCount >= (curPage-1) * Constants.PAGE_SIZE){
                curPage++
            }

            etContent.text = null
            if(isAutoScroll){
                rv.scrollToPosition(mAdapter.itemCount - 1)
            }
        })

        showName = intent.getStringExtra(Constants.KEY_TITLE)
        tvTitle.text = showName
        friendId = intent.getStringExtra(Constants.KEY_ID)
        getApp().friendId = friendId

        mViewModel.queryMessageByFriendId(getApp().getUserId(),friendId,curPage,Constants.PAGE_SIZE)

    }

    private fun clickUser(data: MessageResp){
        Timber.d("sender:${data.sender}--userId:${getApp().getUserId()}")
        if(data.sender == getApp().getUserId()){
            startActivity(UserInfoActivity::class.java)
        }else{
            val intent = newIntent(UserProfileActivity::class.java)
            intent.putExtra(Constants.KEY_ID,data.sender)
            intent.putExtra(Constants.KEY_TITLE,data.senderName)
            startActivity(intent)
        }
    }

    private fun startPhotoViewActivity(imgUrl: String,v: View){
        val intent = Intent(context,PhotoViewActivity::class.java)
        var list = ArrayList<String>()
        list.add(imgUrl)
        intent.putStringArrayListExtra(Constants.KEY_LIST,list)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,v,PhotoViewActivity.IMAGE)
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun updateBtnStatus(isEmpty: Boolean){
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
            if(it.isSender || friendId == it.sender){
                if(it.messageType >= MessageType.NORMAL){
                    when(it.messageType){
                        MessageType.HEART -> fl.addHeart()
                    }
                }else{
                    mAdapter.addData(it)
                    mViewModel.saveMessage(getApp().getUserId(),friendId,showName,avatar,true,resp)
                    if(isAutoScroll){
                        rv.scrollToPosition(mAdapter.itemCount - 1)
                    }
                }
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQ_SELECT_PHOTO -> handleSelectPhoto(data)
            }
        }
    }

    private fun handleSelectPhoto(data: Intent?){
        val result =  Matisse.obtainPathResult(data)
        mViewModel.sendMessage(friendId,result[0],MessageType.IMAGE)
    }


    private fun clickSmile(){
        fl.addHeart()
        mViewModel.sendHeart(friendId)
    }


    private fun clickAdd(){
        selectPhoto()
    }

    private fun clickSend(){
        message = etContent.text.toString()
        message?.let {
            mViewModel.sendMessage(friendId,it,MessageType.TEXT)
        }

    }

    override fun onBackPressed() {
        mViewModel.updateMessageRead(getApp().getUserId(),friendId)
        getApp().friendId = null
        super.onBackPressed()
    }

    override fun onClick(v: View){
        super.onClick(v)
        when(v.id){
            R.id.ivSmile -> clickSmile()
            R.id.ivAdd -> clickAdd()
            R.id.tvSend -> clickSend()
        }
    }

}