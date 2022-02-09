package com.alessandroborelli.floatapp.data.model

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

//TODO keep it or not?
internal sealed class Result {
    data class Success(
        val querySnapshot: QuerySnapshot? //TODO change
    ): Result()
    data class Failure(
        val exception: FirebaseFirestoreException?
    ): Result()
}

internal data class Owner(
    val fullName: String? = null,
    val imgUrl: String? = null
)