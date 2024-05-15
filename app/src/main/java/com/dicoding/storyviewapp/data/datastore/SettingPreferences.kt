package com.dicoding.storyviewapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.SettingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val SettingDataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return SettingDataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        SettingDataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}