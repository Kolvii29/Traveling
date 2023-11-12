package com.kelvin.traveling.features.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.kelvin.traveling.R
import com.kelvin.traveling.databinding.FragmentBoardingTwoBinding

class BoardingTwoFragment : Fragment() {

	private lateinit var goToBoardingThree: Button
	private lateinit var binding: FragmentBoardingTwoBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentBoardingTwoBinding.inflate(inflater, container, false)

		goToBoardingThree = binding.btnNext

		goToBoardingThree.setOnClickListener {
			findNavController().navigate(R.id.action_boardingTwoFragment_to_boardingThreeFragment)
		}

		return binding.root
	}

}