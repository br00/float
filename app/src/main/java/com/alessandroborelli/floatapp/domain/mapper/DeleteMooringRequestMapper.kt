package com.alessandroborelli.floatapp.domain.mapper

import com.alessandroborelli.floatapp.data.model.DeleteMooringDTO
import com.alessandroborelli.floatapp.domain.usecase.DeleteMooringUseCase
import javax.inject.Inject

internal interface DeleteMooringRequestMapper {
    operator fun invoke(params: DeleteMooringUseCase.Params): DeleteMooringDTO
}

internal class DeleteMooringRequestMapperImpl @Inject constructor(): DeleteMooringRequestMapper {
    override fun invoke(params: DeleteMooringUseCase.Params): DeleteMooringDTO {
        return DeleteMooringDTO(
            firestoreId = params.id
        )
    }
}