package com.king.easychat.app.base

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.king.base.util.StringUtils
import com.king.base.util.ToastUtils
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.account.LoginActivity
import com.king.easychat.app.home.HomeActivity
import com.king.easychat.util.Event
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseActivity<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseActivity<VM,VDB>(){

    open fun useEvent(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(useEvent()) Event.registerEvent(this)

    }

    override fun onDestroy() {
        if(useEvent()) Event.unregisterEvent(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAllEvent(event: Any){

    }


    fun clickRightClear(tv: TextView) {
        tv.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP ->  clickRightClear(tv, event)
            }
            false
        }
    }

    private fun clickRightClear(tv: TextView, event: MotionEvent): Boolean {
        val drawableRight = tv.compoundDrawables[2]
        if (drawableRight != null && event.rawX >= tv.right - drawableRight.bounds.width()) {
            tv.text = null
            return true
        }
        return false
    }


    fun clickRightEye(et: EditText) {
        et.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val drawableRight = et.compoundDrawables[2]
                    if (drawableRight != null && event.rawX >= et.right - drawableRight.bounds.width()) {
                        clickEye(et)
                        true
                    }

                }
            }

            false
        }
    }

    private fun clickEye(etPassword: EditText) {
        if (etPassword.inputType != InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {//隐藏密码
            etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            val drawableLeft = ContextCompat.getDrawable(context, R.drawable.ic_password)
            val drawableRight = ContextCompat.getDrawable(context, R.drawable.ic_hide)

            etPassword.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                drawableRight,
                null
            )
        } else {//显示密码
            etPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            val drawableLeft = ContextCompat.getDrawable(context, R.drawable.ic_password)
            val drawableRight = ContextCompat.getDrawable(context, R.drawable.ic_show)
            etPassword.setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                null,
                drawableRight,
                null
            )
        }
    }

    //-------------------------------------------------


    fun showToast(text: CharSequence) {
        if (StringUtils.isNotBlank(text)) {
            ToastUtils.showToast(context, text)
        }
    }

    fun showToast(@StringRes resId: Int) {
        ToastUtils.showToast(context, resId)
    }

    fun showTodo(){
        showToast(R.string.todo)
    }

    fun dismiss(popupWindow: PopupWindow?) {
        if (popupWindow != null && popupWindow.isShowing) {
            popupWindow.dismiss()
        }
    }

    fun checkInput(tv: TextView): Boolean {
        return !TextUtils.isEmpty(tv.text)
    }

    fun checkInput(tv: TextView, @StringRes msg: Int): Boolean {
        return checkInput(tv, msg, true)
    }

    fun checkInput(tv: TextView, @StringRes msg: Int, isShake: Boolean): Boolean {
        if (TextUtils.isEmpty(tv.text)) {
            showToast(msg)
            if (isShake) {
                startShake(tv)
            }
            return false
        }
        return true
    }

    fun checkInput(tv: TextView, msg: CharSequence): Boolean {
        return checkInput(tv, msg, true)
    }

    fun checkInput(tv: TextView, msg: CharSequence, isShake: Boolean): Boolean {
        if (TextUtils.isEmpty(tv.text)) {
            if (StringUtils.isNotBlank(msg)) {
                showToast(msg)
            }
            if (isShake) {
                startShake(tv)
            }
            return false
        }
        return true
    }

    /**
     * 摆动
     * @param view
     */
    fun startShake(view: View) {
        //        view.requestFocus()
        //        view.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.shake))
    }

    fun getApp(): App {
        return application as App
    }

    fun startActivity(title: String, cls: Class<*>) {
        startActivity(newIntent(title, cls))
    }

    fun newIntent(title: String, cls: Class<*>): Intent {
        val intent = Intent(context, cls)
        intent.putExtra(Constants.KEY_TITLE, title)
        return intent
    }

    fun startLoginActivity(username: String? = null){
        val intent = Intent(context,LoginActivity::class.java)
        intent.putExtra(Constants.KEY_USERNAME,username)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.anim_in, R.anim.anim_out)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun startHomeActivity(){
        val intent = Intent(context,HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.anim_in, R.anim.anim_out)
        startActivity(intent, optionsCompat.toBundle())
    }


    open fun onClick(v: View){
        if(v.id == R.id.ivLeft) onBackPressed()
    }
}