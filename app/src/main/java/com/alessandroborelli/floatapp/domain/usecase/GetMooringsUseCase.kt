package com.alessandroborelli.floatapp.domain.usecase

import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.alessandroborelli.floatapp.domain.model.Result
import com.alessandroborelli.floatapp.data.repository.MooringsRepository
import com.alessandroborelli.floatapp.domain.mapper.GetMooringsResponseMapper
import com.alessandroborelli.floatapp.domain.model.MooringResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal interface GetMooringsUseCase {
    operator fun invoke(boatId: String): Flow<Result<MooringResult>>
}

internal class GetMooringsUseCaseImpl @Inject constructor(
    private val mooringsRepository: MooringsRepository,
    private val responseMapper: GetMooringsResponseMapper
): GetMooringsUseCase {
    override fun invoke(boatId: String) = flow<Result<MooringResult>> {
        emit(
            mooringsRepository.getAllMoorings(boatId = boatId).let {
                when (it) {
                    is MooringsResult.Failure.NoData -> {
                        Result.failed("No moorings found for the boatId:$boatId")
                    }
                    is MooringsResult.Success -> {
                        Result.success(responseMapper(it))
                    }
                    is MooringsResult.Failure.ApiErrors -> {
                        Result.failed("Api error") //TODO implement api errors
                    }
                }
            }
        )
    }.onStart {
        emit(Result.loading())
    }.catch {
        emit(Result.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}