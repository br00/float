package com.alessandroborelli.floatapp.data

import com.alessandroborelli.floatapp.domain.model.Location

object Constants {
    const val COLLECTION_OWNERS = "owners"
    const val COLLECTION_BOATS = "boats"
    fun SUB_COLLECTION_MOORINGS(boatId: String) = buildString {
        append(COLLECTION_BOATS)
        append("/")
        append(boatId)
        append("/")
        append("moorings")
    }
    val LONDON = Location(51.525493,-0.0822173)
}