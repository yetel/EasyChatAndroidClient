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
import com.king.easychat.databinding.LoginActivityBinding

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

    fun clickForgotPwd(){

    }

    fun clickLogin(){

        if (!checkInput(mBinding.etUsername, "请输入用户名")) {
            return
        }

        username = mBinding.etUsername.text.toString()

        if (!checkInput(mBinding.etPassword, "请输入密码")) {
            return
        }

        val password = mBinding.etPassword.text.toString()

        mViewModel.login(username!!,password)
    }

    fun clickRegister(){

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