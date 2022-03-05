package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.Mooring
import com.alessandroborelli.floatapp.data.model.MooringsResult
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class MooringsRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    suspend fun getAllMoorings(boatId: String): MooringsResult {
        val collection = firebaseFirestore.collection(Constants.SUB_COLLECTION_MOORINGS(boatId))
        return try {
            val snapshot = collection.get().await()
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
}