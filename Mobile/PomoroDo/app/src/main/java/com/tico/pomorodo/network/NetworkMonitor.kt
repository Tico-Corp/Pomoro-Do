package com.tico.pomorodo.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun observeIsNetworkConnected(): Flow<Boolean>
    suspend fun isNetworkConnected(): Boolean
}