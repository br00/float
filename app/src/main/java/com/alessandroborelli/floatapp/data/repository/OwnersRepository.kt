package com.alessandroborelli.floatapp.data.repository

import com.alessandroborelli.floatapp.data.Constants
import com.alessandroborelli.floatapp.data.model.Owner
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class OwnersRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {
    private val collection = firebaseFirestore.collection(Constants.COLLECTION_OWNERS)

    suspend fun getOwnerDetails(ownerId: String) {
        //TODO
        val snapshot = collection.document(ownerId).get().await()
        val owner = snapshot.toObject(Owner::class.java)
    }
}