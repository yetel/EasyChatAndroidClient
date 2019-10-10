package com.king.easychat.app.friend

import android.os.Bundle
import android.view.View
import com.king.easychat.R
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.ChatActivity
import com.king.easychat.databinding.FriendFragmentBinding
import kotlinx.android.synthetic.main.home_toolbar.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendFragment : BaseFragment<FriendViewModel,FriendFragmentBinding>(), View.OnClickListener{

    companion object{
        fun newInstance(): FriendFragment {
            return FriendFragment()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_friend)
        ivRight.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.friend_fragment
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.ivRight -> startActivity(ChatActivity::class.java)
        }
    }

}