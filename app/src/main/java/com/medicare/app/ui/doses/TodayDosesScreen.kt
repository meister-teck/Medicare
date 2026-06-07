package com.medicare.app.ui.doses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayDosesScreen(
    onBack: () -> Unit = {},
    viewModel: DoseViewModel = hiltViewModel()
) {
    val doses by viewModel.todayDoses.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTodayDoses() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aujourd'hui") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        LazyColumn(modifier = Modifier.padding(pad)) {
            items(doses) { dose ->
                Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("${dose.medicationName} - Prise ${dose.doseIndex}", style = MaterialTheme.typography.titleMedium)
                        Text("Prévu: ${dose.scheduledTime ?: "N/A"}")
                        Text(if (dose.taken) "✅ Pris à ${dose.takenTimestamp}" else "❌ Non pris")
                        if (!dose.taken) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.markAsTaken(dose.id) }) { Text("J'ai pris") }
                        }
                    }
                }
            }
        }
    }
}