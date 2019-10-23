package com.king.easychat.app.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.glide.ImageLoader
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class GroupChatAdapter: BaseMultiItemQuickAdapter<GroupMessageResp, BindingHolder> {

    var curTime =  System.currentTimeMillis()

    constructor() : super(null){
        addItemType(GroupMessageResp.Left, R.layout.rv_group_chat_item)
        addItemType(GroupMessageResp.Right, R.layout.rv_group_chat_right_item)
    }

    override fun convert(helper: BindingHolder, item: GroupMessageResp?) {
        helper.mBinding?.let {
            when(item?.itemType){
                MessageResp.Left -> ImageLoader.displayImage(mContext,helper.getView(R.id.ivAvatar),null,R.drawable.default_avatar)
                MessageResp.Right -> ImageLoader.displayImage(mContext,helper.getView(R.id.ivAvatar),null,R.drawable.default_avatar)
            }
            helper.addOnClickListener(R.id.ivContent)
            helper.addOnClickListener(R.id.ivAvatar)
            it.setVariable(BR.data,item)
            it.setVariable(BR.curTime,curTime)
            it.executePendingBindings()
        }
    }


}