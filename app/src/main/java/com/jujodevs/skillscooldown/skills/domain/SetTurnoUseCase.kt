package com.jujodevs.skillscooldown.skills.domain

import com.jujodevs.skillscooldown.skills.data.DataRepository
import javax.inject.Inject

class SetTurnoUseCase @Inject constructor(private val dataRepository: DataRepository) {

    suspend operator fun invoke(turno: Int){
        dataRepository.setTurno(turno)
    }

}