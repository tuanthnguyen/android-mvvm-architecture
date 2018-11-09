package com.tuann.mvvm.data.api.response.mapper

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.tuann.mvvm.util.ext.atVST
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

class InstantAdapter : JsonAdapter<Instant>() {
    override fun toJson(writer: JsonWriter, value: Instant?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.atVST().format(FORMATTER))
        }
    }

    override fun fromJson(reader: JsonReader): Instant? = when (reader.peek()) {
        JsonReader.Token.NULL -> reader.nextNull()
        else -> {
            val dateString = reader.nextString()
            parseDateString(dateString)
        }
    }

    companion object {
        private val FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

        /**
         * Obtains an instance of Instant from a text string such as "2018-11-04T11:01:34-05:00".
         * The string must represent in AST (GMT-04:00).
         */
        fun parseDateString(dateString: String): Instant {
            return OffsetDateTime.parse(dateString).toInstant()
        }
    }
}