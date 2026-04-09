package ni.edu.uam.prototipo_perfildeusuario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 🎨 Paleta FlowIt
val VerdeOscuro = Color(0xFF2D5A2D)
val VerdePrimario = Color(0xFF4A7C59)
val VerdeClaro = Color(0xFF8BBE8C)
val Beige = Color(0xFFF5E8C7)
val Crema = Color(0xFFFFFBF0)
val GrisClaro = Color(0xFFF8F9FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Crema) {
                    ProfileScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    var editMode by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("María González") }
    var email by remember { mutableStateOf("maria.gonzalez@email.com") }
    var telefono by remember { mutableStateOf("+505 8888 1234") }

    Scaffold(
        topBar = {
            // 🎯 CENTER ALIGNED - IGUAL QUE FLOWIT
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "👤 Mi Perfil",
                        fontSize = 32.sp,  // ← GRANDE como FlowIt
                        fontWeight = FontWeight.ExtraBold,
                        color = Crema,
                        letterSpacing = 2.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = VerdePrimario
                ),
                actions = {
                    IconButton(onClick = { editMode = !editMode }) {
                        Icon(
                            imageVector = if (editMode) Icons.Default.Check else Icons.Default.Edit,
                            contentDescription = null,
                            tint = Crema
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = VerdePrimario,
                contentColor = Crema
            ) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    ) { padding ->
        ProfileContent(
            modifier = Modifier.padding(padding),
            editMode = editMode,
            nombre = nombre,
            onNombreChange = { nombre = it },
            email = email,
            onEmailChange = { email = it },
            telefono = telefono,
            onTelefonoChange = { telefono = it }
        )
    }
}

// ... resto del código igual ...

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    editMode: Boolean,
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ProfileHeader(nombre)
        Spacer(Modifier.height(24.dp))

        ProfileFormCard(
            editMode, nombre, onNombreChange,
            email, onEmailChange,
            telefono, onTelefonoChange
        )

        Spacer(Modifier.height(24.dp))
        ProfileStats()
        Spacer(Modifier.height(24.dp))
        ProfileSettings()
    }
}

@Composable
fun ProfileHeader(nombre: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(24.dp)),
        colors = CardDefaults.cardColors(containerColor = VerdePrimario),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(GrisClaro)
                    .shadow(8.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = nombre[0].toString().uppercase(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrimario
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = nombre,
                fontSize = 20.sp,
                color = Crema,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "⭐ Usuario Premium",
                fontSize = 14.sp,
                color = Crema,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormCard(
    editMode: Boolean,
    nombre: String,
    onNombreChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                text = "📋 Información Personal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = onNombreChange,
                label = { Text("👤 Nombre Completo") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("📧 Email") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = onTelefonoChange,
                label = { Text("📱 Teléfono") },
                enabled = editMode,
                readOnly = !editMode,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro
                )
            )
        }
    }
}

@Composable
fun ProfileStats() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VerdeClaro)
    ) {
        Column(Modifier.padding(24.dp)) {
            Text(
                text = "📊 Estadísticas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem("💰 Movimientos", "127")
                ProfileStatItem("📈 Ingresos", "$4,250")
                ProfileStatItem("📉 Gastos", "$2,180")
            }
        }
    }
}

@Composable
fun ProfileStatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(12.dp)
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePrimario
        )
        Text(
            text = label,
            fontSize = 14.sp,
            color = VerdePrimario,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileSettings() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                text = "⚙️ Configuración",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ProfileSettingItem(
                icon = Icons.Default.Notifications,
                title = "Notificaciones",
                subtitle = "Recibir alertas"
            )

            ProfileSettingItem(
                icon = Icons.Default.Lock,
                title = "Privacidad",
                subtitle = "Datos personales"
            )

            ProfileSettingItem(
                icon = Icons.Default.Settings,
                title = "Pagos",
                subtitle = "Métodos de pago"
            )

            ProfileSettingItem(
                icon = Icons.Default.Info,
                title = "Ayuda",
                subtitle = "Soporte técnico"
            )
        }
    }
}

@Composable
fun ProfileSettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = VerdePrimario
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = VerdePrimario
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = VerdeClaro
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = VerdeClaro,
            modifier = Modifier.size(20.dp)
        )
    }
}