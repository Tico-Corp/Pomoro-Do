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
        sharedPreferences.edit().putString(ID_TOKEN, token).apply()
    }

    fun getIdToken(): String? {
        return sharedPreferences.getString(ID_TOKEN, null)
    }

    fun clearIdToken() {
        sharedPreferences.edit().remove(ID_TOKEN).apply()
    }

    fun isAccessToken(): Boolean = getAccessToken() != null

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    fun clearAccessToken() {
        sharedPreferences.edit().remove(ACCESS_TOKEN).apply()
    }

    companion object {
        private const val FILE_NAME = "encrypted_perf_file"
        private const val ID_TOKEN = "id_token"
        private const val ACCESS_TOKEN = "access_token"
    }
}