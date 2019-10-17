package com.king.easychat.bean

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class Result<T> {

    var code : String? = null

    var desc : String? = null

    var data : T? = null

    fun isSuccess(): Boolean{
        return "0" == code
    }

    override fun toString(): String {
        return "Result(code=$code, desc=$desc, data=$data)"
    }


}