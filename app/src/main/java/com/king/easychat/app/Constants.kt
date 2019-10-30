package com.king.easychat.app

import com.king.easychat.BuildConfig

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object Constants {

    const val isDomain = true

    const val HOST = BuildConfig.API_HOST

    const val BASE_URL = "http://$HOST:9004"

    //---------------------------------------------

    const val TAG = "Jenly"

    //---------------------------------------------

    const val KEY_TITLE = "key_title"

    const val KEY_URL = "key_url"

    const val KEY_TOKEN = "key_token"

    const val KEY_USERNAME = "key_username"

    const val KEY_BEAN = "key_bean"

    const val KEY_ID = "key_id"
    const val KEY_IMAGE_URL = "key_image_url"

    const val KEY_LIST = "key_list"

    const val KEY_TYPE = "key_type"

    const val KEY_TIPS = "key_tips"

    const val KEY_MAX = "key_max"

    const val KEY_CONTENT = "key_content"

    //---------------------------------------------

    const val BUGLY_APP_ID = "96ccc1a8f6"

    //---------------------------------------------

    const val USER_CODE_PREFIX = "EasyChat://user/"
    const val GROUP_CODE_PREFIX = "EasyChat://group/"

    //---------------------------------------------

    const val REQ_SELECT_PHOTO = 0x01

    const val REQ_CROP_PHOTO = 0x02

    const val REQ_CHANGE_USER_INFO = 0x03

    const val REQ_SEARCH = 0x05

    //---------------------------------------------

    const val EVENT_SUCCESS = 0x01
    const val REFRESH_SUCCESS = 0x02

    const val EVENT_REFRESH_MESSAGE_COUNT = 0x03

    const val EVENT_NETTY_DISCONNECT = 0x04
    const val EVENT_NETTY_RECONNECT = 0x05

    const val EVENT_UPDATE_MESSAGE_READ = 0x06
    const val EVENT_UPDATE_GROUP_MESSAGE_READ = 0x07

    //---------------------------------------------

    const val USER_TYPE = 0
    const val GROUP_TYPE =1

    //---------------------------------------------

    const val PAGE_SIZE = 20

    const val DEFAULT_DIR =  ".EasyChat/"

    //---------------------------------------------

    fun getBaseUrl(host: String): String{
        return "http://$host:9004"
    }

    //---------------------------------------------

    val AUTHOR = "Jenly"
    val GMAIL = "jenly1314@gmail.com"
    val QQ_GROUP_KEY = "5D3GQFxzrzGft-E7FyRujPoCYBOcczbQ"

}