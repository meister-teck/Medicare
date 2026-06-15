package com.medicare.app.ui.medications

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medicare.app.data.api.dto.MedicationRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    conditionId: Long,
    onBack: () -> Unit = {},
    onSuccess: () -> Unit = {},
    viewModel: MedicationViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var dosesPerDay by remember { mutableStateOf("") }
    var durationDays by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajouter un médicament") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        Column(modifier = Modifier.padding(pad).padding(16.dp).fillMaxWidth()) {
            OutlinedTextField(name, { name = it }, label = { Text("Nom") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(dosesPerDay, { dosesPerDay = it }, label = { Text("Prises/jour") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(durationDays, { durationDays = it }, label = { Text("Durée (jours)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(startDate, { startDate = it }, label = { Text("Date début (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(hours, { hours = it }, label = { Text("Heures (ex: 08:00,13:00,20:00)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    val times = hours.split(",").map { it.trim() }
                    viewModel.addMedication(MedicationRequest(conditionId, name, dosesPerDay.toIntOrNull() ?: 1, durationDays.toIntOrNull() ?: 1, startDate, times))
                    onSuccess()
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Ajouter") }
        }
    }
}