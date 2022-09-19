package com.example.volunteer.ui

import Job
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteer.R
import com.example.volunteer.data.VenueModel
import com.example.volunteer.data.User
import com.example.volunteer.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnClickJoinButton {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var venue: List<VenueModel>
    private lateinit var user: User
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var jobsAdapter: JobsAdapter
    private val bottomSheetView by lazy { findViewById<View>(R.id.standard_bottom_sheet) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getVenueData()
        getUserData()
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.maxHeight = 1000
        setBottomSheetVisibility(false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupCamera()
        setupMarkers()
    }

    private fun setupMarkers() {
        venue.forEach {
            map.addMarker(MarkerOptions().position(it.latLng).title(it.name))
        }
        map.setOnMarkerClickListener { marker ->
            val venue = venue.find { it.latLng == marker.position && it.name == marker.title }
                ?: error("Could not find venue")
            onMarkerClicked(venue)
            false
        }
        map.setOnMapClickListener { setBottomSheetVisibility(false) }
    }

    private fun setupCamera() {
        map.setMinZoomPreference(MIN_ZOOM)
        map.setLatLngBoundsForCameraTarget(LONDON_BOUNDS)
        map.moveCamera(CameraUpdateFactory.newLatLng(LONDON_LAT_LONG))
    }

    private fun onMarkerClicked(venue: VenueModel) {
        jobsAdapter = JobsAdapter(venue.jobsList)
        jobsAdapter.listener = this
        bottomSheetView.findViewById<TextView>(R.id.title).text = venue.name
        bottomSheetView.findViewById<TextView>(R.id.description_body).text = venue.description
        val recyclerView = bottomSheetView.findViewById<RecyclerView>(R.id.jobs)
        recyclerView.adapter = jobsAdapter
        recyclerView.layoutManager =
            object : LinearLayoutManager(this) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        setBottomSheetVisibility(true)
    }

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState =
            if (isVisible) {
                BottomSheetBehavior.STATE_EXPANDED
            } else {
                BottomSheetBehavior.STATE_COLLAPSED
            }
        bottomSheetBehavior.state = updatedState
    }

    private fun getVenueData() {
        venue = listOf(
            VenueModel(
                name = "Croydon Soup Kitchen",
                description = "You'll be serving food to guests ",
                latLng = LatLng(51.51, -0.13),
                jobsList = listOf(
                    Job(id = 1, day = "Monday", time = "7pm - 8pm", isUserAttending = false),
                    Job(id = 2, day = "Tuesday", time = "12pm - 1pm", isUserAttending = false),
                    Job(id = 3, day = "Wednesday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 4, day = "Thursday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 5, day = "Friday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 6, day = "Saturday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 7, day = "Saturday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 8, day = "Sunday", time = "7pm - 8pm", isUserAttending = false)
                )
            ),
            VenueModel(
                name = "Muswell Hill Soup Kitchen",
                description = "You'll be serving food to guests ",
                latLng = LatLng(51.52, -0.11),
                jobsList = listOf(
                    Job(id = 1, day = "Friday", time = "7pm - 8pm", isUserAttending = false),
                    Job(id = 2, day = "Saturday", time = "12pm - 1pm", isUserAttending = false),
                    Job(id = 3, day = "Sunday", time = "4pm - 5pm", isUserAttending = false),
                )
            ),
            VenueModel(
                name = "Embankment Soup Kitchen",
                description = "You'll be serving food to guests ",
                latLng = LatLng(51.50, -0.19),
                jobsList = listOf(
                    Job(id = 1, day = "Monday", time = "7pm - 8pm", isUserAttending = false),
                    Job(id = 2, day = "Tuesday", time = "12pm - 1pm", isUserAttending = false),
                    Job(id = 3, day = "Tuesday", time = "4pm - 5pm", isUserAttending = false),
                    Job(id = 4, day = "Saturday", time = "7pm - 8pm", isUserAttending = false)
                )
            )
        )
    }

    private fun getUserData() {
        user = User(mutableListOf())
    }

    companion object {
        const val MIN_ZOOM = 10f
        val LONDON_LAT_LONG = LatLng(51.5072, -0.12)
        val LONDON_BOUNDS = LatLngBounds(
            LatLng(51.2, -0.4),  // SW bounds
            LatLng(51.7, 0.2)    // NE bounds
        )
    }

    override fun setUserAttending(job: Job, position: Int) {
        job.isUserAttending = !job.isUserAttending
        if (job.isUserAttending) {
            user.job.add(job)
        } else {
            user.job.remove(job)
        }
        jobsAdapter.notifyItemChanged(position)
    }
}