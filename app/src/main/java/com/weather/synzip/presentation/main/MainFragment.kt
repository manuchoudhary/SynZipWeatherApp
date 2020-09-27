package com.weather.synzip.presentation.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.weather.synzip.R
import com.weather.synzip.databinding.FragmentMainBinding
import com.weather.synzip.presentation.core.InternetCheck

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = PagerAdapter(childFragmentManager, lifecycle)
        binding.places.adapter = adapter

        val indicator = binding.indicator
        indicator.setViewPager(binding.places)
        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.viewState.observe(viewLifecycleOwner) {
            val data = it.data
            if (data.isNotEmpty()) {
                adapter.setPlaces(data)
                binding.places.visibility = View.VISIBLE
                binding.emptyScreen.visibility = View.GONE
            } else {
                binding.places.visibility = View.GONE
                binding.emptyScreen.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_place -> {
                InternetCheck(object : InternetCheck.Consumer {
                    override fun accept(internet: Boolean?) {
                        if (internet!!) {
                            findNavController().navigate(R.id.placesFragment)
                        } else {
                            Snackbar.make(binding.root, R.string.something_went_wrong, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}