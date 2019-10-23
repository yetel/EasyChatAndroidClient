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

        val p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")
        val m = p.matcher(password)
        return m.matches()
    }
}