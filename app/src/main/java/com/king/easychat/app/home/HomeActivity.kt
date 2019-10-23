package com.king.easychat.app.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.FriendFragment
import com.king.easychat.app.group.GroupFragment
import com.king.easychat.app.me.MeFragment
import com.king.easychat.databinding.HomeActivityBinding
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.AddUserResp
import com.king.easychat.netty.packet.resp.InviteGroupResp
import com.king.easychat.util.Event
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.home_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.security.acl.Group

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>() {

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
        mViewModel.getUser()

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
    fun onMessageEvent(event: Packet){
        Timber.d("event:${event.packetType()}")
        when(event.packetType()){
            PacketType.ADD_FRIEND_RESP -> handlerAddFriend(event as AddUserResp)
            PacketType.INVITE_GROUP_RESP -> handlerInviteGroup(event as InviteGroupResp)
        }
    }

    private fun handlerAddFriend(data: AddUserResp){
        var config = AppDialogConfig()
        with(config){
            content = "id=${data.inviterId}向您发送了好友申请？"
            ok = "同意"
            onClickOk =  View.OnClickListener {
                mViewModel.sendAcceptReq(data.inviterId,true)
                AppDialog.INSTANCE.dismissDialog()
            }

        }
        AppDialog.INSTANCE.showDialog(context,config)
    }

    private fun handlerInviteGroup(data: InviteGroupResp){
        var config = AppDialogConfig()
        with(config){
            content = "id=${data.inviteId}邀请您加入群[${data.groupName}]，是否同意？"
            ok = "同意"
            onClickOk =  View.OnClickListener {
                mViewModel.sendAcceptGroupReq(data.groupId!!,data.inviteId!!,true)
                AppDialog.INSTANCE.dismissDialog()
            }
        }
        AppDialog.INSTANCE.showDialog(context,config)
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.rb1 -> showHomeFragment()
            R.id.rb2 -> showFriendFragment()
            R.id.rb3 -> showGroupFragment()
            R.id.rb4 -> showMeFragment()
        }
    }

}