package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.AddMooringDTO
import com.alessandroborelli.floatapp.data.model.DeleteMooringDTO
import com.alessandroborelli.floatapp.data.model.Mooring
import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.alessandroborelli.floatapp.data.model.UpdateMooringDTO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MooringsRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun getAllMoorings(boatId: String): MooringsResult {
        return try {
            val snapshot = collection(boatId).get().await()
            val moorings = mutableListOf<Mooring>()
            snapshot.documents.forEach { document ->
                val mooring = document.toObject(Mooring::class.java)
                if (mooring != null) {
                    mooring.firestoreId = document.id
                    moorings.add(mooring)
                }
            }
            if (moorings.isNullOrEmpty()) {
                MooringsResult.Failure.NoData
            } else {
                MooringsResult.Success(
                    data = moorings.sortedBy { it.arrivedOn }.asReversed()
                )
            }
        } catch (e : Exception){
            MooringsResult.Failure.ApiErrors("Ops! Something happened..")
        }
    }

    suspend fun addMooring(boatId: String, dto: AddMooringDTO): Boolean {
        return try {
            val snapshot = collection(boatId).add(dto).await()
            true
        } catch (e : Exception){
            false
        }
    }

    /**
     * Update a Mooring or just its single field.
     * Instead of using a unmutable dto we use a nullable Mooring
     * object to avoid duplication and makes possible to
     * send just a single field.
     **/
    suspend fun updateMooring(boatId: String, dto: UpdateMooringDTO): Boolean {
        return try {
            val snapshot = collection(boatId)
                .document(dto.firestoreId.orEmpty())
                .set(dto, SetOptions.merge())
                .await()
            true
        } catch (e : Exception){
            false
        }
    }

    suspend fun deleteMooring(boatId: String, dto: DeleteMooringDTO): Boolean {
        return try {
            val snapshot = collection(boatId)
                .document(dto.firestoreId.orEmpty())
                .delete()
                .await()
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