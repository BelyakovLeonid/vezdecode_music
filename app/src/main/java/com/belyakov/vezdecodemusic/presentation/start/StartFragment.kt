package com.belyakov.vezdecodemusic.presentation.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import kotlinx.android.synthetic.main.f_start.*

class StartFragment: Fragment(R.layout.f_start){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
    }

    private fun handleView() {
        button.setOnClickListener {
            findNavController().navigate(R.id.action_to_setupFragment)
        }
    }
}