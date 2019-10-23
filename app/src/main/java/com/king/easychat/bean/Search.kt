package com.king.easychat.bean

import com.king.base.util.StringUtils

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class Search {

    companion object{
        const val USER_TYPE = 0
        const val GROUP_TYPE = 1
    }

    /** 用户id 或者群id */
    var id: String? = null
    /** 用户名称或者群名称 */
    var name: String? = null
    /** 用户 昵称 */
    var nickName: String? = null
    /** 用户图像或者群头像 */
    var avatar: String? = null
    /** 类型 0 用户类型 1 群类型 */
    var type: Int = USER_TYPE

    fun getShowName(): String?{
        if(StringUtils.isNotBlank(nickName)){
            return nickName
        }

        return name
    }

    fun getTypeName(): String?{
        when(type){
            USER_TYPE -> return "用户"
            GROUP_TYPE -> return "群组"
        }
        return "未知"
    }
}