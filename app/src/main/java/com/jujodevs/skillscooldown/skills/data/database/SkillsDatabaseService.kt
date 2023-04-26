package com.jujodevs.skillscooldown.skills.data.database

import com.jujodevs.skillscooldown.skills.data.database.entities.toDatabase
import com.jujodevs.skillscooldown.skills.ui.model.Skill
import com.jujodevs.skillscooldown.skills.ui.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SkillsDatabaseService @Inject constructor(private val skillsCooldownDatabase: SkillsCooldownDatabase) {

    fun getSkills(): Flow<List<Skill>> = skillsCooldownDatabase.getSkillDao().getSkills().map { skills -> skills.map { it.toDomain() } }

    suspend fun insertSkill(skill: Skill){
        withContext(Dispatchers.IO) {
            skillsCooldownDatabase.getSkillDao().insertSkill(skill.toDatabase())
        }
    }

    suspend fun setSkill(skill: Skill){
        withContext(Dispatchers.IO) {
            skillsCooldownDatabase.getSkillDao().setSkill(skill.toDatabase())
        }
    }

    suspend fun cooldownDecrement(){
        withContext(Dispatchers.IO) {
            skillsCooldownDatabase.getSkillDao().cooldownDecrement()
        }
    }

    suspend fun cooldownReset(){
        withContext(Dispatchers.IO) {
            skillsCooldownDatabase.getSkillDao().cooldownReset()
        }
    }

    suspend fun deleteSkill(skill: Skill){
        withContext(Dispatchers.IO) {
            skillsCooldownDatabase.getSkillDao().deleteSkill(skill.toDatabase())
        }
    }

}