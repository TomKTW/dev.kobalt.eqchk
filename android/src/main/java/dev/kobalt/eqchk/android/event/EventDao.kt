package dev.kobalt.eqchk.android.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY events.timestamp DESC")
    fun selectList(): List<EventEntity>

    @Query("SELECT * FROM events WHERE id = :id")
    fun selectItem(id: Int): EventEntity?

    @Query("SELECT * FROM events ORDER BY events.timestamp DESC LIMIT 1")
    fun selectItemWithLatestTimestamp(): EventEntity?

    @Insert
    fun insertList(events: List<EventEntity>)

    @Query("DELETE FROM events")
    fun delete()
}