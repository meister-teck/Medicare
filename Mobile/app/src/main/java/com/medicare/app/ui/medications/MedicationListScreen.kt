package com.medicare.app.ui.medications

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
fun MedicationListScreen(
    conditionId: Long,
    onBack: () -> Unit = {},
    onAddMedication: (Long) -> Unit = {},
    viewModel: MedicationViewModel = hiltViewModel()
) {
    val medications by viewModel.medications.collectAsState()

    LaunchedEffect(conditionId) { viewModel.loadMedications(conditionId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Médicaments") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        Column(modifier = Modifier.padding(pad)) {
            Button(
                onClick = { onAddMedication(conditionId) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) { Text("+ Ajouter un médicament") }
            LazyColumn {
                items(medications) { med ->
                    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(med.name, style = MaterialTheme.typography.titleMedium)
                            Text("${med.dosesPerDay} prise(s)/jour - ${med.durationDays} jours")
                            Text("Début: ${med.startDate}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.deleteMedication(med.id) }) { Text("Supprimer") }
                        }
                    }
                }
            }
        }
    }
}