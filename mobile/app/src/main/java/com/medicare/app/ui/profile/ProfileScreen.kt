package com.medicare.app.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medicare.app.ui.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {},
    authViewModel: AuthViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour") } }
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier.padding(pad).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("👤", style = MaterialTheme.typography.displayLarge)
            Spacer(Modifier.height(16.dp))
            Text("Mon Compte", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(32.dp))
            Button(
                onClick = { authViewModel.logout(); onLogout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) { Text("Se déconnecter") }
        }
    }
}