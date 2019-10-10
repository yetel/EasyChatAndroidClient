package com.king.easychat.app.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.king.easychat.R
import com.king.easychat.app.account.LoginActivity
import com.king.easychat.app.account.LoginViewModel
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.SplashActivityBinding
import kotlinx.android.synthetic.main.splash_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class SplashActivity : BaseActivity<LoginViewModel, SplashActivityBinding>(){


    var isAnimEnd = false

    override fun initData(savedInstanceState: Bundle?) {
        startAnimation(rootView)
    }

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }


    fun startAnimation(view: View) {
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

    fun startActivity(){
        startLoginActivity()
        finish()
    }
}