package com.king.easychat.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.databinding.LoginActivityBinding
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.util.Cache
import kotlinx.android.synthetic.main.login_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class LoginActivity : BaseActivity<LoginViewModel, LoginActivityBinding>(), View.OnClickListener{

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {

        etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                 etUsername.isSelected = !TextUtils.isEmpty(s)
            }

            override fun afterTextChanged(s: Editable) {

            }

        })

        username = intent.getStringExtra(Constants.KEY_USERNAME)
        username ?: Cache.getUsername(context)

        etUsername.setText(username)

        clickRightClear(etUsername)
        clickRightEye(etPassword)
        btnLogin.setOnClickListener(this)
        tvForgotPwd.setOnClickListener(this)
        tvRegister.setOnClickListener(this)

    }

    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: LoginResp){
        handleLoginResp(event)
    }

    fun handleLoginResp(resp : LoginResp){
        hideLoading()
        if(resp.success){
            getApp().loginResp = resp
            Cache.put(context,mViewModel.loginReq)
            HeartBeatService.startHeartBeatService(context)
            startHomeActivity()
            finish()
        }else{
            showToast(resp.reason)
        }
    }


    fun clickForgotPwd(){
        showTodo()
    }

    fun clickLogin(){

        if (!checkInput(etUsername, R.string.tips_username_is_empty)) {
            return
        }

        username = etUsername.text.toString()

        if (!checkInput(etPassword, R.string.tips_password_is_empty)) {
            return
        }

        val password = etPassword.text.toString()

        mViewModel.login(username!!,password)
    }

    fun clickRegister(){
        showTodo()
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.ivLeft -> onBackPressed()
            R.id.btnLogin -> clickLogin()
            R.id.tvForgotPwd -> clickForgotPwd()
            R.id.tvRegister -> clickRegister()
        }
    }

}