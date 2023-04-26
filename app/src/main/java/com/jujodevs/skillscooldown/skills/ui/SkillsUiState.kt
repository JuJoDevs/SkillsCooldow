package com.jujodevs.skillscooldown.skills.ui

import com.jujodevs.skillscooldown.skills.ui.model.Skill


sealed interface SkillsUiState {
    object Loading: SkillsUiState
    data class Error(val throwable: Throwable): SkillsUiState
    data class Success(val skills:List<Skill>): SkillsUiState
}