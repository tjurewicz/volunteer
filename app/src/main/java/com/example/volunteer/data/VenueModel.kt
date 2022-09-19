package com.example.volunteer.data

import Job
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class VenueModel(
    val name: String,
    val description: String,
    val latLng: LatLng,
    var jobsList: List<Job>
) : Parcelable
