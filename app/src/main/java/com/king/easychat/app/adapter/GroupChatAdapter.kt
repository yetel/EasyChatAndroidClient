package com.king.easychat.app.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.netty.packet.resp.GroupMessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatAdapter: BaseMultiItemQuickAdapter<GroupMessageResp, BindingHolder> {

    constructor() : super(null){
        addItemType(GroupMessageResp.Left, R.layout.rv_group_chat_item)
        addItemType(GroupMessageResp.Right, R.layout.rv_group_chat_right_item)
    }

    override fun convert(helper: BindingHolder, item: GroupMessageResp?) {
        helper.mBinding?.let {
            helper.addOnClickListener(R.id.ivContent)
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }


}