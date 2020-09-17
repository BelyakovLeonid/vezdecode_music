package com.belyakov.vezdecodemusic.presentation.music

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.belyakov.vezdecodemusic.R
import com.belyakov.vezdecodemusic.presentation.music.adapters.MusicAdapter
import com.belyakov.vezdecodemusic.utils.observe
import kotlinx.android.synthetic.main.f_choose_music.*
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class ChooseMusicFragment : Fragment(R.layout.f_choose_music) {

    private lateinit var adapter: MusicAdapter
    private val viewModel: ChooseMusicViewModel by lifecycleScope.viewModel(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleView()
        observeViewModel()
    }

    private fun observeViewModel() {
        observe(viewModel.musicDataSet) {
            adapter.submitData(it)
        }
    }

    private fun handleView() {
        setupRecyclerView()
        back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        adapter = MusicAdapter{
            viewModel.onMusicSelected(it)
            findNavController().navigateUp()
        }
        musicList.adapter = adapter
    }
}