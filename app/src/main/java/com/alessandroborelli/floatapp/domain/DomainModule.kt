package com.alessandroborelli.floatapp.domain

import com.alessandroborelli.floatapp.domain.mapper.AddMooringRequestMapper
import com.alessandroborelli.floatapp.domain.mapper.AddMooringRequestMapperImpl
import com.alessandroborelli.floatapp.domain.mapper.GetMooringsResponseMapper
import com.alessandroborelli.floatapp.domain.mapper.GetMooringsResponseMapperImpl
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCase
import com.alessandroborelli.floatapp.domain.usecase.AddMooringUseCaseImpl
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCase
import com.alessandroborelli.floatapp.domain.usecase.GetMooringsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DomainModule {
    @Binds
    abstract fun GetMooringsResponseMapperImpl.bindMooringsResponseMapper(): GetMooringsResponseMapper

    @Binds
    abstract fun GetMooringsUseCaseImpl.bindMooringsUseCase(): GetMooringsUseCase

    @Binds
    abstract fun AddMooringRequestMapperImpl.bindAddMooringRequestMapper(): AddMooringRequestMapper

    @Binds
    abstract fun AddMooringUseCaseImpl.bindAddMooringUseCase(): AddMooringUseCase

}