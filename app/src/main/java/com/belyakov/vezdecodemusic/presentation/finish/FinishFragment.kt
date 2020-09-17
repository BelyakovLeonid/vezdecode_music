package com.belyakov.vezdecodemusic.presentation.finish

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.presentation.music.ChooseMusicViewModel
import kotlinx.android.synthetic.main.f_finish.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class FinishFragment : Fragment(R.layout.f_finish) {

    private val viewModel: FinishViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
    }

    private fun handleView() {
        closeButton.setOnClickListener {
            findNavController().navigateUp()
        }
        button.setOnClickListener {
            share()
        }
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, viewModel.getTextForShare())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}