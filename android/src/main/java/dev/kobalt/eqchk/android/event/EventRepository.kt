package dev.kobalt.eqchk.android.event

import javax.inject.Inject

class EventRepository @Inject constructor(
    private val api: EventApi,
    private val dao: EventDao,
) {

    val items get() = dao.selectAll()

    suspend fun fetch(): List<EventEntity> {
        return api.fetch()
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun insertAll(list: List<EventEntity>) {
        dao.insertAll(list)
    }

    /*
    fun selectList(): List<EventEntity> {
        return localCache.getList().sortedBy { it.timestamp }
    }

    fun selectItem(id: Int): EventEntity? {
        return localCache.getItem(id)
    }

    fun getLatestItem(): EventEntity? {
        return localCache.getItemWithLatestTimestamp()
    }

    fun reload(list: List<EventEntity>) {
        localCache.clear()
        localCache.add(list)
    }

    suspend fun fetchItem(id: String?): EventEntity? {
        return api.fetchItem(id)
    }

    suspend fun fetch(
        minMagnitude: Int? = null,
        maxMagnitude: Int? = null,
        minEstimatedIntensity: Int? = null,
        maxEstimatedIntensity: Int? = null,
        minCommunityIntensity: Int? = null,
        maxCommunityIntensity: Int? = null,
        minDepth: Int? = null,
        maxDepth: Int? = null,
        minTimestamp: LocalDateTime? = null,
        maxTimestamp: LocalDateTime? = null,
        latitude: Double? = null,
        longitude: Double? = null,
        range: Double? = null,
        limit: Int? = null
    ): List<EventEntity> {
        return api.fetch(
            minMagnitude,
            maxMagnitude,
            minEstimatedIntensity,
            maxEstimatedIntensity,
            minCommunityIntensity,
            maxCommunityIntensity,
            minDepth,
            maxDepth,
            minTimestamp,
            maxTimestamp,
            latitude,
            longitude,
            range,
            limit
        )
    }*/

}


