package com.tico.pomorodo.network

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isNetworkConnected: StateFlow<Boolean>
}