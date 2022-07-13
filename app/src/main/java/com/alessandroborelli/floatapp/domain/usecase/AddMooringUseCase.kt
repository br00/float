package com.alessandroborelli.floatapp.domain.usecase

import com.alessandroborelli.floatapp.data.repository.MooringsRepository
import com.alessandroborelli.floatapp.domain.mapper.AddMooringRequestMapper
import com.alessandroborelli.floatapp.domain.model.Result
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal interface AddMooringUseCase {
    operator fun invoke(params: Params): Flow<Result<Boolean>>

    data class Params(
        val boatId: String,
        val index: Int,
        val name: String,
        val creationDate: Timestamp,
        val arrivedOn: String,
        val leftOn: String,
        val latitude: Double,
        val longitude: Double,
    )
}

internal class AddMooringUseCaseImpl @Inject constructor(
    private val mooringsRepository: MooringsRepository,
    private val requestMapper: AddMooringRequestMapper
): AddMooringUseCase {
    override fun invoke(params: AddMooringUseCase.Params) = flow<Result<Boolean>> {
        emit(
            mooringsRepository.addMooring(
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