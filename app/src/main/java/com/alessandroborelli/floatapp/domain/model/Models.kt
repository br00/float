package com.alessandroborelli.floatapp.domain.model


data class MooringResult(
    val data: List<Mooring>
)

data class Mooring(
    val index: Int,
    val arrivedOn: String,
    val creationDate: String,
    val lastUpdate: String,
    val leftOn: String,
    val latitude: String,
    val longitude: String,
    val name: String
)