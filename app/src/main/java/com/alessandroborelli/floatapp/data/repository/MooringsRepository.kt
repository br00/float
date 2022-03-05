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
//    fun getAllMoorings(boatId: String) = flow<Result<MooringsResult>> {
//        val collection = firebaseFirestore.collection(Constants.SUB_COLLECTION_MOORINGS(boatId))
//
//        emit(Result.loading())
//
//        val snapshot = collection.get().await()
//        val moorings = snapshot.toObjects(Mooring::class.java)
//        if (moorings.isNullOrEmpty()) {
//            emit(Result.success(MooringsResult(moorings)))
//        } else {
//            emit(Result.failed("No moorings found for the boatId:$boatId"))
//        }
//    }.catch {
//        emit(Result.failed(it.message.toString()))
//    }.flowOn(Dispatchers.IO)

    suspend fun getAllMoorings(boatId: String): MooringsResult {
        val collection = firebaseFirestore.collection(Constants.SUB_COLLECTION_MOORINGS(boatId))
        return try {
            val snapshot = collection.get().await()
            val moorings = snapshot.toObjects(Mooring::class.java)
            if (moorings.isNullOrEmpty()) {
                MooringsResult.Failure.NoData
            } else {
                MooringsResult.Success(
                    data = moorings
                )
            }
        } catch (e : Exception){
            MooringsResult.Failure.ApiErrors("Ops! Something happened..")
        }
    }
}