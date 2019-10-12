package com.king.easychat.app.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.FriendFragment
import com.king.easychat.app.group.GroupFragment
import com.king.easychat.app.me.MeFragment
import com.king.easychat.databinding.HomeActivityBinding
import com.king.easychat.netty.packet.Packet
import com.king.easychat.util.Event
import kotlinx.android.synthetic.main.home_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

        showHomeFragment()
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
        when(event.packetType()){

        }
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