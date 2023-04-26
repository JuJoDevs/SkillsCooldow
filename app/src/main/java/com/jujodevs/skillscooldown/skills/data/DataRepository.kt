package com.jujodevs.skillscooldown.skills.data

import com.jujodevs.skillscooldown.skills.data.database.SkillsDatabaseService
import com.jujodevs.skillscooldown.skills.data.preferences.PreferencesService
import com.jujodevs.skillscooldown.skills.ui.model.Skill
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DataRepository @Inject constructor(private val skillsDatabaseService: SkillsDatabaseService, private val preferencesService: PreferencesService) {

    fun getSkills(): Flow<List<Skill>> =  skillsDatabaseService.getSkills()

    suspend fun insertSkill(skill: Skill){
        skillsDatabaseService.insertSkill(skill)
    }

    suspend fun setSkill(skill: Skill){
        skillsDatabaseService.setSkill(skill)
    }

    suspend fun cooldownDecrement(){
        skillsDatabaseService.cooldownDecrement()
    }

    suspend fun cooldownReset(){
        skillsDatabaseService.cooldownReset()
    }

    suspend fun deleteSkill(skill: Skill){
        skillsDatabaseService.deleteSkill(skill)
    }

    suspend fun getTurno(): Int = preferencesService.getTurno().firstOrNull() ?: 0

    suspend fun setTurno(turno: Int){
        preferencesService.setTurno(turno)
    }

    suspend fun incrementTurno(){
        preferencesService.incrementTurno()
    }

}