package com.example.treechart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val selectedFragmentLiveData = MutableLiveData<String>()
    val arrayLiveData = MutableLiveData<IntArray>()
    val tappedLiveData = MutableLiveData<Boolean>()

    fun setFragment(fragment: String) {
        selectedFragmentLiveData.postValue(fragment)
    }

    fun setArray(array: IntArray) {
        arrayLiveData.postValue(array)
    }

    fun setTapped(tapped: Boolean) {
        tappedLiveData.postValue(tapped)
    }
}