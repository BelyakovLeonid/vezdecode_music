package com.belyakov.vezdecodemusic.presentation.endsetup

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.presentation.endsetup.adapters.TimecodesAdapter
import com.belyakov.vezdecodemusic.utils.observe
import com.belyakov.vezdecodemusic.utils.roundedTransform
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.f_end_setup.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class EndSetupFragment : Fragment(R.layout.f_end_setup) {

    private val viewModel: EndSetupViewModel by lifecycleScope.viewModel(this)

    private lateinit var adapter: TimecodesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        observeViewModel()
    }

    private fun observeViewModel() {
        observe(viewModel.podcastName) {
            name.text = it
        }
        observe(viewModel.podcastDescription) {
            description.text = it
        }
        observe(viewModel.podcastImage) {
            setImage(it)
        }
        observe(viewModel.podcastTimecodes) {
            adapter.submitList(it)
        }
    }

    private fun handleView() {
        setupRecyclerView()
        back.setOnClickListener {
            findNavController().navigateUp()
        }
        publishButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_finishFragment)
        }
        playButton.onPlayListener = {
            viewModel.onPlayClicked()
        }
        playButton.onPauseListener = {
            viewModel.onPauseClicked()
        }
    }

    private fun setupRecyclerView() {
        adapter = TimecodesAdapter()
        timecodesList.adapter = adapter
    }

    private fun setImage(uri: Uri?) {
        Glide
            .with(requireContext())
            .load(uri)
            .roundedTransform(requireContext())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageContainer)
    }
}
