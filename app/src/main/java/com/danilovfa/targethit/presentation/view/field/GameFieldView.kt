package com.danilovfa.targethit.presentation.view.field

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.TypedValue
import android.view.View
import com.danilovfa.targethit.R
import com.danilovfa.targethit.domain.model.Coordinate
import com.danilovfa.targethit.utils.Constants.Companion.CROSSHAIR_SIZE_DP
import com.danilovfa.targethit.utils.Constants.Companion.CROSSHAIR_SPEED
import com.danilovfa.targethit.utils.Constants.Companion.FPS
import com.danilovfa.targethit.utils.Constants.Companion.GAME_FIELD_PADDING_DP
import com.danilovfa.targethit.utils.Constants.Companion.TARGET_SIZE_DP

class GameFieldView(context: Context): FieldView(context), SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastUpdateMilliseconds = System.currentTimeMillis()

    override fun onSensorChanged(event: SensorEvent?) {
        val currentTime = System.currentTimeMillis()
        if (event != null && (currentTime - lastUpdateMilliseconds) > 1000 / FPS) {
            val deltaX = event.values[0].toInt() * CROSSHAIR_SPEED
            val deltaY = event.values[1].toInt() * CROSSHAIR_SPEED

            if (super.crosshairX - deltaX + GAME_FIELD_PADDING_DP.dpToPx() > 0 &&
                super.crosshairX - deltaX - GAME_FIELD_PADDING_DP.dpToPx() < width)
                super.crosshairX -= deltaX

            if (super.crosshairY + deltaY + GAME_FIELD_PADDING_DP.dpToPx() > 0 &&
                super.crosshairY + deltaY - GAME_FIELD_PADDING_DP.dpToPx() < height)
                super.crosshairY += deltaY

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
}