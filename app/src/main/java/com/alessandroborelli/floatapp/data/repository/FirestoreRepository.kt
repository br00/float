package com.alessandroborelli.floatapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

internal class FirestoreRepository @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
){
    //TODO generic repo?
}