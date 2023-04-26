package com.jujodevs.skillscooldown.skills.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jujodevs.skillscooldown.skills.ui.model.Skill

@Entity(tableName = "skill_table")
data class SkillEntity (
    @PrimaryKey(true)
    val id: Int = 0,
    val name: String,
    val cooldown: Int,
    val cooldownCount: Int
)

fun Skill.toDatabase() = SkillEntity(id, name, cooldown, cooldownCount)