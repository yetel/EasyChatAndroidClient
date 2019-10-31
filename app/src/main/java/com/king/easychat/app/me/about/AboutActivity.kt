package com.king.easychat.app.me.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.king.easychat.BuildConfig
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.app.web.WebActivity
import com.king.easychat.databinding.AboutActivityBinding
import com.king.easychat.util.SystemBarHelper
import com.king.frame.mvvmframe.base.DataViewModel
import kotlinx.android.synthetic.main.about_activity.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AboutActivity : BaseActivity<DataViewModel,AboutActivityBinding>(){

    override fun initData(savedInstanceState: Bundle?) {
        SystemBarHelper.immersiveStatusBar(this,0.0f)
        SystemBarHelper.setHeightAndPadding(this,toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener{ onBackPressed() }
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(context, R.color.white))
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(context, R.color.white))
        collapsingToolbar.collapsedTitleGravity = Gravity.CENTER

        tvVersion.text = "${getString(R.string.app_name)} v${BuildConfig.VERSION_NAME}"
    }

    override fun getLayoutId(): Int {
        return R.layout.about_activity
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_me, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuGithub -> startWeb(Constants.AUTHOR, "https://github.com/jenly1314")
            R.id.menuSource -> startWeb(
                getString(R.string.menu_source),
                "https://github.com/yetel/EasyChatAndroidClient"
            )
            R.id.menuIssue -> startWeb(
                getString(R.string.menu_issue),
                "https://github.com/yetel/EasyChatAndroidClient/issues"
            )
            R.id.menuEmail -> clickEmail(Constants.GMAIL)
            R.id.menuQQGroup -> joinQQGroup(Constants.QQ_GROUP_KEY)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clickEmail(email: String) {
        val uri = Uri.parse(String.format("mailto:%s", email))
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(Intent.createChooser(intent, null))
    }

    private fun joinQQGroup(key: String): Boolean {
        val intent = Intent()
        intent.data = Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D$key")
        try {
            startActivity(intent)
            return true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            showToast(R.string.tips_join_qq_group_exception)
            return false
        }

    }


    private fun startWeb(title: String,url: String){
        val intent = newIntent(title,WebActivity::class.java)
        intent.putExtra(Constants.KEY_URL,url)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when(v.id){
            R.id.fab -> startWeb(getString(R.string.app_name), "https://github.com/yetel/EasyChatAndroidClient")
        }
    }

}