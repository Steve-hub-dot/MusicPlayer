package com.example.musicplayer.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {

    private lateinit var playlistViewModel: PlaylistViewModel
    private var _binding: FragmentPlaylistBinding?= null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        playlistViewModel =
            ViewModelProvider(this)[PlaylistViewModel::class.java]

        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        playlistViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}