package com.jujodevs.skillscooldown.skills.domain

import com.jujodevs.skillscooldown.skills.data.DataRepository
import com.jujodevs.skillscooldown.skills.ui.model.Skill
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateSkillUseCase @Inject constructor(private val dataRepository: DataRepository) {

    suspend operator fun invoke(skill: Skill){
        withContext(Dispatchers.IO){
            dataRepository.setSkill(skill)
        }
    }

}