package com.king.easychat.app.me

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.databinding.MeFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MeFragment : BaseFragment<MeViewModel,MeFragmentBinding>(){

    companion object{
        fun newInstance(): MeFragment{
            return MeFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_me)
    }

    override fun getLayoutId(): Int {
        return R.layout.me_fragment
    }

}