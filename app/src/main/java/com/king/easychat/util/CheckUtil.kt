package com.king.easychat.util

import java.util.regex.Pattern

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object CheckUtil {


    /**
     * 密码验证 6-20位数字，不允许有空格
     *
     * @param password
     * @return
     */
    fun isPassword(password: String): Boolean {
        val regex = "^(?![0-9]+\$)(?![a-zA-Z]+\$)[0-9A-Za-z]{6,20}\$"
        return matcher(password,regex)
    }

    fun isUrl(url: String): Boolean{
        val regex = "[hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://"
        return matcher(url,regex)
    }

    fun matcher(content: String,regex: String): Boolean{
        val p = Pattern.compile(regex)
        val  m = p.matcher(content)
        return m.matches()
    }
}