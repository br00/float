package com.alessandroborelli.floatapp.domain.usecase

import com.alessandroborelli.floatapp.data.model.Mooring
import com.alessandroborelli.floatapp.data.model.MooringsResult

internal object GetMooringsUseCaseResultFactory {

    fun fakeSuccess() = MooringsResult.Success(
        data = listOf(
            Mooring(
                index = null,
                arrivedOn = null,
                creationDate = null,
                lastUpdate = null,
                leftOn = null,
                latitude = null,
                longitude = null,
                name = null
            )
        )
    )

    fun fakeFailure() = MooringsResult.Failure.ApiErrors(
        errorMessage = "an error message"
    )
}