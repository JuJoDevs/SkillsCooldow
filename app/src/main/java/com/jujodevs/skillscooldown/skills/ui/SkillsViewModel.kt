package com.jujodevs.skillscooldown.skills.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jujodevs.skillscooldown.skills.domain.*
import com.jujodevs.skillscooldown.skills.ui.SkillsUiState.*
import com.jujodevs.skillscooldown.skills.ui.model.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@Suppress("unused")
@HiltViewModel
class SkillsViewModel @Inject constructor(
    getSkillsUseCase: GetSkillsUseCase,
    private val insertSkillUseCase: InsertSkillUseCase,
    private val updateSkillUseCase: UpdateSkillUseCase,
    private val deleteSkillUseCase: DeleteSkillUseCase,
    private val getTurnoUsesCase: GetTurnoUsesCase,
    private val cooldownDecrementUseCase: CooldownDecrementUseCase,
    private val cooldownResetUseCase: CooldownResetUseCase,
    private val setTurnoUseCase: SetTurnoUseCase,
    private val incrementUseCase: IncrementUseCase
): ViewModel() {

    val uiState: Flow<SkillsUiState> = getSkillsUseCase()
        .map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _turno = MutableLiveData<Int>()
    val turno:LiveData<Int> = _turno

    private val _edit = MutableLiveData<Boolean>()
    val edit:LiveData<Boolean> = _edit

    init {
        _edit.value = false
        _turno.value = runBlocking { getTurnoUsesCase.invoke() }
    }

    fun setEdit(edit: Boolean){
        _edit.value = edit
    }

    fun insertSkill(){
        viewModelScope.launch {
            val skill = Skill(0, "", 0, 0)
            insertSkillUseCase(skill)
        }
    }

    fun updateSkill(skill: Skill){
        viewModelScope.launch {
            updateSkillUseCase(skill)
        }
    }

    fun deleteSkill(skill: Skill){
        viewModelScope.launch {
            deleteSkillUseCase(skill)
        }
    }

    fun incrementTurno(){
        viewModelScope.launch {
            _turno.value = turno.value?.plus(1)
            incrementUseCase()
            cooldownDecrementUseCase()
        }
    }

    fun setTurno(turno: Int){
        viewModelScope.launch {
            _turno.value = turno
            setTurnoUseCase(turno)
        }
    }

    fun resetTurno(){
        viewModelScope.launch {
            _turno.value = 1
            setTurnoUseCase(1)
            cooldownResetUseCase()
        }
    }

}