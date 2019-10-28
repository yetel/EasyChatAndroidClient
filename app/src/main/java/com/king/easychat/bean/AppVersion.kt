package com.king.easychat.bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class AppVersion {
    /** 版本码  */
    val versionCode: Int? = null
    /** 版本名称  */
    val versionName: String? = null
    /** 版本更新描述  */
    val versionDesc: String? = null
    /** 下载链接  */
    val downloadUrl: String? = null
    /** 是否强制升级  */
    val isForcedUpgrade: Boolean = false

    /** 发布时间 */
    val releaseTime: String? = null

    override fun toString(): String {
        return "AppVersion(versionCode=$versionCode, versionName=$versionName, versionDesc=$versionDesc, downloadUrl=$downloadUrl, isForcedUpgrade=$isForcedUpgrade, releaseTime=$releaseTime)"
    }


}