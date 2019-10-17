package com.king.easychat.util

import android.util.Base64
import java.io.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object FileUtil {


    fun imageToBase64(filename: String): String{
        return imageToBase64(File(filename))
    }

    fun imageToBase64(file: File): String{
        var bytes = file.toBytes()
        bytes?.let {
            return Base64.encodeToString(it,Base64.NO_WRAP)
        }

        return ""
    }

    fun File.toBytes(): ByteArray?{
        val length = length().toInt()
        var baos = ByteArrayOutputStream(length)
        var ins : InputStream? = null
        try {
            ins = BufferedInputStream(FileInputStream(this))
            ins?.let {
                var buffer = ByteArray(8192)
                var read  = it.read(buffer)
                while (read > 0 ){
                    baos.write(buffer,0 ,read)
                    read = it.read(buffer)
                }
            }

        }catch (e: Exception){

        }finally {
            try{
                ins?.close()
            }catch (e1: Exception){

            }
        }

        return baos.toByteArray()
    }
}