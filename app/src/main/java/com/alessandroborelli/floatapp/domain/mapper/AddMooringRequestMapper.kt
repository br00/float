package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.data.model.MooringDTO
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import javax.inject.Inject

internal interface AddMooringRequestMapper {
    operator fun invoke(params: AddMooringUseCase.Params): MooringDTO
}

internal class AddMooringRequestMapperImpl @Inject constructor(): AddMooringRequestMapper {
    override fun invoke(params: AddMooringUseCase.Params): MooringDTO {
        return MooringDTO(
            name = params.name,
            index = params.index,
            creationDate = params.creationDate,
            arrivedOn = params.arrivedOn
        )
    }
}