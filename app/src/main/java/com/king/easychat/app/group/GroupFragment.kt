package com.king.easychat.app.group

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.databinding.GroupFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupFragment : BaseFragment<GroupViewModel,GroupFragmentBinding>(){

    companion object{
        fun newInstance(): GroupFragment{
            return GroupFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

        tvTitle.setText(R.string.menu_group)
    }

    override fun getLayoutId(): Int {
        return R.layout.group_fragment
    }

}