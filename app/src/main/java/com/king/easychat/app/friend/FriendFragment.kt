package com.king.easychat.app.friend

import android.os.Bundle
import com.king.easychat.R
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.databinding.FriendFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendFragment : BaseFragment<FriendViewModel,FriendFragmentBinding>(){

    companion object{
        fun newInstance(): FriendFragment {
            return FriendFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_friend)
    }

    override fun getLayoutId(): Int {
        return R.layout.friend_fragment
    }

}