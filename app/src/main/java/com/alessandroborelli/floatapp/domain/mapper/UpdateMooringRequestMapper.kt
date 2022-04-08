package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.data.model.UpdateMooringDTO
import com.alessandroborelli.floatapp.domain.usecase.UpdateMooringUseCase
import javax.inject.Inject

internal interface UpdateMooringRequestMapper {
    operator fun invoke(params: UpdateMooringUseCase.Params): UpdateMooringDTO
}

internal class UpdateMooringRequestMapperImpl @Inject constructor(): UpdateMooringRequestMapper {
    override fun invoke(params: UpdateMooringUseCase.Params): UpdateMooringDTO {
        return UpdateMooringDTO(
            firestoreId = params.id,
            leftOn = params.leftOn,
            lastUpdate = params.lastUpdate
        )
    }
}