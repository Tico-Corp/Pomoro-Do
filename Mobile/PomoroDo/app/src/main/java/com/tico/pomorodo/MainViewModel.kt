package com.tico.pomorodo

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
) : ViewModel() {

    val isNetworkConnected: StateFlow<Boolean> = networkMonitor.isNetworkConnected
}