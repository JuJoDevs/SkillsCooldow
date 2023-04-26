package com.jujodevs.skillscooldown.core

import android.content.Context
import androidx.room.Room
import com.jujodevs.skillscooldown.skills.data.database.SkillsCooldownDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SKILlCOOLDOWN_DATABASE_NAME = "skillcooldown_database"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context):SkillsCooldownDatabase {
        return Room.databaseBuilder(
            context,
            SkillsCooldownDatabase::class.java,
            SKILlCOOLDOWN_DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideSkillDao(skillsCooldownDatabase: SkillsCooldownDatabase) = skillsCooldownDatabase.getSkillDao()
}