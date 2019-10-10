package com.king.easychat.app.home

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.databinding.HomeFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class HomeFragment : BaseFragment<HomeViewModel,HomeFragmentBinding>(){

    companion object{
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_message)
    }

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

}