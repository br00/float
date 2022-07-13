package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.data.model.AddMooringDTO
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

internal interface AddMooringRequestMapper {
    operator fun invoke(params: AddMooringUseCase.Params): AddMooringDTO
}

internal class AddMooringRequestMapperImpl @Inject constructor(): AddMooringRequestMapper {

    private companion object {
        val DATE_FORMATTER = SimpleDateFormat("d/M/yyyy HH:mm", Locale.UK)
    }

    override fun invoke(params: AddMooringUseCase.Params): AddMooringDTO {
        return AddMooringDTO(
            name = params.name,
            index = params.index,
            creationDate = params.creationDate,
            arrivedOn = mapDate(params.arrivedOn),
            leftOn = if (params.leftOn.isNotEmpty()) {
                mapDate(params.leftOn)
            } else null,
            latitude = params.latitude,
            longitude = params.longitude
        )
    }

    private fun mapDate(dateString: String): Timestamp {
        val date = DATE_FORMATTER.parse(dateString)
        var timestamp: Timestamp? = null
        if (date != null) {
            timestamp = Timestamp(date)
        }
        return timestamp ?: Timestamp(Date())
    }
}