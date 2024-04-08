package dev.kobalt.eqchk.android.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kobalt.eqchk.android.event.EventConverters
import dev.kobalt.eqchk.android.event.EventDao
import dev.kobalt.eqchk.android.event.EventEntity

@Database(entities = [EventEntity::class], version = 1)
@TypeConverters(EventConverters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}