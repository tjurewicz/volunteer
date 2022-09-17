package com.example.volunteer.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteer.R
import com.example.volunteer.data.VenueModel
import com.example.volunteer.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var venue: VenueModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var workingHoursAdapter: WorkingHoursAdapter
    private val bottomSheetView by lazy { findViewById<View>(R.id.standard_bottom_sheet) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getVenueData()
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.maxHeight = 2000
        setBottomSheetVisibility(false)
    }

    private fun getVenueData() {
        venue = VenueModel(
            name = "Muswell Hill Soup Kitchen",
            workingHours = mapOf(
                "Monday" to "7pm - 8pm",
                "Tuesday" to "12pm - 1pm"
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(MIN_ZOOM)
        map.setLatLngBoundsForCameraTarget(LONDON_BOUNDS)
        map.moveCamera(CameraUpdateFactory.newLatLng(LONDON_LAT_LONG))
        map.addMarker(MarkerOptions().position(LONDON_LAT_LONG).title(venue.name))
        map.setOnMarkerClickListener {
            onMarkerClicked()
            false
        }
        map.setOnMapClickListener { setBottomSheetVisibility(false) }
    }

    private fun onMarkerClicked() {
        workingHoursAdapter = WorkingHoursAdapter(venue.workingHours)
        bottomSheetView.findViewById<TextView>(R.id.title).text = venue.name
        bottomSheetView.findViewById<RecyclerView>(R.id.times).adapter = workingHoursAdapter
        setBottomSheetVisibility(true)
    }

    private fun setBottomSheetVisibility(isVisible: Boolean) {
        val updatedState =
            if (isVisible) BottomSheetBehavior.STATE_EXPANDED
            else BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = updatedState
    }

    companion object {
        const val MIN_ZOOM = 10f
        val LONDON_LAT_LONG = LatLng(51.5072, -0.12)
        val LONDON_BOUNDS = LatLngBounds(
            LatLng(51.2, -0.4),  // SW bounds
            LatLng(51.7, 0.2)    // NE bounds
        )
    }
}