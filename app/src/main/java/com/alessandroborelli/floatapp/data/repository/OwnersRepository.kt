package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.Owner
import com.alessandroborelli.floatapp.data.model.Result
import com.alessandroborelli.floatapp.data.model.State
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class OwnersRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
//    @OptIn(ExperimentalCoroutinesApi::class)
//    fun getOwnerDetails() = callbackFlow {
//        val collection = firebaseFirestore.collection("owners")
//        val snapshotListener = collection.addSnapshotListener { value, error ->
//            val response = if (error == null) {
//                Result.Success(value)
//            } else {
//                Result.Failure(error)
//            }
//            trySend(response)
//        }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }

    private val collection = firebaseFirestore.collection(Constants.COLLECTION_OWNERS)

    fun getOwnerDetails(id: String) = flow<State<Owner>> {

        // Emit state loading
        emit(State.loading())

        val snapshot = collection.document(id).get().await()
        val owner = snapshot.toObject(Owner::class.java)
        if (owner != null) {
            // Success
            emit(State.success(owner))
        } else {
            emit(State.failed("No owner found for that id"))
        }
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}