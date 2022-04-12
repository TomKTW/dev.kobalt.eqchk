package dev.kobalt.earthquakecheck.android.event

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import java.math.BigDecimal
import java.time.LocalDateTime

object EventTable : IntIdTable("event") {
    val uid: Column<String> = varchar("uid", 30)
    val location: Column<String> = varchar("location", 255)
    val timestamp: Column<LocalDateTime> = datetime("timestamp")
    val estimatedIntensity: Column<BigDecimal> = decimal("estimatedIntensity", 10, 2)
    val communityIntensity: Column<BigDecimal> = decimal("communityIntensity", 10, 2)
    val communityResponseCount: Column<Int> = integer("communityResponseCount")
    val magnitude: Column<BigDecimal> = decimal("magnitude", 10, 2)
    val latitude: Column<Double> = double("latitude")
    val longitude: Column<Double> = double("longitude")
    val depth: Column<Double> = double("depth")
    val detailsUrl: Column<String> = varchar("detailsUrl", 255)
    val tectonicSummary: Column<String> = text("tectonicSummary")
    val impactSummary: Column<String> = text("impactSummary")
}