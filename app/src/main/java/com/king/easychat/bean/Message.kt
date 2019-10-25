package com.king.easychat.bean

import com.king.base.util.StringUtils
import com.king.easychat.util.AES

/**
 * @author Zed
 * date: 2019/10/12.
 * description: 消息列表
 */
class Message {

    companion object{
        var userMode = 0
        var groupMode = 1
    }

    /** 用户id 或者群聊id*/
    var id : String?=null
    /** 消息时间 用于排序*/
    var dateTime : String?=null
    /** 0 单聊消息 1 群聊消息*/
    var messageMode : Int?=null

    /** 单聊为好友id 群聊为发送者id*/
    var senderId : String?=null
    /** 发送的消息内容*/
    var message : String?=null

    /** 0 普通消息 1 图片消息*/
    var messageType : Int?=null

    var name : String? = null

    var avatar: String? = null

    /**
     * 未读消息数
     */
    var count = 0

    fun getMsg() : String? {
        if(messageType ==1){
            return "[图片消息]"
        }
        message?.let {
            return AES.decrypt(it, "${dateTime}ab")
        }
       return null
    }
    
    fun getShowName(): String?{
        return if(StringUtils.isNotBlank(name)) name else id
    }

    override fun toString(): String {
        return "Message(id=$id, dateTime=$dateTime, messageMode=$messageMode, senderId=$senderId, message=$message, messageType=$messageType, name=$name, avatar=$avatar, count=$count)"
    }


}