package com.danilovfa.targethit.presentation.view.field

import android.content.Context
import com.danilovfa.targethit.domain.model.Coordinate

class ReplayFieldView(context: Context): FieldView(context) {
    fun updateCrosshair(coordinate: Coordinate) {
        crosshairX = coordinate.x
        crosshairY = coordinate.y
        invalidate()
    }
}