package com.jujodevs.skillscooldown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jujodevs.skillscooldown.model.Routes
import com.jujodevs.skillscooldown.skills.ui.SkillsScreen
import com.jujodevs.skillscooldown.skills.ui.SkillsViewModel
import com.jujodevs.skillscooldown.ui.theme.SkillsCooldownTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<SkillsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkillsCooldownTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Routes.Skills.route){
                        composable(Routes.Skills.route) { SkillsScreen(viewModel, navController, false) }
                        composable(Routes.EditSkills.route) { SkillsScreen(viewModel, navController, true) }
                    }
                }
            }
        }
    }
}