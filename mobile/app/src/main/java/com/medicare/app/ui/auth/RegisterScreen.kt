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
fun RegisterScreen(onSuccess: () -> Unit, onLogin: () -> Unit, vm: AuthViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var pwd2 by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Inscription") }) }) { pad ->
        Column(
            Modifier.padding(pad).padding(24.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(email, { email = it }, label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(pwd, { pwd = it }, label = { Text("Mot de passe") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(pwd2, { pwd2 = it }, label = { Text("Confirmer le mot de passe") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(), singleLine = true)
            error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = MaterialTheme.colorScheme.error) }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    if (pwd != pwd2) { error = "Les mots de passe ne correspondent pas"; return@Button }
                    error = null; loading = true
                    scope.launch {
                        runCatching { vm.register(email.trim(), pwd) }
                            .onSuccess { onSuccess() }
                            .onFailure { error = it.message ?: "Échec d'inscription" }
                        loading = false
                    }
                },
                enabled = !loading && email.isNotBlank() && pwd.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (loading) "Inscription..." else "S'inscrire") }
            TextButton(onClick = onLogin) { Text("J'ai déjà un compte") }
        }
    }
}
