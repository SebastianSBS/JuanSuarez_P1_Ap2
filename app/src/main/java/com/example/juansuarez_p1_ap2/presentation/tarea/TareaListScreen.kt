package com.example.juansuarez_p1_ap2.presentation.tarea

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun TareaListScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TareaListBodyScreen(
        uiState = uiState,
        goToTarea,
        createTarea
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaListBodyScreen(
    uiState: TareaUiState,
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Tareas",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createTarea) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nueva tarea")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 80.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(uiState.tareas) {
                TareaRow(
                    it,
                    goToTarea,
                    createTarea
                )
            }
        }
    }
}

@Composable
fun TareaRow(
    it: TareaEntity,
    goToTarea: (Int) -> Unit,
    createTarea: () -> Unit
){

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Card (
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    goToTarea(it.tareaId ?: 0)
                }
            ) {
                Text(
                    text = "ID: ${it.tareaId}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Asignación: ${it.descripcion}",
                style = MaterialTheme.typography.titleMedium
            )
            CountDownTimer(deadlineMillis = it.tiempo!!.toLong())
        }
    }
}

@Composable
fun CountDownTimer(deadlineMillis: Long){
    var timeLeftMillis by remember { mutableStateOf(deadlineMillis - System.currentTimeMillis()) }

    LaunchedEffect (key1 = deadlineMillis) {
        while (timeLeftMillis > 0){
            delay(1000L)
            timeLeftMillis = deadlineMillis - System.currentTimeMillis()
        }
    }

    val days = TimeUnit.MILLISECONDS.toDays(timeLeftMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(timeLeftMillis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftMillis) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis) % 60

    Text(
        text = when {
            days > 0 -> "La tarea vence en $days dias, $hours h"
            hours > 0 -> "La tarea vence en $hours h, $minutes min"
            minutes > 0 -> "La tarea vence en $minutes min, $seconds s"
            seconds > 0 -> "La tarea vence en $seconds s"
            else -> "La tarea ha caducado.."
        }
    )
}