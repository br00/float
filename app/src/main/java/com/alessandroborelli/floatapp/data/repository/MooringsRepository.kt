package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.Mooring
import com.alessandroborelli.floatapp.data.model.MooringDTO
import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MooringsRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun getAllMoorings(boatId: String): MooringsResult {
        return try {
            val snapshot = collection(boatId).get().await()
            val moorings = snapshot.toObjects(Mooring::class.java)
            if (moorings.isNullOrEmpty()) {
                MooringsResult.Failure.NoData
            } else {
                MooringsResult.Success(
                    data = moorings.sortedBy { it.creationDate }
                )
            }
        } catch (e : Exception){
            MooringsResult.Failure.ApiErrors("Ops! Something happened..")
        }
    }

    suspend fun addMooring(boatId: String, dto: MooringDTO): Boolean {
        return try {
            val snapshot = collection(boatId).add(dto).await()
            true
        } catch (e : Exception){
            false
        }
    }

    private companion object {
        private fun MooringsRepository.collection(boatId: String) =
            firebaseFirestore.collection(Constants.SUB_COLLECTION_MOORINGS(boatId))
    }
}