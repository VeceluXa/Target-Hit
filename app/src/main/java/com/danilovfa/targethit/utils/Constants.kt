package com.danilovfa.targethit.utils

class Constants {
    companion object {
        const val LEADERBOARD_SIZE = 10
        const val FPS = 120
        // Can only be dividable by 10. Less than or equal to 1000
        // i.e. 1, 10, 100, 1000
        const val STOPWATCH_UPDATE_TIME = 10
        const val TARGET_SIZE_DP = 100
        const val CROSSHAIR_SIZE_DP = 50
        const val CROSSHAIR_SPEED = 5
        const val GAME_FIELD_PADDING_DP = 5
        const val SCORE_UPDATE_VALUE = 5
        const val DOMAIN_GAME_FIELD_WIDTH = 600
        const val DOMAIN_GAME_FIELD_HEIGHT = 1200

        const val BASE_URL="https://target-hit-f5fc3-default-rtdb.europe-west1.firebasedatabase.app/"
    }
}