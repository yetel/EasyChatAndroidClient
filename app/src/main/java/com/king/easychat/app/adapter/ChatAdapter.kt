package com.king.easychat.app.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatAdapter: BaseMultiItemQuickAdapter<MessageResp, BindingHolder> {

    constructor() : super(null){
        addItemType(MessageResp.Left, R.layout.rv_chat_item)
        addItemType(MessageResp.Right, R.layout.rv_chat_right_item)
    }

    override fun convert(helper: BindingHolder, item: MessageResp?) {
        helper.mBinding?.let {
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }


}