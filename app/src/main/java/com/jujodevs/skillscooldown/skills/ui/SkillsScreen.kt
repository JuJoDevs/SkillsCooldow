package com.jujodevs.skillscooldown.skills.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.jujodevs.skillscooldown.model.Routes
import com.jujodevs.skillscooldown.skills.ui.model.Skill


@Composable
fun SkillsScreen(viewModel: SkillsViewModel, navHostController: NavHostController, edit: Boolean) {
    val turno by viewModel.turno.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column() {
            Row(Modifier.fillMaxWidth()) {
                Top("Cooldown Skills", edit, navHostController)
            }
            if (!edit) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Turno ${turno.toString()}",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)) {
                Body(Modifier, viewModel, edit)
            }
        }
        if (edit == true) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FabNewSkill(viewModel)
            }
        } else {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                FabNextTurn(viewModel)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                FabResetTurn(viewModel)
            }
        }
    }
}


@Composable
fun Body(modifier: Modifier, viewModel: SkillsViewModel, edit: Boolean?) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SkillsUiState>(
        initialValue = SkillsUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            viewModel.uiState.collect{ value = it}
        }
    }

    when(uiState){
        is SkillsUiState.Error -> {}
        SkillsUiState.Loading -> { Column(Modifier.fillMaxWidth()) {
            CircularProgressIndicator(modifier.align(Alignment.CenterHorizontally))
        } }
        is SkillsUiState.Success -> Skills((uiState as SkillsUiState.Success).skills, modifier, viewModel, edit)
    }


}

@Composable
fun Skills(skills: List<Skill>, modifier: Modifier, viewModel: SkillsViewModel, edit: Boolean?) {
    LazyColumn {
        items(skills, key = { it.id }) { skill ->
            SkillItem(skill, modifier, viewModel, edit)
        }
    }

}

@Composable
fun FabNewSkill(viewModel: SkillsViewModel) {
    FloatingActionButton(onClick = {
        viewModel.insertSkill()
    }) {
        Icon(Icons.Default.Add, contentDescription = "Nueva Skill", tint = Color.White)
    }
}

@Composable
fun FabNextTurn(viewModel: SkillsViewModel) {
    FloatingActionButton(onClick = {
        viewModel.incrementTurno()
    }) {
        Icon(Icons.Default.PlayArrow, contentDescription = "Siguiente turno", tint = Color.White)
    }
}

@Composable
fun FabResetTurn(viewModel: SkillsViewModel) {
    FloatingActionButton(onClick = {
        viewModel.resetTurno()
    }) {
        Icon(Icons.Default.Refresh, contentDescription = "Resetear turno", tint = Color.White)
    }
}


@Composable
fun SkillItem(skill: Skill, modifier: Modifier, viewModel: SkillsViewModel, edit: Boolean?){

    if (edit == true){
        val id by rememberSaveable {
            mutableStateOf(skill.id)
        }
        var name by rememberSaveable {
            mutableStateOf(skill.name)
        }
        var cooldown by rememberSaveable {
            mutableStateOf(skill.cooldown.toString())
        }
            Card(
                modifier
                    .fillMaxWidth()
                    .padding(16.dp, 8.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                viewModel.deleteSkill(skill)
                            }
                        )
                    },
                elevation = 8.dp) {
                Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    TextField(value = name,
                        modifier = modifier
                            .padding(8.dp)
                            .weight(7f),
                        textStyle = TextStyle.Default.copy(fontWeight = FontWeight.Bold),
                        onValueChange = {
                            try {
                                name = it
                                viewModel.updateSkill(Skill(id, name, cooldown.toInt(), 0))
                            }catch (_: Exception){}
                        }
                    )
                    Text(text = "|", modifier = modifier.padding(4.dp), fontWeight = FontWeight.Bold)
                    TextField(value = cooldown,
                        modifier = modifier
                            .padding(8.dp)
                            .weight(2f),
                        textStyle = TextStyle.Default.copy(fontWeight = FontWeight.Bold),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        maxLines = 1,
                        onValueChange = {
                            try {
                                cooldown = it
                                var value = if (it == "") {0} else {it.toInt()}
                                if (value > 999) { value = 999} else if (value < 0) {value = 0}
                                viewModel.updateSkill(Skill(id, name, value, 0))
                            }catch (_: Exception){}
                        }
                    )
                }
            }
    }else{
        val id = skill.id
        val name = skill.name
        val cooldown = skill.cooldown.toString()
        var cooldownCount = skill.cooldownCount
        Card(
            modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
                .clickable {
                    if (cooldownCount <= 0) {
                        cooldownCount = cooldown.toInt()
                        viewModel.updateSkill(Skill(id, name, cooldown.toInt(), cooldownCount))
                    }
                }
                , elevation = 8.dp) {
            Row(
                modifier
                    .fillMaxWidth()
                    .background(
                        if (cooldownCount > 0) {
                            MaterialTheme.colors.secondaryVariant
                        } else {
                            DefaultTintColor
                        }
                    ), verticalAlignment = Alignment.CenterVertically) {
                Text(text = name, modifier = modifier
                    .padding(8.dp)
                    .weight(1f), fontWeight = FontWeight.Bold)
                if (cooldownCount > 0){
                    Text(text = "|", modifier = modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                    Text(text = cooldownCount.toString(), modifier = modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun Top(
    title: String,
    edit: Boolean,
    navHostController: NavHostController
) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp, 0.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White)
        }
        Column(Modifier.weight(1f)){}
        Column(Modifier.padding(12.dp, 0.dp)) {
            if (!edit){
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Set Skills",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier.clickable {
                        //viewModel.setEdit(true)
                        navHostController.navigate(Routes.EditSkills.route)
                    }
                )
            }
        }
    }
}
