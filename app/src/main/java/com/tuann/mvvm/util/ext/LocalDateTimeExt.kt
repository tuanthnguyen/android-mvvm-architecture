package com.tuann.mvvm.util.ext

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun LocalDateTime.atAST(): ZonedDateTime {
    return atZone(ZoneId.of("AST", ZoneId.SHORT_IDS))
}
