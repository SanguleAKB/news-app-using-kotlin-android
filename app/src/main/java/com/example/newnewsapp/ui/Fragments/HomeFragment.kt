package com.example.newnewsapp.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newnewsapp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find each CardView by its ID
        val cardViewThree = view.findViewById<CardView>(R.id.viewCardThree)
        val cardViewFour = view.findViewById<CardView>(R.id.viewCardFour)
        val cardViewFive = view.findViewById<CardView>(R.id.viewCardFive)
        val cardViewSix = view.findViewById<CardView>(R.id.viewCardSix)
        val cardViewSeven = view.findViewById<CardView>(R.id.viewCardSeven)
        val cardViewEight = view.findViewById<CardView>(R.id.viewCardEight)

        cardViewThree.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_healthFragment)
        }

        cardViewFour.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_entertainmentFragment)
        }

        cardViewFive.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_sportFragment)

        }

        cardViewSix.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_scienceFragment)
        }

        cardViewSeven.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_businessFragment)

        }

        cardViewEight.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_technologyFragment)
        }

        return view
    }
}



