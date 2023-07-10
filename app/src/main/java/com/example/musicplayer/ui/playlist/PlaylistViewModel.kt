package com.example.musicplayer.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a Playlist Fragment"
    }
    val text: LiveData<String> = _text
}