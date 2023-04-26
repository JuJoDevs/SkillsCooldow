package com.jujodevs.skillscooldown.model

sealed class Routes(val route: String){
    object Skills: Routes("PlaySkills")
    object EditSkills: Routes("EditSkills")
}
