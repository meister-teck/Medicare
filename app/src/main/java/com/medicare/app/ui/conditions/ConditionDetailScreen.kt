package com.medicare.app.ui.conditions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medicare.app.ui.medications.MedicationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionDetailScreen(
    conditionId: Long,
    onBack: () -> Unit = {},
    onAddMedication: (Long) -> Unit = {},
    onMedicationClick: (Long) -> Unit = {},
    medicationViewModel: MedicationViewModel = hiltViewModel(),
    conditionViewModel: ConditionViewModel = hiltViewModel()
) {
    val medications by medicationViewModel.medications.collectAsState()
    val conditions by conditionViewModel.conditions.collectAsState()
    val condition = conditions.find { it.id == conditionId }

    LaunchedEffect(conditionId) { medicationViewModel.loadMedications(conditionId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(condition?.name ?: "Condition") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        Column(modifier = Modifier.padding(pad).padding(16.dp)) {
            Text("Type: ${condition?.type ?: ""}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onAddMedication(conditionId) },
                modifier = Modifier.fillMaxWidth()
            ) { Text("+ Ajouter un médicament") }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Médicaments:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (medications.isEmpty()) {
                Text("Aucun médicament pour cette maladie.", style = MaterialTheme.typography.bodyMedium)
            } else {
                LazyColumn {
                    items(medications) { med ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            onClick = { onMedicationClick(med.id) }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(med.name, style = MaterialTheme.typography.titleSmall)
                                Text("${med.dosesPerDay} prises/jour - ${med.durationDays} jours")
                                Text("Depuis le ${med.startDate}")
                            }
                        }
                    }
                }
            }
        }
    }
}