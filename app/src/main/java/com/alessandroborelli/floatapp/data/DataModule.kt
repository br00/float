package com.alessandroborelli.floatapp.data

import com.alessandroborelli.floatapp.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal object DataModule {

    @Provides
    @Reusable
    fun firebaseFirestoreClient(): FirebaseFirestore {
        val firebaseFirestore = Firebase.firestore
//        if (BuildConfig.DEBUG) { //TODO change this logic
//            firebaseFirestore.useEmulator("10.0.2.2", 8080)
//        }
        return firebaseFirestore
    }
}