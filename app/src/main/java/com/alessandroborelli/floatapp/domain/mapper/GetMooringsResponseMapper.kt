package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.domain.model.Mooring
import com.alessandroborelli.floatapp.domain.model.MooringResult
import javax.inject.Inject

private typealias MooringsResultDataLayer = com.alessandroborelli.floatapp.data.model.MooringsResult.Success

internal interface GetMooringsResponseMapper {
    operator fun invoke(result: MooringsResultDataLayer): MooringResult
}

internal class GetMooringsResponseMapperImpl @Inject constructor():
    GetMooringsResponseMapper {

    override fun invoke(result: MooringsResultDataLayer): MooringResult {
        return MooringResult(
            data = result.data.map {
                Mooring(
                    arrivedOn = "",
                    creationDate = "",
                    lastUpdate = "",
                    leftOn = "",
                    latitude = it.latitude.orEmpty(),
                    longitude = it.longitude.orEmpty(),
                    name = it.name.orEmpty()
                )
            }
        )
    }
}