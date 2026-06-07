package com.medicare.app.ui

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.medicare.app.ui.auth.LoginScreen
import com.medicare.app.ui.auth.RegisterScreen
import com.medicare.app.ui.home.HomeScreen
import com.medicare.app.ui.conditions.ConditionListScreen
import com.medicare.app.ui.conditions.ConditionDetailScreen
import com.medicare.app.ui.medications.AddMedicationScreen
import com.medicare.app.ui.medications.MedicationDetailScreen
import com.medicare.app.ui.doses.HistoryScreen
import com.medicare.app.ui.profile.ProfileScreen

@Composable
fun MediCareApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onSuccess = { navController.navigate("home") },
                onRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                onSuccess = { navController.popBackStack() },
                onLogin = { navController.popBackStack() }
            )
        }
        composable("home") {
            HomeScreen(
                onNavigateToConditions = { navController.navigate("conditions") },
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("conditions") {
            ConditionListScreen(
                onBack = { navController.popBackStack() },
                onConditionClick = { id -> navController.navigate("condition/$id") }
            )
        }
        composable("condition/{conditionId}") { entry ->
            val id = entry.arguments?.getString("conditionId")?.toLong() ?: 0L
            ConditionDetailScreen(
                conditionId = id,
                onBack = { navController.popBackStack() },
                onAddMedication = { cid -> navController.navigate("addMedication/$cid") },
                onMedicationClick = { mid -> navController.navigate("medication/$mid") }
            )
        }
        composable("addMedication/{conditionId}") { entry ->
            val id = entry.arguments?.getString("conditionId")?.toLong() ?: 0L
            AddMedicationScreen(
                conditionId = id,
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
        composable("medication/{medicationId}") { entry ->
            val id = entry.arguments?.getString("medicationId")?.toLong() ?: 0L
            MedicationDetailScreen(
                medicationId = id,
                onBack = { navController.popBackStack() }
            )
        }
        composable("history") {
            HistoryScreen(onBack = { navController.popBackStack() })
        }
        composable("profile") {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = { navController.navigate("login") { popUpTo(0) } }
            )
        }
    }
}