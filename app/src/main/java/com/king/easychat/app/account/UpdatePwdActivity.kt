package com.king.easychat.app.account

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.king.easychat.R
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.UpdatePwdActivityBinding
import com.king.easychat.util.CheckUtil
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.update_pwd_activity.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UpdatePwdActivity : BaseActivity<UpdatePwdViewModel, UpdatePwdActivityBinding>(),View.OnClickListener{

    override fun initData(savedInstanceState: Bundle?) {

        tvTitle.setText(R.string.modify_password)
        mBinding.viewModel = mViewModel
        etOldPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                etOldPassword.isSelected = !TextUtils.isEmpty(s)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                etNewPassword.isSelected = !TextUtils.isEmpty(s)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                etConfirmPassword.isSelected = !TextUtils.isEmpty(s)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        clickRightClear(etOldPassword)
        clickRightClear(etNewPassword)
        clickRightClear(etConfirmPassword)

        btnModify.setOnClickListener(this)

        registerStatusEvent {
            when(it){
                StatusEvent.Status.LOADING -> showLoading()
                StatusEvent.Status.SUCCESS -> {
                    hideLoading()
                    showToast(R.string.success_modify)
                    finish()
                }
                StatusEvent.Status.FAILURE -> hideLoading()
                StatusEvent.Status.ERROR -> hideLoading()
            }
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.update_pwd_activity
    }


    private fun clickModify(){
        if (!checkInput(etOldPassword, R.string.tips_old_password_is_empty)) {
            return
        }
        val oldPwd = etOldPassword.text.toString()

        if (!checkInput(etNewPassword, R.string.tips_new_password_is_empty)) {
            return
        }
        val newPwd = etNewPassword.text.toString()
        if (!CheckUtil.isPassword(newPwd)) {
            showToast(R.string.tips_password_matcher)
            startShake(etNewPassword)
            return
        }

        if (!checkInput(etConfirmPassword, R.string.tips_confirm_new_password_is_empty)) {
            return
        }
        val confirmPwd = etConfirmPassword.text.toString()
        if (newPwd != confirmPwd) {
            showToast(R.string.tips_password_is_different)
            return
        }

        mViewModel.updateUserPassword(oldPwd,newPwd)
    }


    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.btnModify -> clickModify()
        }
    }

}