package com.example.gestor_siniestros.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gestor_siniestros.R
import com.example.gestor_siniestros.databinding.FragmentListBinding

class ListFragment: Fragment() {

    private lateinit var binding: FragmentListBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnNavegar1.setOnClickListener {
                findNavController().navigate(R.id.action_secondFragment_to_mainFragment)
        }
    }
}