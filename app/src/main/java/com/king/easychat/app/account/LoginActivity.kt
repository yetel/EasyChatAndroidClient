package com.king.easychat.app.account

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import com.king.base.baseurlmanager.BaseUrlManagerActivity
import com.king.base.util.StringUtils
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

        if(StringUtils.isBlank(username)){
            username = Cache.getUsername()
        }

        etUsername.setText(username)

        if(StringUtils.isNotBlank(username)){
            etPassword.requestFocus()
        }

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

    override fun handleLoginResp(resp : LoginResp){
        hideLoading()
        if(resp.success){
            getApp().login(resp)
            mViewModel.loginReq?.token = resp.token
            Cache.put(mViewModel.loginReq,resp.token)
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

        if (!checkInput(etUsername, com.king.easychat.R.string.tips_username_is_empty)) {
            return
        }

        username = etUsername.text.toString()

        if (!checkInput(etPassword, com.king.easychat.R.string.tips_password_is_empty)) {
            return
        }

        val password = etPassword.text.toString()

        mViewModel.login(username!!,password)
    }

    private fun clickLogo(){
        if(Constants.isDomain){
            val intent = Intent(this, BaseUrlManagerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickRegister(){
        username = etUsername.text.toString().trim()
        val intent = Intent(context,RegisterActivity::class.java)
        intent.putExtra(Constants.KEY_USERNAME,username)
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.anim_in, R.anim.anim_out)
        startActivity(intent, optionsCompat.toBundle())
    }

    override fun onClick(v: View){
        when(v.id){
            R.id.ivLeft -> onBackPressed()
            R.id.ivLogo -> clickLogo()
            R.id.btnLogin -> clickLogin()
            R.id.tvForgotPwd -> clickForgotPwd()
            R.id.tvRegister -> clickRegister()
        }
    }

}