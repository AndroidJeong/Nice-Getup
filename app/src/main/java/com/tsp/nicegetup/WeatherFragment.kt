package com.tsp.nicegetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

import com.tsp.nicegetup.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {


    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var WeatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,container:ViewGroup?,savedstate:Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupSpinner() {
        val spinner: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_array,R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}