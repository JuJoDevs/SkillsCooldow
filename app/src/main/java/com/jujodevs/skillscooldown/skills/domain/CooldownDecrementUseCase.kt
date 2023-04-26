package com.jujodevs.skillscooldown.skills.domain

import com.jujodevs.skillscooldown.skills.data.DataRepository
import javax.inject.Inject

class CooldownDecrementUseCase @Inject constructor(private val dataRepository: DataRepository) {

    suspend operator fun invoke() = dataRepository.cooldownDecrement()

}