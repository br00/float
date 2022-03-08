package com.alessandroborelli.floatapp.data

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
}