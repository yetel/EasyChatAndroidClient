package com.king.easychat.app.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.FriendFragment
import com.king.easychat.app.group.GroupFragment
import com.king.easychat.app.me.MeFragment
import com.king.easychat.databinding.HomeActivityBinding
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp
import com.king.easychat.view.DragBubbleView
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.home_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>() ,
    HomeFragment.OnTotalCountCallback {

     var homeFragment : HomeFragment? = null

     var friendFragment : FriendFragment? = null

     var groupFragment : GroupFragment? = null

     var meFragment : MeFragment? = null

    override fun initData(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        showHomeFragment()
        registerStatusEvent {
            when(it){
                StatusEvent.Status.LOADING -> showLoading()
                StatusEvent.Status.SUCCESS -> hideLoading()
                StatusEvent.Status.FAILURE -> hideLoading()
                StatusEvent.Status.ERROR -> hideLoading()
            }
        }

        dbvCount.setOnBubbleStateListener(object : DragBubbleView.OnBubbleStateListener{
            override fun onDrag() {
            }

            override fun onMove() {
            }

            override fun onRestore() {
            }

            override fun onDismiss() {
                mViewModel.updateAllMessageRead(getApp().getUserId())
            }

        })

        mViewModel.getUser()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Int){
        Timber.d("----------刷新$event")
    }

    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    fun showHomeFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(homeFragment == null){
            homeFragment = HomeFragment.newInstance()
            fragmentTransaction.add(R.id.fragmentContent,homeFragment!!)
        }

        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(homeFragment!!)
        fragmentTransaction.commit()
    }

    fun showFriendFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(friendFragment == null){
            friendFragment = FriendFragment.newInstance()
            fragmentTransaction.add(R.id.fragmentContent,friendFragment!!)
        }
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(friendFragment!!)
        fragmentTransaction.commit()
    }

    fun showGroupFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(groupFragment == null){
            groupFragment = GroupFragment.newInstance()
            fragmentTransaction.add(R.id.fragmentContent,groupFragment!!)
        }
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(groupFragment!!)
        fragmentTransaction.commit()
    }

    fun showMeFragment(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if(meFragment == null){
            meFragment = MeFragment.newInstance()
            fragmentTransaction.add(R.id.fragmentContent,meFragment!!)
        }
        hideAllFragment(fragmentTransaction)
        fragmentTransaction.show(meFragment!!)
        fragmentTransaction.commit()
    }

    fun hideAllFragment(fragmentTransaction: FragmentTransaction){
        homeFragment?.let {
            fragmentTransaction.hide(it)
        }
        friendFragment?.let {
            fragmentTransaction.hide(it)
        }

        groupFragment?.let {
            fragmentTransaction.hide(it)
        }

        meFragment?.let {
            fragmentTransaction.hide(it)
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onMessageEvent(event: Packet){
        super.onMessageEvent(event)
        when(event.packetType()){
            PacketType.SEND_MESSAGE_RESP -> handleMessageResp(event as MessageResp)
            PacketType.GROUP_MESSAGE_RESP -> handleGroupMessageResp(event as GroupMessageResp)
        }
    }

    /**
     * 处理普通消息
     */
    private fun handleMessageResp(data: MessageResp){
        val read = getApp().firends != null
        mViewModel.saveMessage(getApp().getUserId(),data.sender!!,data.senderName,null,read,data)
    }

    /**
     * 处理群消息
     */
    private fun handleGroupMessageResp(data: GroupMessageResp){
        val read = getApp().groupId != null
        mViewModel.saveGroupMessage(getApp().getUserId(),data.groupId,null,read,data)
    }


    override fun onClick(v: View){
        when(v.id){
            R.id.rb1 -> showHomeFragment()
            R.id.rb2 -> showFriendFragment()
            R.id.rb3 -> showGroupFragment()
            R.id.rb4 -> showMeFragment()
        }
    }

    override fun onTotalCountChanged(count: Int){
        mBinding.data = count
    }

}
