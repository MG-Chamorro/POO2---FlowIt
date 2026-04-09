package ni.edu.uam.flowit_login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. DEFINICIÓN DE LA PALETA DE COLORES (Según la imagen proporcionada)
val FlowitDarkGreen = Color(0xFF546B41)   // rgb(84, 107, 65) - Primario / Texto
val FlowitMedGreen = Color(0xFF99AD7A)    // rgb(153, 173, 122) - Botón Principal
val FlowitBeige = Color(0xFFDCCCAC)      // rgb(220, 204, 172) - Bordes / Detalles
val FlowitOffWhite = Color(0xFFFFF8EC)   // rgb(255, 248, 236) - Fondo Principal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Estado para controlar qué pantalla mostrar (0=Login, 1=Registro)
                var currentScreen by remember { mutableIntStateOf(0) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FlowitOffWhite // Fondo OffWhite
                ) {
                    // Animación sutil al cambiar de pantalla
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        }
                    ) { screen ->
                        if (screen == 0) {
                            LoginScreen(onNavigateToRegister = { currentScreen = 1 })
                        } else {
                            RegisterScreen(onNavigateToLogin = { currentScreen = 0 })
                        }
                    }
                }
            }
        }
    }
}

// --- PANTALLA DE INICIO DE SESIÓN ---
@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Título - Usando FlowitDarkGreen
        Text(
            text = "Flowit",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = FlowitDarkGreen
        )
        Text(
            text = "Inicia sesión para continuar",
            fontSize = 16.sp,
            color = FlowitDarkGreen
        )

        Spacer(modifier = Modifier.height(64.dp))

        // Campo: Usuario
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FlowitMedGreen,
                unfocusedBorderColor = FlowitBeige,
                focusedLabelColor = FlowitDarkGreen,
                unfocusedLabelColor = FlowitDarkGreen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FlowitDarkGreen) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FlowitMedGreen,
                unfocusedBorderColor = FlowitBeige,
                focusedLabelColor = FlowitDarkGreen,
                unfocusedLabelColor = FlowitDarkGreen
            )
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Botón Principal - Usando FlowitMedGreen con texto OffWhite
        Button(
            onClick = { /* Lógica de Login */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = FlowitMedGreen,
                contentColor = FlowitOffWhite
            )
        ) {
            Text("Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BOTÓN SOLO LETRAS (TextButton) - Navega al Registro
        TextButton(onClick = onNavigateToRegister) {
            Text(
                "¿No tienes cuenta? Regístrate aquí",
                color = FlowitDarkGreen,
                fontSize = 14.sp
            )
        }
    }
}

// --- PANTALLA DE REGISTRO ---
@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Crear Cuenta",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = FlowitDarkGreen
        )
        Text(
            text = "Únete a Flowit",
            fontSize = 14.sp,
            color = FlowitDarkGreen
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo: Correo Electrónico (Para validación)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = FlowitDarkGreen) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Nombre
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Apellido
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FlowitDarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Usuario
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null, tint = FlowitDarkGreen) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FlowitDarkGreen) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FlowitMedGreen, unfocusedBorderColor = FlowitBeige)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Principal - Usando FlowitMedGreen
        Button(
            onClick = { /* Lógica de Registro */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FlowitMedGreen, contentColor = FlowitOffWhite)
        ) {
            Text("Registrarse", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // BOTÓN SOLO LETRAS (TextButton) - Vuelve al Login
        TextButton(onClick = onNavigateToLogin) {
            Text(
                "¿Ya tienes cuenta? Inicia sesión",
                color = FlowitDarkGreen,
                fontSize = 14.sp
            )
        }
    }
}