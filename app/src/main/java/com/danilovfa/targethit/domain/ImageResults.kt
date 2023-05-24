package com.danilovfa.targethit.domain

import android.graphics.*
import com.danilovfa.targethit.domain.model.Score

class ImageResults {
    fun getImage(score: Score, imageWidth: Int, imageHeight: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        val coordinates = score.log
        if (coordinates.isEmpty())
            return bitmap

        val path = Path()
        path.moveTo(coordinates[0].x.toFloat(), coordinates[0].y.toFloat())
        for (i in 1 until coordinates.size) {
            path.lineTo(coordinates[i].x.toFloat(), coordinates[i].y.toFloat())
        }
        path.close()
        
        canvas.drawPath(path, paint)

        return bitmap
    }
}