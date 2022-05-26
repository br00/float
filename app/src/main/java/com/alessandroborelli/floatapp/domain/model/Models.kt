package com.alessandroborelli.floatapp.domain.model


data class MooringResult(
    val data: List<Mooring>
)

data class Mooring(
    val id: String,
    val index: Int,
    val arrivedOn: String,
    val creationDate: String,
    val lastUpdate: String,
    val leftOn: String,
    val latitude: Double,
    val longitude: Double,
    val name: String
)