package com.medicare.app.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onSuccess: () -> Unit, onRegister: () -> Unit, vm: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("MediCare") }) }) { pad ->
        Column(
            Modifier.padding(pad).padding(24.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Connexion", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            OutlinedTextField(
                email, { email = it }, label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                password, { password = it }, label = { Text("Mot de passe") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = MaterialTheme.colorScheme.error) }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    error = null; loading = true
                    scope.launch {
                        runCatching { vm.login(email.trim(), password) }
                            .onSuccess { onSuccess() }
                            .onFailure { error = it.message ?: "Échec de connexion" }
                        loading = false
                    }
                },
                enabled = !loading && email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (loading) "Connexion..." else "Se connecter") }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onRegister) { Text("Créer un compte") }
        }
    }
}
