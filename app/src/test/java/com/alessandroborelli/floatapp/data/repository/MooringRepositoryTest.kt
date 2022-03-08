package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.model.Mooring
import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalCoroutinesApi::class)
class MooringRepositoryTest {

    private val firebaseFirestore = mock<FirebaseFirestore>()
    private lateinit var repository: MooringsRepository
    private val boatId = "boatId"

    @Before
    fun setUp() {
        repository = MooringsRepository(
            firebaseFirestore = firebaseFirestore
        )
    }

    @Test
    fun `when getAllMoorings succeeds`() = runBlocking {
        val request = firebaseFirestore.collection(boatId).get().await()
        //TODO find a way to mock Task<QuerySnapshot> for the response
        //whenever(request).doReturn(response)
        val result = repository.getAllMoorings(boatId)
        assertThat(result).isInstanceOf(MooringsResult.Success::class.java)
    }
}