package com.example.storyapp.ui.home.ui.map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.R
import com.example.storyapp.ui.main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {


    private val callback = OnMapReadyCallback { googleMap ->

        try {
            val success =
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }

        val mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        mapsViewModel.fetch(MainViewModel.API_KEY)

        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true

        mapsViewModel.stories.observe(viewLifecycleOwner) {listStory ->
            listStory.forEachIndexed { index, story ->
                val obj = LatLng(story.lat!!, story.lon!!)
                googleMap.addMarker(MarkerOptions().position(obj).title(story.name))
            }
        }

        val indonesiaLatLng = LatLng(0.7893, 113.9213)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLatLng, 3f))


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(callback)
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}