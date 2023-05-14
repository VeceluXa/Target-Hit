package com.danilovfa.targethit.domain.time.timestamp

interface TimestampProvider {
    fun getMilliseconds(): Long
}