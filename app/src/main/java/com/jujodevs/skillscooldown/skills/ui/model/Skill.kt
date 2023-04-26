package com.jujodevs.skillscooldown.skills.ui.model

import com.jujodevs.skillscooldown.skills.data.database.entities.SkillEntity

data class Skill(
    val id: Int,
    var name: String,
    var cooldown: Int,
    var cooldownCount: Int
)

fun SkillEntity.toDomain() = Skill(id, name, cooldown, cooldownCount)
