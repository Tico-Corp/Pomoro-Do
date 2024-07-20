package com.tico.pomorodo.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {

        val masterKey = MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()


        EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveIdToken(token: String) {
        sharedPreferences.edit().putString("id_token", token).apply()
    }

    fun getIdToken(): String {
        return sharedPreferences.getString("id_token", "") ?: ""
    }

    fun clearIdToken() {
        sharedPreferences.edit().remove("id_token").apply()
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString("access_token", token).apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString("access_token", "") ?: ""
    }

    fun clearAccessToken() {
        sharedPreferences.edit().remove("access_token").apply()
    }

    companion object {
        private const val FILE_NAME = "encrypted_perf_file"
    }
}