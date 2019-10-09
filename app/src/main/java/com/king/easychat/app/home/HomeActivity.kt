package com.king.easychat.app.home

import android.os.Bundle
import androidx.annotation.MainThread
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.HomeActivityBinding
import com.king.easychat.netty.packet.LoginResp
import com.king.easychat.netty.packet.Packet
import com.king.easychat.util.Event
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeActivity : BaseActivity<HomeViewModel, HomeActivityBinding>() {


    override fun initData(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        Event.registerEvent(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_activity
    }

    override fun onDestroy() {
        Event.unregisterEvent(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Packet){
        when(event.messageType()){

        }
    }

}