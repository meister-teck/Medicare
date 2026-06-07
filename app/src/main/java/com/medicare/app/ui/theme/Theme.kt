package com.medicare.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Palette principale
val MedicalPrimary = Color(0xFF3A0CA3)        // Violet foncé
val MedicalSecondary = Color(0xFF4361EE)       // Bleu vif
val MedicalAccent = Color(0xFF7209B7)          // Violet accent
val MedicalBackground = Color(0xFFF8F9FA)      // Fond clair
val MedicalSurface = Color(0xFFFFFFFF)         // Blanc
val MedicalOnPrimary = Color(0xFFFFFFFF)       // Texte sur violet
val MedicalOnBackground = Color(0xFF1A1A2E)    // Texte foncé
val MedicalError = Color(0xFFE63946)           // Rouge erreur
val MedicalSuccess = Color(0xFF06D6A0)         // Vert succès
val MedicalCard = Color(0xFFF0F0FF)            // Fond carte légèrement violet

private val MediCareColorScheme = lightColorScheme(
    primary = MedicalPrimary,
    onPrimary = MedicalOnPrimary,
    secondary = MedicalSecondary,
    tertiary = MedicalAccent,
    background = MedicalBackground,
    onBackground = MedicalOnBackground,
    surface = MedicalSurface,
    onSurface = MedicalOnBackground,
    error = MedicalError,
    onError = MedicalOnPrimary,
    surfaceVariant = MedicalCard
)

@Composable
fun MediCareTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MediCareColorScheme,
        typography = Typography(),
        content = content
    )
}