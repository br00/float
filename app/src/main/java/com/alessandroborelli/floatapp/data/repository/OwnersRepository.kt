package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.Owner
import com.alessandroborelli.floatapp.data.model.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class OwnersRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    private val collection = firebaseFirestore.collection(Constants.COLLECTION_OWNERS)

    fun getOwnerDetails(id: String) = flow<Result<Owner>> {
        // Emit state loading
        emit(Result.loading())

        val snapshot = collection.document(id).get().await()
        val owner = snapshot.toObject(Owner::class.java)
        if (owner != null) {
            // Success
            emit(Result.success(owner))
        } else {
            // Fail
            emit(Result.failed("No owner found for that id"))
        }
    }.catch {
        emit(Result.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}