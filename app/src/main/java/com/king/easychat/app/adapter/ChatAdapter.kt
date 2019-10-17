package com.king.easychat.app.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.glide.ImageLoader
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class ChatAdapter(var friendImageUrl : String?,var myImageUrl : String?): BaseMultiItemQuickAdapter<MessageResp, BindingHolder>(null) {

    init {

        addItemType(MessageResp.Left, R.layout.rv_chat_item)
        addItemType(MessageResp.Right, R.layout.rv_chat_right_item)
    }

    override fun convert(helper: BindingHolder, item: MessageResp?) {
        helper.mBinding?.let {
            when(item?.itemType){
                MessageResp.Left -> ImageLoader.displayImage(mContext,helper.getView(R.id.ivAvatar),friendImageUrl,R.drawable.default_avatar)
                MessageResp.Right -> ImageLoader.displayImage(mContext,helper.getView(R.id.ivAvatar),myImageUrl,R.drawable.default_avatar)
            }
            helper.addOnClickListener(R.id.ivContent)
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }


}