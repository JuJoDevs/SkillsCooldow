package com.jujodevs.skillscooldown.skills.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class PreferencesService @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val TURNO_KEY = intPreferencesKey("turno")

    fun getTurno() = dataStore.data
        .map { preferences ->
            preferences[TURNO_KEY] ?: 1
        }

    suspend fun setTurno(turno: Int) = dataStore.edit { preferences ->
        preferences[TURNO_KEY] = turno
    }

    suspend fun incrementTurno() = dataStore.edit { preferences ->
        preferences[TURNO_KEY] = (preferences[TURNO_KEY] ?: 1) + 1
    }

}