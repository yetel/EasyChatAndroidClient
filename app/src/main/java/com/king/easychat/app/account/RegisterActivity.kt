package com.king.easychat.app.account

import android.os.Bundle
import android.view.View
import com.king.base.util.StringUtils
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.service.HeartBeatService
import com.king.easychat.databinding.RegisterActivityBinding
import com.king.easychat.netty.packet.resp.RegisterResp
import com.king.easychat.util.Cache
import com.king.easychat.util.CheckUtil
import kotlinx.android.synthetic.main.login_activity.etPassword
import kotlinx.android.synthetic.main.login_activity.etUsername
import kotlinx.android.synthetic.main.register_activity.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class RegisterActivity : BaseActivity<RegisterViewModel, RegisterActivityBinding>(), View.OnClickListener{
    var username : String? = null

    override fun initData(savedInstanceState: Bundle?) {

        username = intent.getStringExtra(Constants.KEY_USERNAME)

        etUsername.setText(username)

        if(StringUtils.isNotBlank(username)){
            etPassword.requestFocus()
        }


        btnRegister.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.register_activity
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: RegisterResp){
        handleRegisterResp(event)
    }

    fun handleRegisterResp(resp : RegisterResp){
        hideLoading()
        if(resp.success){
            getApp().login(resp)
            Cache.put(mViewModel.registerReq,resp.token)
            HeartBeatService.startHeartBeatService(context)
            startHomeActivity()
            finish()
        }else{
            showToast(resp.reason)
        }
    }

    fun clickRegister(){

        if (!checkInput(etUsername, R.string.tips_username_is_empty)) {
            return
        }

        username = etUsername.text.toString()

        if (!checkInput(etPassword, R.string.tips_password_is_empty)) {
            return
        }

        val pwd = etPassword.text.toString()
        if (!CheckUtil.isPassword(pwd)) {
            showToast(R.string.tips_password_matcher)
            startShake(etPassword)
            return
        }

        if (!checkInput(etConfirmPassword, R.string.tips_confirm_password_is_empty)) {
            return
        }
        val confirmPwd = etConfirmPassword.text.toString()
        if (pwd != confirmPwd) {
            showToast(R.string.tips_password_is_different)
            return
        }

        val password = etPassword.text.toString()

        mViewModel.register(username!!,password)
    }


    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnRegister -> clickRegister()
            R.id.tvLogin -> onBackPressed()
        }
    }
}