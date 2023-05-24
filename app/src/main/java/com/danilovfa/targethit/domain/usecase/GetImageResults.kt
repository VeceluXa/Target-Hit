package com.danilovfa.targethit.domain.usecase

import android.graphics.Bitmap
import com.danilovfa.targethit.domain.ImageResults
import com.danilovfa.targethit.domain.model.Score

class GetImageResults {
    fun execute(score: Score, imageWidth: Int, imageHeight: Int): Bitmap {
        val imageResults = ImageResults()
        return imageResults.getImage(score, imageWidth, imageHeight)
    }
}