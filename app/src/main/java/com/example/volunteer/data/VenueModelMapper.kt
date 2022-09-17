package com.example.volunteer.data

class VenueModelMapper {

    fun map(name: String, times: Map<String, String>): VenueModel =
        VenueModel(
            name = name,
            workingHours = times
        )
}
