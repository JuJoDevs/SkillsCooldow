package com.jujodevs.skillscooldown.skills.domain

import com.jujodevs.skillscooldown.skills.data.DataRepository
import javax.inject.Inject

class GetSkillsUseCase @Inject constructor(private val dataRepository: DataRepository) {

    operator fun invoke() = dataRepository.getSkills()

}