package com.kelvin.traveling.features.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.kelvin.traveling.R
import com.kelvin.traveling.databinding.FragmentBoardingOneBinding

class BoardingOneFragment : Fragment() {

	private lateinit var goToBoardingTwo: Button
	private lateinit var binding: FragmentBoardingOneBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentBoardingOneBinding.inflate(inflater, container, false)

		goToBoardingTwo = binding.btnNext

		goToBoardingTwo.setOnClickListener {
			findNavController().navigate(R.id.action_boardingOneFragment_to_boardingTwoFragment)
		}

		return binding.root
	}

}