package com.example.volunteer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueModel(
    val name: String,
    val workingHours: Map<String, String>
) : Parcelable
