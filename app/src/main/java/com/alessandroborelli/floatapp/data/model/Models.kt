package com.alessandroborelli.floatapp.data.model

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
    val arrivedOn: Date? = null,
    val creationDate: Date? = null,
    val lastUpdate: Date? = null,
    val leftOn: Date? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val name: String? = null
)