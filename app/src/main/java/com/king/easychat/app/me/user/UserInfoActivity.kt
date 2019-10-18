package com.king.easychat.app.me.user

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.UserInfoActivityBinding
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import java.io.File

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class UserInfoActivity : BaseActivity<UserInfoViewModel, UserInfoActivityBinding>(){

    var avatarFile: File? = null

    override fun initData(savedInstanceState: Bundle?) {


        mViewModel.userLiveData.observe(this, Observer {
            it?.let {
                mBinding.data = it
            }
        })

    }


    override fun getLayoutId(): Int {
        return R.layout.user_info_activity
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQ_SELECT_PHOTO -> cropRawPhoto(obtainSelectPhoto(data))
                Constants.REQ_CROP_PHOTO -> avatarFile?.let { mViewModel.updateAvatar(it) }

            }
        }
    }

    private fun obtainSelectPhoto(data: Intent?): Uri?{
        val result = Matisse.obtainPathResult(data)
        return Uri.fromFile(File(result[0]))
    }

    /**
     * 使用UCrop进行图片剪裁
     *
     * @param uri
     */
    private fun cropRawPhoto(uri: Uri?) {
        uri?.let {
            val options = UCrop.Options()
            //        options.setToolbarTitle("裁剪");
            // 修改标题栏颜色
            options.setToolbarColor(resources.getColor(R.color.colorPrimary))
            // 修改状态栏颜色
            options.setStatusBarColor(resources.getColor(R.color.colorPrimaryDark))
            // 隐藏底部工具
            options.setHideBottomControls(true)
            // 图片格式
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG)
            // 设置图片压缩质量
            options.setCompressionQuality(100)
            // 是否让用户调整范围(默认false)，如果开启，可能会造成剪切的图片的长宽比不是设定的
            // 如果不开启，用户不能拖动选框，只能缩放图片
            options.setFreeStyleCropEnabled(false)
            options.setCircleDimmedLayer(true)
            avatarFile = File(getApp().getPath(), System.currentTimeMillis().toString() + ".jpg")

            // 设置源uri及目标uri
            UCrop.of(uri,Uri.fromFile(avatarFile))
                // 长宽比
                .withAspectRatio(1f, 1f)
                // 图片大小
                .withMaxResultSize(300, 300)
                // 配置参数
                .withOptions(options)
                .start(this,Constants.REQ_CROP_PHOTO)
        }
    }


    private fun clickAvatar(){
        selectPhoto()
    }

    private fun clickNickname(){
        val intent = newIntent(getString(R.string.nickname),ChangeUserInfoActivity::class.java)
        intent.putExtra(Constants.KEY_TYPE,ChangeUserInfoActivity.CHANGE_NICKNAME)
        intent.putExtra(Constants.KEY_TIPS,getString(R.string.tips_change_nickname))
        intent.putExtra(Constants.KEY_CONTENT,getApp().user?.getShowName())
        intent.putExtra(Constants.KEY_MAX,resources.getInteger(R.integer.nickname_max_length))
        startActivityForResult(intent,Constants.REQ_CHANGE_USER_INFO)
    }

    private fun clickSignature(){
        val intent = newIntent(getString(R.string.signature),ChangeUserInfoActivity::class.java)
        intent.putExtra(Constants.KEY_TYPE,ChangeUserInfoActivity.CHANGE_SIGNATURE)
        intent.putExtra(Constants.KEY_TIPS,getString(R.string.tips_change_signature))
        intent.putExtra(Constants.KEY_CONTENT,getApp().user?.getShowName())
        intent.putExtra(Constants.KEY_MAX,resources.getInteger(R.integer.signature_max_length))
        startActivityForResult(intent,Constants.REQ_CHANGE_USER_INFO)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.tvLabelAvatar -> clickAvatar()
            R.id.tvLabelNickname -> clickNickname()
            R.id.tvLabelSignature -> clickSignature()
        }
    }
}