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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.juansuarez_p1_ap2.data.local.entities.TareaEntity

@Composable
fun TareaListScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    goToSystem: (Int) -> Unit,
    createSystem: () -> Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SystemListBodyScreen(
        uiState = uiState,
        goToSystem,
        createSystem
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemListBodyScreen(
    uiState: TareaUiState,
    goToSystem: (Int) -> Unit,
    createSystem: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Sistemas",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createSystem) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nuevo ticket")
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
                SystemRow(
                    it,
                    goToSystem,
                    createSystem
                )
            }
        }
    }
}

@Composable
fun SystemRow(
    it: TareaEntity,
    goToSystem: (Int) -> Unit,
    createSystem: () -> Unit
){
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
                    goToSystem(it.tareaId ?: 0)
                },
                horizontalArrangement = TODO(),
                content = TODO()
            )
        }
    }
}