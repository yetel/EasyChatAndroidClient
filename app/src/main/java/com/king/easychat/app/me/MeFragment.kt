package com.king.easychat.app.me

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.king.app.dialog.AppDialog
import com.king.app.dialog.AppDialogConfig
import com.king.app.updater.AppUpdater
import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.account.UpdatePwdActivity
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.me.about.AboutActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.app.photo.PhotoViewActivity
import com.king.easychat.databinding.MeFragmentBinding
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.home_toolbar.*
import kotlinx.android.synthetic.main.me_fragment.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MeFragment : BaseFragment<MeViewModel,MeFragmentBinding>(),View.OnClickListener{

    companion object{
        fun newInstance(): MeFragment{
            return MeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_me)

        mBinding.viewModel = mViewModel
        getApp().loginResp?.let {
            mBinding.setVariable(BR.data,it)
        }

        srl.setColorSchemeResources(R.color.colorAccent)
        mViewModel.userLiveData.observe(this, Observer {
            mBinding.user = it
            getApp().user = it
        })


        mViewModel.versionLiveData.observe(this, Observer {
            val url = it.downloadUrl
            val config = AppDialogConfig()
            with(config){
                isHideTitle = true
                isHideCancel = it.isForcedUpgrade
                content = (it.versionDesc ?: getString(R.string.tips_default_new_version))
                ok = getString(R.string.update_now)
                onClickOk = View.OnClickListener {
                    AppDialog.INSTANCE.dismissDialog()
                    url?.let {
                        appUpdater(url)
                    }
                }
            }

            AppDialog.INSTANCE.showDialog(context,config)
        })

        ivAvatar.setOnClickListener(this)
        clUser.setOnClickListener(this)
        tvPassword.setOnClickListener(this)
        tvAbout.setOnClickListener(this)
        tvVersion.setOnClickListener(this)

        registerStatusEvent {
            when(it){
                StatusEvent.Status.SUCCESS -> srl.isRefreshing = false
                StatusEvent.Status.FAILURE -> srl.isRefreshing = false
                StatusEvent.Status.ERROR -> srl.isRefreshing = false

            }
        }

    }

    private fun appUpdater(url: String){
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe{
                        if(it){
                            AppUpdater(context!!,url).start()
                        }
                    }

    }

    override fun getLayoutId(): Int {
        return R.layout.me_fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQ_CHANGE_USER_INFO -> mBinding.user = getApp().user
            }
        }
    }


    private fun clickAvatar(){
        getApp().getAvatar()?.let {
            startPhotoViewActivity(it,ivAvatar)
        }
    }

    private fun clickUser(){
        startActivityForResult(UserInfoActivity::class.java,Constants.REQ_CHANGE_USER_INFO)
    }

    private fun clickPassword(){
        startActivity(UpdatePwdActivity::class.java)
    }

    private fun clickAbout(){
        startActivity(AboutActivity::class.java)
    }

    private fun clickVersion(){
        mViewModel.checkVersion()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ivAvatar -> clickAvatar()
            R.id.clUser -> clickUser()
            R.id.tvPassword -> clickPassword()
            R.id.tvAbout -> clickAbout()
            R.id.tvVersion -> clickVersion()
        }
    }

}