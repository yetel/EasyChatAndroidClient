package com.king.easychat.util
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object AES {
    private const val IV_STRING = "ABCD1234EFGH5678"
    private const val charset = "UTF-8"

    fun encrypt(content: String, key: String): String? {
        try {
            val contentBytes = content.toByteArray(charset(charset))
            val keyBytes = key.toByteArray(charset(charset))
            val encryptedBytes = encrypt(contentBytes, keyBytes)
            return Base64.encodeToString(encryptedBytes,Base64.DEFAULT)
        } catch (e: Exception) {
            return null
        }

    }

    fun decrypt(content: String, key: String): String? {
        try {
            val encryptedBytes = Base64.decode(content,Base64.DEFAULT)
            val keyBytes = key.toByteArray(charset(charset))
            val decryptedBytes = decrypt(encryptedBytes, keyBytes)
            return String(decryptedBytes)
        } catch (e: Exception) {
            return null
        }

    }

    @Throws(Exception::class)
    private fun encrypt(contentBytes: ByteArray, keyBytes: ByteArray): ByteArray {
        return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE)
    }

    @Throws(Exception::class)
    private fun decrypt(contentBytes: ByteArray, keyBytes: ByteArray): ByteArray {
        return cipherOperation(contentBytes, keyBytes, Cipher.DECRYPT_MODE)
    }

    @Throws(Exception::class)
    private fun cipherOperation(
        contentBytes: ByteArray,
        keyBytes: ByteArray,
        mode: Int
    ): ByteArray {
        val secretKey = SecretKeySpec(keyBytes, "AES")

        val initParam = IV_STRING.toByteArray(charset(charset))
        val ivParameterSpec = IvParameterSpec(initParam)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(mode, secretKey, ivParameterSpec)

        return cipher.doFinal(contentBytes)
    }


}