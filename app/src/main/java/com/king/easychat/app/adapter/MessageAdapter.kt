package com.king.easychat.app.adapter

import com.king.easychat.BR
import com.king.easychat.R
import com.king.easychat.app.Constants
import com.king.easychat.app.home.HomeViewModel
import com.king.easychat.bean.Message
import com.king.easychat.util.Event
import com.king.easychat.view.DragBubbleView
import com.king.easychat.view.EasySwipeMenuLayout

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class MessageAdapter(val userId: String,val viewModel: HomeViewModel) : BindingAdapter<Message>(R.layout.rv_message_item) {

    var curTime =  System.currentTimeMillis()

    override fun convert(helper: BindingHolder, item: Message) {
        helper.getView<DragBubbleView>(R.id.dbvCount).setOnBubbleStateListener(object : DragBubbleView.OnBubbleStateListener{
            override fun onDrag() {
            }

            override fun onMove() {
            }

            override fun onRestore() {
            }

            override fun onDismiss() {
                when(item.messageMode){
                    Message.userMode -> viewModel.updateMessageRead(userId,item.id!!)
                    Message.groupMode -> viewModel.updateGroupMessageRead(userId,item.id!!)
                }
            }

        })

        helper.addOnClickListener(R.id.clContent)
        helper.addOnClickListener(R.id.llDelete)


        helper.mBinding?.let {
            it.setVariable(BR.curTime,curTime)
            it.setVariable(BR.data,item)
            it.executePendingBindings()
        }
    }
}