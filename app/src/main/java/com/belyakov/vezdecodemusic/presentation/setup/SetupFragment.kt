package com.belyakov.vezdecodemusic.presentation.setup

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.presentation.setup.contracts.PickAudioContract
import com.belyakov.vezdecodemusic.presentation.setup.contracts.PickImageContract
import com.belyakov.vezdecodemusic.utils.launch
import com.belyakov.vezdecodemusic.utils.observe
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.f_setup.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel


class SetupFragment : Fragment(R.layout.f_setup) {

    private val viewModel: SetupViewModel by lifecycleScope.viewModel(this)

    private val pickPicture =
        registerForActivityResult(PickImageContract()) { uri: Uri? ->
                onImagePicked(uri)
        }
    private val pickAudio =
        registerForActivityResult(PickAudioContract()) { uri: Uri? ->
                onAudioPicked(uri)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        observeViewModel()
    }

    private fun observeViewModel() {
        nextButton.isEnabled = true
//        observe(viewModel.setupDone) {
//            nextButton.isEnabled = it
//        }
        observe(viewModel.audioInfo) {
            loadAudio.loadAudioInfo(it)
        }
    }

    private fun handleView() {
        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_redactFragment)
        }
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        loadImage.setOnClickListener {
            pickPicture.launch()
        }
        loadAudio.onLoadClickedListener = {
            pickAudio.launch()
        }
        inputName.textChangeListener = {
            viewModel.setPodcastName(it)
        }
        inputDescription.textChangeListener = {
            viewModel.setPodcastDescription(it)
        }
        checkContent.setOnCheckedChangeListener { _, checked ->
            viewModel.setPodcastTagContent(checked)
        }
        checkExport.setOnCheckedChangeListener { _, checked ->
            viewModel.setPodcastTagExport(checked)
        }
        checkTrailer.setOnCheckedChangeListener { _, checked ->
            viewModel.setPodcastTagTrailer(checked)
        }
    }

    private fun onImagePicked(uri: Uri?) {
        uri?.let {
            viewModel.setPodcastImage(it)
            loadImage.loadPhoto(it)
        }
    }

    private fun onAudioPicked(uri: Uri?) {
        uri?.let {
            viewModel.setPodcastAudio(it)
        }
    }
}