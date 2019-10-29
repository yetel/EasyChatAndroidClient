package com.king.easychat.app.base

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
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
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.base.util.StringUtils
import com.king.base.util.ToastUtils
import com.king.easychat.App
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.account.LoginActivity
import com.king.easychat.app.code.ScanCodeActivity
import com.king.easychat.app.home.HomeActivity
import com.king.easychat.app.photo.PhotoViewActivity
import com.king.easychat.bean.Operator
import com.king.easychat.glide.GlideEngine
import com.king.easychat.netty.NettyClient
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.AddUserResp
import com.king.easychat.netty.packet.resp.InviteGroupResp
import com.king.easychat.netty.packet.resp.LoginResp
import com.king.easychat.util.Cache
import com.king.easychat.util.Event
import com.king.frame.mvvmframe.base.BaseActivity
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseActivity<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseActivity<VM,VDB>(){

    val rxPermission by lazy { RxPermissions(this@BaseActivity) }

    var isStop = true

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: Operator){
        Timber.d("event:${event.event}")
        if(!isStop) {
            when (event.event) {
                Constants.EVENT_NETTY_DISCONNECT -> showToast(R.string.tips_netty_disconnect)
                Constants.EVENT_NETTY_RECONNECT -> showToast(R.string.tips_netty_reconnect)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: Packet){
        Timber.d("event:${event.packetType()}")
        if(!isStop){
            when(event.packetType()){
                PacketType.ADD_FRIEND_RESP -> handlerAddUserResp(event as AddUserResp)
                PacketType.INVITE_GROUP_RESP -> handlerInviteGroupResp(event as InviteGroupResp)
                PacketType.LOGIN_RESP -> handleLoginResp(event as LoginResp)
            }
        }
    }

    open fun handleLoginResp(resp : LoginResp){
        if(resp.success){
            showToast(R.string.tips_netty_connected)
            getApp().login(resp)
        }
    }

    /**
     * 处理添加好友
     */
    private fun handlerAddUserResp(data: AddUserResp){
        var config = AppDialogConfig()
        with(config){
            isHideTitle = true
            content = String.format(getString(R.string.tips_accept_friend_),data.inviterId)
            ok = getString(R.string.accept)
            cancel = getString(R.string.ignore)
            onClickOk =  View.OnClickListener {
                NettyClient.INSTANCE.sendAcceptReq(data.inviterId,true)
                AppDialog.INSTANCE.dismissDialog()
            }

        }
        AppDialog.INSTANCE.showDialog(context,config)
    }

    private fun handlerInviteGroupResp(data: InviteGroupResp){
        var config = AppDialogConfig()
        with(config){
            isHideTitle = true
            content = String.format(getString(R.string.tips_accept_group_),data.inviteId,data.groupName)
            ok = getString(R.string.accept)
            cancel = getString(R.string.ignore)
            onClickOk =  View.OnClickListener {
                NettyClient.INSTANCE.sendAcceptGroupReq(data.groupId!!,data.inviteId!!,true)
                AppDialog.INSTANCE.dismissDialog()
            }
        }
        AppDialog.INSTANCE.showDialog(context,config)
    }

    fun selectPhoto(){
        rxPermission.request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)
            .subscribe{
                if(it){
                    Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .capture(true)
                        .captureStrategy(CaptureStrategy(true, "$packageName.fileProvider"))
                        .maxSelectable(1)
                        .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.size_120dp))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(GlideEngine())
                        .forResult(Constants.REQ_SELECT_PHOTO)
                }
            }

    }

    open fun useEvent(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(useEvent()) Event.registerEvent(this)
        super.onCreate(savedInstanceState)
        registerMessageEvent{
            Timber.d("sendMessage:$it")
            showToast(it)
        }


    }

    override fun onDestroy() {
        if(useEvent()) Event.unregisterEvent(this)
        super.onDestroy()
    }

    override fun onResume() {
        isStop = false
        super.onResume()
    }

    override fun onStop() {
        isStop = true
        super.onStop()
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

    fun startLoginActivity(username: String? = Cache.getUsername()){
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

    fun startPhotoViewActivity(imgUrl: String,v: View){
        val intent = Intent(context, PhotoViewActivity::class.java)
        var list = ArrayList<String>()
        list.add(imgUrl)
        intent.putStringArrayListExtra(Constants.KEY_LIST,list)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,v,
            PhotoViewActivity.IMAGE)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun startScanCodeActivity(){
        val intent = Intent(context,ScanCodeActivity::class.java)
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.anim_in, R.anim.anim_out)
        startActivity(intent, optionsCompat.toBundle())
    }

    fun clickScan(){
        rxPermission.request(Manifest.permission.CAMERA)
            .subscribe{
                if(it){
                    startScanCodeActivity()
                }
            }
    }

    open fun onClick(v: View){
        if(v.id == R.id.ivLeft) onBackPressed()
    }
}