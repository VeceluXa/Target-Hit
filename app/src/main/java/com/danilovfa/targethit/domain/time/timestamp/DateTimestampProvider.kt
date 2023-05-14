package com.danilovfa.targethit.domain.time.timestamp

import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimestampProvider: TimestampProvider {
    override fun getMilliseconds(): Long {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
    }
}