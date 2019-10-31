package com.king.easychat.util

import android.graphics.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object BitmapUtil {

    /**
     * 通过PorterDuff模式获取圆角图片
     */
    fun getOvalBitmap(bitmap: Bitmap): Bitmap{
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = Color.WHITE
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        val rectF = RectF(rect)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }
}

