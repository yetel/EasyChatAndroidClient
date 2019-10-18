package com.king.easychat.app.me.user

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.ChangeUserInfoActivityBinding
import kotlinx.android.synthetic.main.change_user_info_activity.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChangeUserInfoActivity : BaseActivity<ChangeUserInfoViewModel, ChangeUserInfoActivityBinding>(){

    var changeType = CHANGE_NICKNAME

    companion object{
        const val CHANGE_NICKNAME = 0
        const val CHANGE_SIGNATURE = 1
    }

    override fun initData(savedInstanceState: Bundle?) {

        ivRight.setImageResource(R.drawable.btn_save_selector)
        tvTitle.text = intent.getStringExtra(Constants.KEY_TITLE)
        changeType = intent.getIntExtra(Constants.KEY_TYPE,CHANGE_NICKNAME)

        tvTips.text = intent.getStringExtra(Constants.KEY_TIPS)
//
        etContent.maxEms = intent.getIntExtra(Constants.KEY_MAX,20)
        etContent.setText(intent.getStringExtra(Constants.KEY_CONTENT))
//
        mViewModel.userLiveData.observe(this, Observer {
            it?.let {
                setResult(Activity.RESULT_OK)
                onBackPressed()
            }
        })
    }


    override fun getLayoutId(): Int {
        return R.layout.change_user_info_activity
    }

    private fun clickSave(){
        when(changeType){
            CHANGE_NICKNAME ->  clickNickname()
            CHANGE_SIGNATURE -> clickSignature()
        }
    }

    private fun clickNickname(){
        if(checkInput(etContent,R.string.tips_password_is_empty)){
            mViewModel.updateNickname(etContent.text.toString().trim())
        }

    }

    private fun clickSignature(){
        if(checkInput(etContent)){
            mViewModel.updateSignature(etContent.text.toString().trim())
        }
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.ivRight -> clickSave()
        }
    }
}