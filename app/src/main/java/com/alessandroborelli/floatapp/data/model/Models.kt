package com.alessandroborelli.floatapp.data.model

import com.google.firebase.Timestamp
import java.util.*


internal data class Owner(
    val fullName: String? = null,
    val imgUrl: String? = null
)

internal sealed class MooringsResult {
    data class Success(
        val data: List<Mooring>
    ): MooringsResult()

    sealed class Failure : MooringsResult() {
        object NoData: Failure()
        data class ApiErrors(val errorMessage: String): Failure() //TODO make generic errors class
    }
}

internal data class Mooring(
    var firestoreId: String? = null,
    val index: Int? = null,
    val arrivedOn: Date? = null,
    val creationDate: Date? = null,
    val lastUpdate: Date? = null,
    val leftOn: Date? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val name: String? = null,
    val notes: String? = null
)

internal data class AddMooringDTO(
    val index: Int,
    val name: String,
    val notes: String,
    val creationDate: Timestamp,
    val arrivedOn: Timestamp,
    val leftOn: Timestamp?,
    val latitude: Double,
    val longitude: Double,
)

internal data class UpdateMooringDTO(
    var firestoreId: String? = null,
    val lastUpdate: Timestamp? = null,
    val leftOn: Timestamp? = null,
)

internal data class DeleteMooringDTO(
    var firestoreId: String? = null
)