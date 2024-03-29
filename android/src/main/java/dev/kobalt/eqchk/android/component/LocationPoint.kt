package dev.kobalt.eqchk.android.component

import kotlin.math.*

data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double
) {

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    fun distanceFrom(location: LocationPoint): Double {

        val lat1 = this.latitude
        val lon1 = this.longitude
        val alt1 = this.altitude
        val lat2 = location.latitude
        val lon2 = location.longitude
        val alt2 = location.altitude

        val r = 6371 // Radius of the earth

        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a =
            sin(latDistance / 2) * sin(latDistance / 2) + (cos(Math.toRadians(lat1)) * cos(
                Math.toRadians(lat2)
            )
                    * sin(lonDistance / 2) * sin(lonDistance / 2))
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        var distance = r.toDouble() * c * 1000.0 // convert to meters

        val height = alt1 - alt2

        distance = distance.pow(2.0) + height.pow(2.0)

        return sqrt(distance)
    }

}