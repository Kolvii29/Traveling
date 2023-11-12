package com.kelvin.traveling.features.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.kelvin.traveling.R
import com.kelvin.traveling.databinding.FragmentBoardingThreeBinding

class BoardingThreeFragment : Fragment() {

	private lateinit var goToLogInFragment: Button
	private lateinit var goToRegisterFragment: Button
	private lateinit var binding: FragmentBoardingThreeBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentBoardingThreeBinding.inflate(inflater, container, false)
		listeners()

		return binding.root
	}

	private fun listeners(){
		goToLogInFragment = binding.btnSignin
		goToRegisterFragment = binding.btnSignup

		goToLogInFragment.setOnClickListener {
			//indNavController().navigate(R.id.action_boardingThreeFragment_to_logInFragment)
		}

		goToRegisterFragment.setOnClickListener {
			//findNavController().navigate(R.id.action_boardingThreeFragment_to_registerFragment)
		}
	}

}