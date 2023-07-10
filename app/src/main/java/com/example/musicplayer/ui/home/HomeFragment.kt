package com.example.musicplayer.ui.home

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var runnable: Runnable
    private var handler = Handler()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.songName
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer.release()
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val music = R.raw.sound_file1

        mediaPlayer = MediaPlayer.create(activity?.applicationContext, music)
        mediaPlayer.setVolume(0.5f,0.5f)
        binding.totalTimeLabel.text = mediaPlayer.duration.toString()

        binding.StartBtn.setOnClickListener {
            if(mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                binding.StartBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
            else {
                mediaPlayer.start()
                binding.StartBtn.setImageResource(R.drawable.ic_baseline_pause_24)
            }
        }
        binding.StopBtn.setOnClickListener {
            mediaPlayer.seekTo(0)
            mediaPlayer.pause()
            binding.StartBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }

        binding.statusBar.max = mediaPlayer.duration
        binding.statusBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress * 1000)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    mediaPlayer.seekTo(binding.statusBar.progress)
                }
            }
            )
        runnable = Runnable {
            binding.totalTimeLabel.text = createTimeLabel(mediaPlayer.duration)
            binding.ellapsedTimeLabel.text = createTimeLabel(mediaPlayer.currentPosition)
            binding.statusBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    fun createTimeLabel(time: Int): String {

        var minute = time / 1000 / 60
        var sec = time / 1000 % 60

        var timeLabel = "$minute:"
        if(sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }
    }
