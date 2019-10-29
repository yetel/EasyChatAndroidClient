package com.king.easychat.util

import android.graphics.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object BitmapUtil {

    fun getOvalBitmap(bitmap: Bitmap): Bitmap{
        val boxSize = 16
        val output = Bitmap.createBitmap(bitmap.width + boxSize, bitmap.height + boxSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = Color.WHITE
        val paint = Paint()
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        val rect = Rect(boxSize/2, boxSize/2, bitmap.width, bitmap.height)

        val rectF = RectF(0f,0f,output.width.toFloat(), output.height.toFloat())
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)


        return output
    }
}

