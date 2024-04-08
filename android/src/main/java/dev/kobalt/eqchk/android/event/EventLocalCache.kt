package dev.kobalt.eqchk.android.event

import javax.inject.Inject

class EventLocalCache @Inject constructor(
    private val dao: EventDao
) {

    fun getList(): List<EventEntity> {
        return dao.selectList()
    }

    fun getItem(id: Int): EventEntity? {
        return dao.selectItem(id)
    }

    fun getItemWithLatestTimestamp(): EventEntity? {
        return dao.selectItemWithLatestTimestamp()
    }

    fun add(events: List<EventEntity>) {
        dao.insertList(events)
    }

    fun clear() {
        dao.delete()
    }

}