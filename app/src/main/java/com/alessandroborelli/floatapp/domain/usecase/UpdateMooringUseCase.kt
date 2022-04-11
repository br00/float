package com.alessandroborelli.floatapp.domain.usecase

import com.alessandroborelli.floatapp.data.repository.MooringsRepository
import com.alessandroborelli.floatapp.domain.mapper.UpdateMooringRequestMapper
import com.alessandroborelli.floatapp.domain.model.Result
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal interface UpdateMooringUseCase {
    operator fun invoke(params: Params): Flow<Result<Boolean>>

    data class Params(
        val boatId: String,
        val id: String,
        val leftOn: Timestamp,
        val lastUpdate: Timestamp
    )
}

internal class UpdateMooringUseCaseImpl @Inject constructor(
    private val mooringsRepository: MooringsRepository,
    private val requestMapper: UpdateMooringRequestMapper
): UpdateMooringUseCase {
    override fun invoke(params: UpdateMooringUseCase.Params) = flow<Result<Boolean>> {
        emit(
            mooringsRepository.updateMooring(
                boatId = params.boatId,
                dto = requestMapper(params)
            ).let { isSuccessful ->
                if (isSuccessful) {
                    Result.success(true)
                } else {
                    Result.failed("Api error") //TODO implement api errors
                }
            }
        )
    }.onStart {
        emit(Result.loading())
    }.catch {
        emit(Result.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}