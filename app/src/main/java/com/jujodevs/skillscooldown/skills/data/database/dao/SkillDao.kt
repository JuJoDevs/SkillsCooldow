package com.jujodevs.skillscooldown.skills.data.database.dao

import androidx.room.*
import com.jujodevs.skillscooldown.skills.data.database.entities.SkillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {

    @Query("SELECT * FROM skill_table")
    fun getSkills(): Flow<List<SkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSkill(skillEntity: SkillEntity)

    @Update
    fun setSkill(skillEntity: SkillEntity)

    @Query("UPDATE skill_table SET cooldownCount = cooldownCount - 1 WHERE cooldownCount > 0")
    fun cooldownDecrement()

    @Query("UPDATE skill_table SET cooldownCount = 0")
    fun cooldownReset()

    @Delete
    fun deleteSkill(skillEntity: SkillEntity)

}