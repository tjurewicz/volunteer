package com.example.volunteer.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueModel(
    val name: String,
    val description: String,
    var jobsList: List<Job>
) : Parcelable

@Parcelize
data class Job(
    val id: String? = null,
    val day: String,
    val time: String,
    val isUserAttending: Boolean
) : Parcelable
