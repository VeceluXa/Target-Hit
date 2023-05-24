package com.danilovfa.targethit.domain

import android.graphics.*
import com.danilovfa.targethit.domain.model.Score

class ImageResults {
    fun getImage(score: Score, imageWidth: Int, imageHeight: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        val coordinates = score.log
        if (coordinates.isEmpty())
            return bitmap

        val path = Path().apply {
            moveTo(coordinates[0].x.toFloat(), coordinates[0].y.toFloat())
            for (i in 1 until coordinates.size) {
                lineTo(coordinates[i].x.toFloat(), coordinates[i].y.toFloat())
            }
            close()
        }

        canvas.drawPath(path, paint)

        return bitmap
    }
}