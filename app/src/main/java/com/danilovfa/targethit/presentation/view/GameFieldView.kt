package com.danilovfa.targethit.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.danilovfa.targethit.R
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.domain.model.Level
import com.danilovfa.targethit.utils.Constants.Companion.CROSSHAIR_SIZE_DP
import com.danilovfa.targethit.utils.Constants.Companion.CROSSHAIR_SPEED
import com.danilovfa.targethit.utils.Constants.Companion.FPS
import com.danilovfa.targethit.utils.Constants.Companion.GAME_FIELD_PADDING_DP
import com.danilovfa.targethit.utils.Constants.Companion.TARGET_SIZE_DP
import com.danilovfa.targethit.utils.TAG

@SuppressLint("ViewConstructor")
class GameFieldView(context: Context, level: Level): View(context), SensorEventListener {
    var target: Coordinate? = null

    private val screenWidth = resources.displayMetrics.widthPixels
    private val screenHeight = resources.displayMetrics.heightPixels

    private var crosshairX = screenWidth / 2
    private var crosshairY = screenHeight / 2

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastUpdateMilliseconds = System.currentTimeMillis()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

         if (target != null)
             drawTarget(canvas, target!!)

        drawCrosshair(canvas, crosshairX, crosshairY)
    }

    fun startGame() {
        Log.d(TAG, "width: $screenWidth")
        Log.d(TAG, "height: $screenHeight")
    }

    fun updateTarget(coordinate: Coordinate?) {
        target = coordinate
        invalidate()
    }

    private fun drawTarget(canvas: Canvas?, target: Coordinate) {
        if (canvas == null)
            return

        val targetSize = TARGET_SIZE_DP.dpToPx()
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

        val crosshairSize = CROSSHAIR_SIZE_DP.dpToPx()

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

        val crosshairSize = CROSSHAIR_SIZE_DP.dpToPx()
        val rect = Rect(target!!.x - crosshairSize / 2,
            target!!.y - crosshairSize / 2,
            target!!.x + crosshairSize / 2,
            target!!.y + crosshairSize / 2)

        return rect.contains(crosshairX, crosshairY)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val currentTime = System.currentTimeMillis()
//        Log.d("Game", "onSensorChanged: $currentTime $lastUpdateMilliseconds")
        if (event != null && (currentTime - lastUpdateMilliseconds) > 1000 / FPS) {
            val deltaX = event.values[0].toInt() * CROSSHAIR_SPEED
            val deltaY = event.values[1].toInt() * CROSSHAIR_SPEED

            if (crosshairX - deltaX + GAME_FIELD_PADDING_DP.dpToPx() > 0 &&
                crosshairX - deltaX - GAME_FIELD_PADDING_DP.dpToPx() < screenWidth)
                crosshairX -= deltaX

            if (crosshairY + deltaY + GAME_FIELD_PADDING_DP.dpToPx() > 0 &&
                crosshairY + deltaY - GAME_FIELD_PADDING_DP.dpToPx() < screenHeight)
                crosshairY += deltaY


//            Log.d("Game", "onSensorChanged: $crosshairX, $crosshairY")

            lastUpdateMilliseconds = currentTime
            invalidate()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Not used
    }

    // Register listener when view is visible
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME)
    }

    // Remove listener when view is not visible
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        sensorManager.unregisterListener(this)
    }

    /**
     * Converts display independent units to pixels
     */
    private fun Int.dpToPx() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()
}