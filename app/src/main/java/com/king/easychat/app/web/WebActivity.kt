package com.king.easychat.app.web

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.*
import com.github.lzyzsd.jsbridge.DefaultHandler
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.base.BaseActivity
import com.king.easychat.databinding.WebActivityBinding
import com.king.frame.mvvmframe.base.DataViewModel
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.web_activity.*


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class WebActivity : BaseActivity<DataViewModel, WebActivityBinding>() {

    private lateinit var url: String

    private var isError = false

    companion object{
        const val BLANK_URL = "about:blank"
    }

    override fun initData(savedInstanceState: Bundle?) {

        val title = intent.getStringExtra(Constants.KEY_TITLE)
        title?.let {
            tvTitle.text = it
        }

        url = intent.getStringExtra(Constants.KEY_URL)

        web.setDefaultHandler(DefaultHandler())

        web.webChromeClient = object : WebChromeClient(){

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                title?.let {
                    if(!it.equals(BLANK_URL,true)){
                        tvTitle.text = it
                    }
                }

            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                updateProgress(newProgress,isError)
            }
        }
        web.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if(!url.equals(BLANK_URL,true)){
                    this@WebActivity.url = url
                }
                updateProgress(0,false)
                isError = false
            }



            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                updateProgress(100,isError)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                isError = true
                view?.loadUrl(BLANK_URL)
                updateProgress(0,true)
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                isError = true
                view?.loadUrl(BLANK_URL)
                updateProgress(0,true)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
                handler.cancel()
                handler.proceed()
            }


        }

        web.loadUrl(url)
    }

    private fun updateProgress(progress: Int,isError: Boolean){

        if(isError){
            pb.progress = 0
            pb.visibility = View.GONE
            llError.visibility = View.VISIBLE
        }else{
            pb.progress = progress
            if(llError.visibility != View.GONE){
                llError.visibility = View.GONE
            }

            if(progress<100){
                if(pb.visibility != View.VISIBLE){
                    pb.visibility = View.VISIBLE
                }

            }else{
                pb.visibility = View.GONE
            }

        }
    }


    override fun getLayoutId(): Int {
        return R.layout.web_activity
    }

    private fun retry(){
        web.loadUrl(url)
    }

    private fun isGoBack(): Boolean {
        return web != null && web.canGoBack()
    }


    override fun onBackPressed() {
        if(isGoBack() && !isError && !url.equals(BLANK_URL,true)){
            web.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ivLeft -> finish()
            R.id.llError -> retry()
        }
    }
}