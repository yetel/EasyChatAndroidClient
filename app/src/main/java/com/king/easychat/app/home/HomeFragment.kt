package com.king.easychat.app.home

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.MessageAdapter
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.ChatActivity
import com.king.easychat.app.chat.GroupChatActivity
import com.king.easychat.bean.Message
import com.king.easychat.bean.Operator
import com.king.easychat.databinding.HomeFragmentBinding
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.home_toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeFragment : BaseFragment<HomeViewModel,HomeFragmentBinding>(){

    val mAdapter by lazy { MessageAdapter(getApp().getUserId(),mViewModel) }

    var isRefresh = false

    var onTotalCountCallback : OnTotalCountCallback? = null


    interface OnTotalCountCallback{
        fun onTotalCountChanged(count: Int)
    }

    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnTotalCountCallback){
            onTotalCountCallback = context
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

        mViewModel.lastMessageLiveData.observe(this, Observer {
            mAdapter.curTime = System.currentTimeMillis()
            mAdapter.replaceData(it)
        })


        mViewModel.totalCountLiveData.observe(this, Observer {
            onTotalCountCallback?.onTotalCountChanged(it)
        })

        registerSingleLiveEvent {
            when(it.what){
                Constants.REFRESH_SUCCESS -> srl.isRefreshing = false
            }
        }
    }

    fun requestData(){
        if(isRefresh){
            mViewModel.delay(100)
        }

    }

    override fun onResume() {
        isRefresh = true
        super.onResume()
        requestData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isRefresh = !hidden
        requestData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Packet){
        when(event.packetType()){
            PacketType.SEND_MESSAGE_RESP -> requestData()
            PacketType.GROUP_MESSAGE_RESP -> requestData()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Operator){
        when(event.event){
            Constants.EVENT_REFRESH_MESSAGE_COUNT -> requestData()
        }
    }

    fun clickItem(data: Message){
        when(data.messageMode){
            Message.userMode -> startChatActivity(data)
            Message.groupMode -> startGroupChatActivity(data)
        }

    }

    fun startChatActivity(data: Message){
        val intent = newIntent(data.getShowName()!!,ChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.id)
        intent.putExtra(Constants.KEY_IMAGE_URL,data.avatar)
        startActivity(intent)
    }

    fun startGroupChatActivity(data: Message){
        val intent = newIntent(data.getShowName()!!,GroupChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.id)
        intent.putExtra(Constants.KEY_IMAGE_URL,data.avatar)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        isRefresh = false
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}