package com.jujodevs.skillscooldown.skills.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jujodevs.skillscooldown.skills.data.database.dao.SkillDao
import com.jujodevs.skillscooldown.skills.data.database.entities.SkillEntity

@Database(
    version = 1,
    entities = [
        SkillEntity::class
        ],
    exportSchema = true,
    autoMigrations = []
)
abstract class SkillsCooldownDatabase: RoomDatabase() {

    abstract fun getSkillDao(): SkillDao

}