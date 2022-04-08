package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.domain.model.MooringResult
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private typealias MooringsResultDataLayer = com.alessandroborelli.floatapp.data.model.MooringsResult.Success

internal interface GetMooringsResponseMapper {
    operator fun invoke(result: MooringsResultDataLayer): MooringResult
}

internal class GetMooringsResponseMapperImpl @Inject constructor():
    GetMooringsResponseMapper {

    private companion object {
        const val UNKNOWN_INDEX = -1
        val DATE_FORMATTER = SimpleDateFormat("dd MMM (E) HH:mm", Locale.ROOT)
    }

    override fun invoke(result: MooringsResultDataLayer): MooringResult {
        return MooringResult(
            data = result.data.map {
                Mooring(
                    id = it.firestoreId.orEmpty(), //TODO id should be not null
                    index = it.index ?: UNKNOWN_INDEX,
                    arrivedOn = mapDate(it.arrivedOn),
                    creationDate = mapDate(it.creationDate),
                    lastUpdate = mapDate(it.lastUpdate),
                    leftOn = mapDate(it.leftOn),
                    latitude = it.latitude.orEmpty(),
                    longitude = it.longitude.orEmpty(),
                    name = it.name.orEmpty()
                )
            }
        )
    }

    private fun mapDate(date: Date?): String {
        return if (date != null) {
            DATE_FORMATTER.format(date)
        } else {
            ""
        }
    }
}