package com.example.volunteer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueModel(
    val name: String,
    var workingHours: Map<Job, Boolean>
) : Parcelable

@Parcelize
data class Job(
    val day: String,
    val time: String
) : Parcelable
