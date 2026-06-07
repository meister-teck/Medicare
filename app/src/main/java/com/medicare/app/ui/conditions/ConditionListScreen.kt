package com.medicare.app.ui.conditions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medicare.app.ui.theme.MedicalPrimary
import com.medicare.app.ui.theme.MedicalSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionListScreen(
    onBack: () -> Unit = {},
    onConditionClick: (Long) -> Unit = {},
    viewModel: ConditionViewModel = hiltViewModel()
) {
    val conditions by viewModel.conditions.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("CHRONIC") }

    LaunchedEffect(Unit) { viewModel.loadConditions() }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nouvelle maladie", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(newName, { newName = it }, label = { Text("Nom") })
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selectedType == "CHRONIC", { selectedType = "CHRONIC" }); Text("Chronique")
                        Spacer(Modifier.width(8.dp))
                        RadioButton(selectedType == "ACUTE", { selectedType = "ACUTE" }); Text("Aiguë")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { if (newName.isNotBlank()) { viewModel.addCondition(selectedType, newName); showDialog = false } }) { Text("Ajouter") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Annuler") } }
        )
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.fillMaxWidth().background(Brush.horizontalGradient(listOf(MedicalPrimary, MedicalSecondary))).padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour", tint = MaterialTheme.colorScheme.onPrimary) }
                    Text("Mes Maladies", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
    ) { pad ->
        Column(modifier = Modifier.padding(pad)) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MedicalPrimary)
            ) { Text("+ Ajouter une maladie") }
            LazyColumn {
                items(conditions) { c ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = { onConditionClick(c.id) }
                    ) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(c.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                                Text(if (c.type == "CHRONIC") "🔄 Chronique" else "⚡ Aiguë")
                            }
                            IconButton(onClick = { viewModel.deleteCondition(c.id) }) { Text("🗑") }
                        }
                    }
                }
            }
        }
    }
}