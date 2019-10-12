package com.king.easychat.util

import com.google.gson.Gson
import org.json.JSONObject


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object JsonUtil{

    private val gson = Gson()

    fun toJson(obj: Any): String{
        return gson.toJson(obj)
    }

    @ExperimentalStdlibApi
    fun toJsonBytes(obj: Any): ByteArray{
        return toJson(obj).encodeToByteArray()
    }

    fun <T> fromJson(json: String,clazz: Class<T>): T{
        return gson.fromJson(json,clazz)
    }


    fun getPacketType(json: String): Int {
        val jsonObject = JSONObject(json)
        return jsonObject.getInt("command")
    }

}

