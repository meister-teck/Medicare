package com.medicare.app.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medicare.app.ui.doses.DoseViewModel
import com.medicare.app.ui.theme.MedicalPrimary
import com.medicare.app.ui.theme.MedicalSecondary
import com.medicare.app.ui.theme.MedicalSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToConditions: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    doseViewModel: DoseViewModel = hiltViewModel()
) {
    val doses by doseViewModel.todayDoses.collectAsState()

    LaunchedEffect(Unit) { doseViewModel.loadTodayDoses() }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listOf(MedicalPrimary, MedicalSecondary)))
                    .padding(16.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("💊 MediCare", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    IconButton(onClick = onNavigateToProfile) {
                        Text("👤", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Text("🏠") }, label = { Text("Accueil") })
                NavigationBarItem(selected = false, onClick = onNavigateToConditions, icon = { Text("🩺") }, label = { Text("Maladies") })
                NavigationBarItem(selected = false, onClick = onNavigateToHistory, icon = { Text("📅") }, label = { Text("Historique") })
            }
        }
    ) { pad ->
        LazyColumn(modifier = Modifier.padding(pad).padding(16.dp)) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📋 Aujourd'hui", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text("${doses.size} prise(s) prévue(s)", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (doses.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                        Text("🎉 Aucune prise aujourd'hui !", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            items(doses) { dose ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (dose.taken) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(dose.medicationName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text("Prise ${dose.doseIndex} • ${dose.scheduledTime ?: ""}", style = MaterialTheme.typography.bodySmall)
                            Text(
                                if (dose.taken) "✅ Déjà pris" else "⏳ À prendre",
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (dose.taken) MedicalSuccess else MaterialTheme.colorScheme.error
                            )
                        }
                        if (!dose.taken) {
                            Button(
                                onClick = { doseViewModel.markAsTaken(dose.id) },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MedicalSuccess)
                            ) { Text("J'ai pris") }
                        }
                    }
                }
            }
        }
    }
}