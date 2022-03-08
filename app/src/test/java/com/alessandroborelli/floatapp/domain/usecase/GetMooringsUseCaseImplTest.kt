package com.alessandroborelli.floatapp.domain.usecase

import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.alessandroborelli.floatapp.data.repository.MooringsRepository
import com.alessandroborelli.floatapp.domain.mapper.GetMooringsResponseMapper
import com.alessandroborelli.floatapp.domain.model.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetMooringsUseCaseImplTest {

    private val repository = mock<MooringsRepository>()
    private val responseMapper = mock<GetMooringsResponseMapper>()
    private lateinit var useCase: GetMooringsUseCase
    private val boatId = "boatId"

    @Before
    fun setUp() {
        useCase = GetMooringsUseCaseImpl(
            mooringsRepository = repository,
            responseMapper = responseMapper
        )
    }

    @Test
    fun `Flow completes`() = runBlocking {
        stubRepository(
            GetMooringsUseCaseResultFactory.fakeSuccess()
        )
        val flowCount = useCase(boatId).count()
        assertThat(flowCount).isEqualTo(2)
    }

    @Test
    fun `Flow Success sequence`() = runBlocking {
        val repoResult = GetMooringsUseCaseResultFactory.fakeSuccess()
        stubRepository(repoResult)

        val flowResults = useCase(boatId).toList()

        verify(responseMapper).invoke(eq(repoResult))

        assertThat(flowResults.size).isEqualTo(2)
        assertThat(flowResults[0]).isInstanceOf(Result.Loading::class.java)
        assertThat(flowResults[1]).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `Flow Failure sequence`() = runBlocking {
        val repoResult = GetMooringsUseCaseResultFactory.fakeFailure()
        stubRepository(repoResult)

        val flowResults = useCase(boatId).toList()

        verify(responseMapper, never()).invoke(any())

        assertThat(flowResults.size).isEqualTo(2)
        assertThat(flowResults[0]).isInstanceOf(Result.Loading::class.java)
        assertThat(flowResults[1]).isInstanceOf(Result.Failed::class.java)
    }

    @Test
    fun `Flow Unhandled Exception sequence`() = runBlocking {
        val exception = RuntimeException("ops!")
        repository.stub {
            onBlocking {
                getAllMoorings(anyString())
            }.thenThrow(exception)
        }

        val flowResults = useCase(boatId).toList()

        verify(responseMapper, never()).invoke(any())
        assertThat(flowResults.size).isEqualTo(2)
        assertThat(flowResults[0]).isInstanceOf(Result.Loading::class.java)
        assertThat(flowResults[1]).isInstanceOf(Result.Failed::class.java)
    }

    private fun stubRepository(result: MooringsResult) {
        repository.stub {
            onBlocking {
                getAllMoorings(anyString())
            }.doReturn(result)
        }
    }

}