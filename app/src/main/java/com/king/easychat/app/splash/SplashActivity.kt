package com.king.easychat.app.splash

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.king.anetty.Netty
import com.king.base.util.StringUtils
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.databinding.SplashActivityBinding
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.req.LoginReq
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.util.Cache
import kotlinx.android.synthetic.main.splash_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SplashActivity : BaseActivity<SplashViewModel, SplashActivityBinding>(){

    var isRequest = false
    var isAnimEnd = false
    var loginResp: LoginResp? = null

    private val onSendMessageListener = Netty.OnSendMessageListener { msg, success ->
        if(!success){
            isRequest = true
            startActivity()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

        if(!NettyClient.INSTANCE.isConnected()){
            NettyClient.INSTANCE.setOnConnectListener(object: Netty.OnConnectListener{
                override fun onSuccess() {
                    autoLogin()
                }

                override fun onFailed() {
                    isRequest = true
                    startActivity()
                }

                override fun onError(e: Exception?) {
                    isRequest = true
                    startActivity()
                }

            })
            NettyClient.INSTANCE.setOnSendMessageListener(onSendMessageListener)
            NettyClient.INSTANCE.connect()
        }else{
            loginResp = getApp().loginResp
            isRequest = true
        }


        startAnimation(rootView)
    }

    /**
     * token自动登录
     */
    fun autoLogin(){
        var cacheToken = Cache.getToken()
        Timber.d("cacheToken=$cacheToken")
        if(StringUtils.isNotBlank(cacheToken)){
            var loginReq: LoginReq?  = Cache.getLoginReq()
            if(loginReq != null){
                mViewModel.login(loginReq)
                return
            }
        }

        isRequest = true
        startActivity()

    }

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun onDestroy() {
        NettyClient.INSTANCE.setOnSendMessageListener(null)
        super.onDestroy()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LoginResp){
        handleLoginResp(event)
    }

    override fun handleLoginResp(resp : LoginResp){
        isRequest = true
        if(resp.success){
            loginResp = resp
            getApp().login(resp)
        }

        startActivity()
    }


    private fun startAnimation(view: View) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.splash_in)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                isAnimEnd = true
                startActivity()
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        view.startAnimation(anim)
    }

    private fun startActivity(){
        if(isAnimEnd && isRequest){

            if(getApp().isLogin()){
                HeartBeatService.startHeartBeatService(context)
                startHomeActivity()
            }else{
                startLoginActivity(Cache.getUsername())
            }

            finish()
        }

    }


}