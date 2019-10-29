package com.king.easychat.app.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.king.base.adapter.divider.DividerItemDecoration
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.adapter.BindingAdapter
import com.king.easychat.app.base.BaseFragment
import com.king.easychat.app.chat.GroupChatActivity
import com.king.easychat.app.search.SearchActivity
import com.king.easychat.bean.Group
import com.king.easychat.databinding.GroupFragmentBinding
import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.PacketType
import com.king.easychat.netty.packet.resp.CreateGroupResp
import com.king.frame.mvvmframe.base.livedata.StatusEvent
import kotlinx.android.synthetic.main.group_fragment.*
import kotlinx.android.synthetic.main.home_toolbar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupFragment : BaseFragment<GroupViewModel,GroupFragmentBinding>(),View.OnClickListener{

    val mAdapter by lazy { BindingAdapter<Group>(R.layout.rv_group_item) }

    companion object{
        fun newInstance(): GroupFragment{
            return GroupFragment()
        }


    }

    override fun initData(savedInstanceState: Bundle?) {
        tvTitle.setText(R.string.menu_group)
        ivLeft.setImageResource(R.drawable.btn_create_group_selector)
        ivRight.setImageResource(R.drawable.btn_search_selector)
        ivLeft.setOnClickListener(this)
        ivRight.setOnClickListener(this)

        srl.setColorSchemeResources(R.color.colorAccent)

        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        rv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        rv.adapter = mAdapter

        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            clickItem(mAdapter.getItem(position)!!)
        }

        mBinding.viewModel = mViewModel

        registerStatusEvent {
            if(it!= StatusEvent.Status.LOADING){
                srl.isRefreshing = false
                setEmpty()
            }
        }

        mViewModel.groupsLiveData.observe(this, Observer<List<Group>>{
            it?.let {
                mAdapter.replaceData(it)
                getApp().groups = it
            }
            srl.isRefreshing = false
        })

    }

    private fun setEmpty(){
        if(mAdapter.emptyView == null){
            mAdapter.setEmptyView(R.layout.layout_empty,rv)
        }
    }

    fun clickItem(data: Group){
        val intent = newIntent(data.groupName,GroupChatActivity::class.java)
        intent.putExtra(Constants.KEY_ID,data.groupId)
        startActivity(intent)
    }

    override fun getLayoutId(): Int {
        return R.layout.group_fragment
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: CreateGroupResp){
        showToast(R.string.success_create)
        mViewModel.retry()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQ_SEARCH -> mViewModel.retry()
            }
        }
    }



    private fun clickCreateGroup(){
        var view = LayoutInflater.from(context).inflate(R.layout.create_group_dialog,null)
        var etContent = view.findViewById<EditText>(R.id.etDialogContent)
        var btnOk = view.findViewById<Button>(R.id.btnDialogOK)
        var btnCancel = view.findViewById<Button>(R.id.btnDialogCancel)
        btnOk.setOnClickListener{
            if(checkInput(etContent,R.string.tips_group_name)){
                dismissDialog()
                mViewModel.createGroup(etContent.text.toString().trim())
            }
        }
        btnCancel.setOnClickListener{
            dismissDialog()
        }

        showDialog(view)
        dialog.setOnKeyListener(null)
    }

    private fun clickSearch(){
        startActivityForResult(SearchActivity::class.java,Constants.REQ_SEARCH)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.ivLeft -> clickCreateGroup()
            R.id.ivRight -> clickSearch()
        }
    }


}