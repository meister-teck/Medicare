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
import com.medicare.app.ui.doses.DoseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationDetailScreen(
    medicationId: Long,
    onBack: () -> Unit = {},
    doseViewModel: DoseViewModel = hiltViewModel()
) {
    val history by doseViewModel.historyDoses.collectAsState()

    LaunchedEffect(medicationId) { doseViewModel.loadHistory(medicationId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Planning") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        LazyColumn(modifier = Modifier.padding(pad).padding(16.dp)) {
            items(history) { dose ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Jour ${dose.doseDate} - Prise ${dose.doseIndex}")
                        Text("Prévu: ${dose.scheduledTime ?: "N/A"}")
                        Text(if (dose.taken) "✅ Pris à ${dose.takenTimestamp}" else "❌ Non pris")
                    }
                }
            }
        }
    }
}