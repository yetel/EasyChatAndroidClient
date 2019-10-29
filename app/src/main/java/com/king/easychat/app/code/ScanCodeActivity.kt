package com.king.easychat.app.code

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.databinding.ViewDataBinding
import com.king.base.util.StringUtils
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.friend.UserProfileActivity
import com.king.easychat.app.group.GroupProfileActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.app.web.WebActivity
import com.king.easychat.util.CheckUtil
import com.king.easychat.util.SystemBarHelper
import com.king.frame.mvvmframe.base.DataViewModel
import com.king.zxing.CaptureHelper
import com.king.zxing.OnCaptureCallback
import com.king.zxing.camera.CameraConfigurationUtils.setTorch
import kotlinx.android.synthetic.main.scan_code_activity.*
import kotlinx.android.synthetic.main.scan_toolbar.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ScanCodeActivity : BaseActivity<DataViewModel,ViewDataBinding>(), OnCaptureCallback {

    lateinit var helper: CaptureHelper

    override fun initData(savedInstanceState: Bundle?) {
        SystemBarHelper.immersiveStatusBar(this,0.2f)
        SystemBarHelper.setHeightAndPadding(this,toolbar)
        tvTitle.setText(com.king.easychat.R.string.scan_code_title)

        if(!hasTorch()){
            ivFlash.visibility = View.GONE
        }
        helper = CaptureHelper(this,surfaceView,viewfinderView)
        helper.setOnCaptureCallback(this)
        helper.playBeep(true)
            .continuousScan(true)
            .autoRestartPreviewAndDecode(false)
            .onCreate()
    }

    override fun getLayoutId(): Int {
        return com.king.easychat.R.layout.scan_code_activity
    }

    override fun onResume() {
        super.onResume()
        helper.onResume()
    }

    override fun onPause() {
        super.onPause()
        helper.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        helper.onDestroy()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        helper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    /**
     * 检测是否支持闪光灯（手电筒）
     * @return
     */
    private fun hasTorch(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    /**
     * 开启或关闭闪光灯（手电筒）
     * @param on `true`表示开启，`false`表示关闭
     */
    private fun setTorch(on: Boolean) {
        val camera = helper.cameraManager.openCamera.camera
        val parameters = camera.parameters
        setTorch(parameters, on)
        camera.parameters = parameters

    }

    private fun clickFlash(v: View) {
        val isSelected = v.isSelected
        setTorch(!isSelected)
        v.isSelected = !isSelected
    }

    override fun onResultCallback(result: String?): Boolean {
        result?.let {
            if(result.startsWith(Constants.USER_CODE_PREFIX,true)){//用户二维码
                var userId = result.substring(Constants.USER_CODE_PREFIX.length)
                if(getApp().getUserId() == userId){//自己
                    val intent = newIntent(UserInfoActivity::class.java)
                    intent.putExtra(Constants.KEY_ID,userId)
                    startActivity(intent)
                    finish()
                    return true
                }else{
                    val intent = newIntent(UserProfileActivity::class.java)
                    intent.putExtra(Constants.KEY_ID,userId)
                    startActivity(intent)
                    finish()
                    return true
                }

            }else if(result.startsWith(Constants.GROUP_CODE_PREFIX,true)){//群组二维码
                var groupId = result.substring(Constants.GROUP_CODE_PREFIX.length)
                if(StringUtils.isNotBlank(groupId)){
                    val intent = newIntent(GroupProfileActivity::class.java)
                    intent.putExtra(Constants.KEY_ID,groupId)
                    startActivity(intent)
                    finish()
                    return true
                }
            }else if(CheckUtil.isUrl(it)){//url
                val intent = newIntent(WebActivity::class.java)
                intent.putExtra(Constants.KEY_URL,it)
                startActivity(intent)
                finish()
                return true
            }
            //扫的其它信息，直接提示
            showToast(it)
            helper.restartPreviewAndDecode()
        }

        return true
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            com.king.easychat.R.id.ivFlash -> clickFlash(v)
        }
    }
}