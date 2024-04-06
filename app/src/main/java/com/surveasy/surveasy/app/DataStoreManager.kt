package com.surveasy.surveasy.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("REFRESH_TOKEN")
        private val AUTO_LOGIN_KEY = booleanPreferencesKey("AUTO_LOGIN")
        private val TUTORIAL_KEY = booleanPreferencesKey("TUTORIAL_KEY")
    }

    fun getTutorial(): Flow<Boolean?> {
        return dataStore.data.map { prefs ->
            prefs[TUTORIAL_KEY] ?: false
        }
    }

    suspend fun putTutorial() {
        dataStore.edit { prefs ->
            prefs[TUTORIAL_KEY] = true
        }
    }

    fun getAccessToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY] ?: ""
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY] ?: ""
        }
    }

    fun getAutoLogin(): Flow<Boolean?> {
        return dataStore.data.map { prefs ->
            prefs[AUTO_LOGIN_KEY] ?: false
        }
    }

    suspend fun putAccessToken(token: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    suspend fun putRefreshToken(token: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun putAutoLogin(isAuto: Boolean) {
        dataStore.edit { prefs ->
            prefs[AUTO_LOGIN_KEY] = isAuto
        }
    }

    suspend fun deleteAccessToken() {
        dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }

    suspend fun deleteRefreshToken() {
        dataStore.edit { prefs ->
            prefs.remove(REFRESH_TOKEN_KEY)
        }
    }

    suspend fun deleteAutoLogin() {
        dataStore.edit { prefs ->
            prefs.remove(AUTO_LOGIN_KEY)
        }
    }

}