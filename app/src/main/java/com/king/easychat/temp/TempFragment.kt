package com.king.easychat.temp

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.king.easychat.app.account.RegisterViewModel
import com.king.easychat.app.base.BaseFragment

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class TempFragment : BaseFragment<TempViewModel,ViewDataBinding>(){

    companion object{
        fun newInstance(): TempFragment{
            return TempFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int {
        return 0
    }

}