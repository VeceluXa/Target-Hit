package com.danilovfa.targethit.presentation.view.field

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import com.danilovfa.targethit.R
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.utils.Constants

open class FieldView(context: Context): View(context) {
    var target: Coordinate? = null

    var crosshairX = 0
        protected set
    var crosshairY = 0
        protected set

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        crosshairX = width / 2
        crosshairY = height / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (target != null)
            drawTarget(canvas, target!!)

        drawCrosshair(canvas, crosshairX, crosshairY)
    }

    fun updateTarget(coordinate: Coordinate?) {
        target = coordinate
        invalidate()
    }

    private fun drawTarget(canvas: Canvas?, target: Coordinate) {
        if (canvas == null)
            return

        val targetSize = Constants.TARGET_SIZE_DP.dpToPx()
        val targetBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.target)
        val rect = Rect(target.x - targetSize / 2,
            target.y - targetSize / 2,
            target.x + targetSize / 2,
            target.y + targetSize / 2)

        canvas.drawBitmap(targetBitmap, null, rect, null)
    }

    private fun drawCrosshair(canvas: Canvas?, x: Int, y: Int) {
        if (canvas == null)
            return

        val crosshairSize = Constants.CROSSHAIR_SIZE_DP.dpToPx()

        val crosshairBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.crosshair)
        val rect = Rect(x - crosshairSize / 2,
            y - crosshairSize / 2,
            x + crosshairSize / 2,
            y + crosshairSize / 2)

        canvas.drawBitmap(crosshairBitmap, null, rect, null)
    }

    /**
     * Check if crosshair is currently inside target's rectangle
     * If it is, remove rectangle.
     * @return true if crosshair inside target
     */
    fun isTargetHit(): Boolean {
        if (target == null)
            return false

        val crosshairSize = Constants.CROSSHAIR_SIZE_DP.dpToPx()
        val rect = Rect(target!!.x - crosshairSize / 2,
            target!!.y - crosshairSize / 2,
            target!!.x + crosshairSize / 2,
            target!!.y + crosshairSize / 2)

        return rect.contains(crosshairX, crosshairY)
    }

    /**
     * Converts display independent units to pixels
     */
    protected fun Int.dpToPx() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()
}