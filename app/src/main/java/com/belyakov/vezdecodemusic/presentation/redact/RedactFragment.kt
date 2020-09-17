package com.belyakov.vezdecodemusic.presentation.redact

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.utils.observe
import kotlinx.android.synthetic.main.f_redact.*
import kotlinx.android.synthetic.main.f_setup.back
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class RedactFragment : Fragment(R.layout.f_redact) {

    private val viewModel: RedactViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        handleView()
    }

    private fun observeViewModel() {
        observe(viewModel.selectedMusicName) {
            audioControl.setTopTextVisible(it != null)
            it?.let {
                audioControl.setTopText(it)
            }
        }
    }

    private fun handleView() {
        timecodesHolder.fragmentManager = childFragmentManager
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        addTimecodeButton.setOnClickListener {
            timecodesHolder.addTimeCode()
        }
        nextButton.setOnClickListener {
            viewModel.setTimecodes(timecodesHolder.getTimecodes())
            viewModel.onLeaveScreen()
            findNavController().navigate(R.id.action_to_endSetupFragment)
        }
        buttonPlay.onPlayListener = {
            viewModel.onPlayClicked()
        }
        buttonPlay.onPauseListener = {
            viewModel.onPauseClicked()
        }
        buttonMusic.onCheckedListener = {
            audioControl.setTopTextVisible(it)
            if (it) {
                findNavController().navigate(R.id.action_redactFragment_to_chooseMusicFragment)
            }
        }
        buttonGrove.onCheckedListener = {
            audioControl.setBottomText("Появление: вкл")
            audioControl.setBottomTextVisible(it)
        }
        buttonReduce.onCheckedListener = {
            audioControl.setBottomText("Угасание: вкл") //todo bad logic
            audioControl.setBottomTextVisible(it)
        }
    }
}