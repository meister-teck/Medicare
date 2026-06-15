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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    onBack: () -> Unit = {},
    viewModel: DoseViewModel = hiltViewModel()
) {
    val allDoses by viewModel.historyDoses.collectAsState()

    var medicationNameInput by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)) }
    var searchPerformed by remember { mutableStateOf(false) }

    // Charger les doses au démarrage
    LaunchedEffect(Unit) { viewModel.loadHistory(1L) }

    // Filtrer selon les critères uniquement si recherche lancée
    val filteredDoses = if (searchPerformed) {
        allDoses.filter { dose ->
            dose.doseDate == dateInput &&
                    (medicationNameInput.isBlank() || dose.medicationName.contains(medicationNameInput, ignoreCase = true))
        }
    } else {
        emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historique") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        Column(modifier = Modifier.padding(pad).padding(16.dp)) {

            OutlinedTextField(
                value = medicationNameInput,
                onValueChange = { medicationNameInput = it },
                label = { Text("Nom du médicament") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dateInput,
                onValueChange = { dateInput = it },
                label = { Text("Date (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { searchPerformed = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔍 Rechercher")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (searchPerformed && filteredDoses.isEmpty()) {
                Text("Aucune prise trouvée pour ce médicament à cette date.")
            } else if (searchPerformed) {
                LazyColumn {
                    items(filteredDoses) { dose ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("${dose.medicationName} - Prise ${dose.doseIndex}", style = MaterialTheme.typography.titleSmall)
                                Text("Prévu: ${dose.scheduledTime ?: "N/A"}")
                                Text(if (dose.taken) "✅ Pris à ${dose.takenTimestamp}" else "❌ Non pris")
                                dose.notes?.let { Text("Note: $it", style = MaterialTheme.typography.bodySmall) }
                            }
                        }
                    }
                }
            } else {
                Text("Saisissez un nom et une date, puis appuyez sur Rechercher.")
            }
        }
    }
}