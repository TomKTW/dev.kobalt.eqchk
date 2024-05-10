package dev.kobalt.eqchk.android.event

import androidx.room.TypeConverter
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlinx.datetime.*

class EventConverters {
    @TypeConverter
    fun toTimestamp(value: Long?): LocalDateTime? {
        return value?.let { Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.UTC) }
    }

    @TypeConverter
    fun fromTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.UTC)?.toEpochMilliseconds()
    }

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? {
        return value?.toBigDecimal()
    }

    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? {
        return value?.toString()
    }
}