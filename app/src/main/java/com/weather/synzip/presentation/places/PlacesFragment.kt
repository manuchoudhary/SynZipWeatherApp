package com.weather.synzip.presentation.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.weather.synzip.R
import com.weather.synzip.databinding.FragmentPlacesBinding
import com.weather.synzip.presentation.core.hideKeyboard
import com.weather.synzip.presentation.core.onTextChange
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    private val placesViewModel: PlacesViewModel by viewModels()

    private lateinit var binding: FragmentPlacesBinding
    private lateinit var placesAdapter: PlacesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlacesBinding.inflate(inflater, container, false)
        placesAdapter = PlacesAdapter()
        binding.rvPlaces.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = placesAdapter
        }

        placesAdapter.onClick = {
            placesViewModel.storePlace(it)
            context?.hideKeyboard(view!!)
            findNavController().navigate(R.id.mainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        placesViewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            if (state.error != null) {
                // TODO: Enable reload here
                Snackbar.make(binding.root, R.string.something_went_wrong, Snackbar.LENGTH_SHORT)
                    .show()
            } else if (state.data != null) {
                placesAdapter.submitList(state.data)
            }
        })
        placesViewModel.searchPlaces(binding.tiSearchText.onTextChange())
    }
}