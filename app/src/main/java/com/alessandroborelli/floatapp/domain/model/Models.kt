package com.alessandroborelli.floatapp.domain.model

import com.alessandroborelli.floatapp.data.Constants.INVALID_LAT_LNG
import kotlin.math.sqrt


data class MooringResult(
    val data: List<Mooring>
)

data class Mooring(
    val id: String,
    val index: Int,
    val arrivedOn: String,
    val creationDate: String,
    val lastUpdate: String,
    val leftOn: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
) {
    private val arriveDateTime = arrivedOn.split(") ")
    private val arriveDate = arriveDateTime.first()+")"
    private val arriveTime = arriveDateTime.last()
    private val leftDateTime = leftOn.split(") ")
    private val leftDate = leftDateTime.first()+")"
    private val leftTime = leftDateTime.last()

    val isCurrent = leftOn.isEmpty()

    val displayDate =
        if (isCurrent) arriveDate
        else "$arriveDate - $leftDate"

    //TODO replace with res strings
    val displayTime =
        if (isCurrent) "arrived at: $arriveTime"
        else "arrived at: $arriveTime - left at $leftTime"

    fun isValid(): Boolean {
        return arriveDate.isNotEmpty() &&
                latitude != INVALID_LAT_LNG &&
                longitude != INVALID_LAT_LNG //TODO add more conditions

    }

}

data class Location(val latitude: Double, val longitude: Double) {
    fun distance(that: Location): Double {
        val distanceLat = this.latitude - that.latitude
        val distanceLong = this.longitude - that.longitude

        return sqrt(distanceLat * distanceLat + distanceLong * distanceLong)
    }
}