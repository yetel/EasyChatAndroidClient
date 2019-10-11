package com.king.easychat.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.base.util.SharedPreferencesUtils
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.databinding.LoginActivityBinding
import com.king.easychat.netty.packet.resp.LoginResp
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class LoginActivity : BaseActivity<LoginViewModel, LoginActivityBinding>(), View.OnClickListener{

    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {

        mBinding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.etUsername.isSelected = !TextUtils.isEmpty(s)
            }

            override fun afterTextChanged(s: Editable) {

            }

        })

        username = intent.getStringExtra(Constants.KEY_USERNAME)
        username ?: SharedPreferencesUtils.getString(context,Constants.KEY_USERNAME)

        mBinding.etUsername.setText(username)

        clickRightClear(mBinding.etUsername)
        clickRightEye(mBinding.etPassword)
        mBinding.btnLogin.setOnClickListener(this)
        mBinding.tvForgotPwd.setOnClickListener(this)
        mBinding.tvRegister.setOnClickListener(this)

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

        if (!checkInput(mBinding.etUsername, R.string.tips_username_is_empty)) {
            return
        }

        username = mBinding.etUsername.text.toString()

        if (!checkInput(mBinding.etPassword, R.string.tips_password_is_empty)) {
            return
        }

        val password = mBinding.etPassword.text.toString()

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