package ni.edu.uam.portotipo_formulariodedatos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.portotipo_formulariodedatos.ui.theme.PortotipoFormularioDeDatosTheme
import kotlin.math.absoluteValue

// 🎨 Paleta FlowIt Mejorada
val VerdeOscuro = Color(0xFF2D5A2D)
val VerdePrimario = Color(0xFF4A7C59)
val VerdeClaro = Color(0xFF8BBE8C)
val Beige = Color(0xFFF5E8C7)
val Crema = Color(0xFFFFFBF0)
val RojoGasto = Color(0xFFE74C3C)
val GrisClaro = Color(0xFFF8F9FA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PortotipoFormularioDeDatosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Crema
                ) {
                    FlowItApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowItApp() {
    var showForm by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    // 🎯 LOGO FLOWIT MÁS GRANDE Y CENTRADO
                    Text(
                        text = "FlowIt",
                        fontSize = 32.sp,  // ← Aumentado de 20sp a 32sp
                        fontWeight = FontWeight.ExtraBold,
                        color = Crema,
                        letterSpacing = 2.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = VerdePrimario
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = !showForm },
                containerColor = VerdePrimario,
                contentColor = Crema
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        FlowItContent(
            modifier = Modifier.padding(padding),
            showForm = showForm
        )
    }
}

data class Movimiento(
    val id: Long,
    val tipo: String,
    val monto: String,
    val descripcion: String,
    val fecha: Long
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowItContent(
    modifier: Modifier = Modifier,
    showForm: Boolean
) {
    val context = LocalContext.current
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Ingreso") }

    // Datos mejorados con modelo
    val movimientos = remember { mutableStateListOf<Movimiento>() }

    // Balance calculado
    val balance = movimientos.sumOf {
        val montoNum = it.monto.toDoubleOrNull() ?: 0.0
        if (it.tipo == "Ingreso") montoNum else -montoNum
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header con balance
        BalanceCard(balance = balance)

        Spacer(modifier = Modifier.height(16.dp))

        // Formulario animado
        AnimatedVisibility(
            visible = showForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(400)
            ) + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            FormularioMovimiento(
                monto = monto,
                onMontoChange = { monto = it },
                descripcion = descripcion,
                onDescripcionChange = { descripcion = it },
                tipo = tipo,
                onTipoChange = { tipo = it },
                onGuardar = {
                    if (monto.isNotEmpty() && descripcion.isNotEmpty()) {
                        val nuevoMovimiento = Movimiento(
                            id = System.currentTimeMillis(),
                            tipo = tipo,
                            monto = monto,
                            descripcion = descripcion,
                            fecha = System.currentTimeMillis()
                        )
                        movimientos.add(nuevoMovimiento)

                        monto = ""
                        descripcion = ""

                        Toast.makeText(context, "✅ Movimiento guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "⚠️ Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de movimientos
        HistorialMovimientos(movimientos = movimientos)
    }
}

@Composable
fun BalanceCard(balance: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (balance >= 0) VerdeClaro else RojoGasto
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Monto Total",
                fontSize = 14.sp,
                color = Crema,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${String.format("%.2f", balance.absoluteValue)}",
                fontSize = 28.sp,
                color = Crema,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = if (balance >= 0) "¡Excelente!" else "Revisa tus gastos",
                fontSize = 12.sp,
                color = Crema.copy(alpha = 0.9f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMovimiento(
    monto: String,
    onMontoChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    tipo: String,
    onTipoChange: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "📝 Nuevo Movimiento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = VerdePrimario,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = monto,
                onValueChange = onMontoChange,
                label = { Text("💵 Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro,
                    focusedContainerColor = Crema,
                    unfocusedContainerColor = Beige
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("📄 Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = VerdePrimario,
                    unfocusedBorderColor = VerdeClaro,
                    focusedContainerColor = Crema,
                    unfocusedContainerColor = Beige
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Selector de tipo CORREGIDO con colores correctos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    selected = tipo == "Ingreso",
                    onClick = { onTipoChange("Ingreso") },
                    label = { Text("📈 Ingreso") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = GrisClaro,
                        selectedContainerColor = VerdePrimario,
                        labelColor = VerdePrimario,
                        selectedLabelColor = Crema
                    )
                )

                FilterChip(
                    selected = tipo == "Gasto",
                    onClick = { onTipoChange("Gasto") },
                    label = { Text("📉 Gasto") },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = GrisClaro,
                        selectedContainerColor = RojoGasto,
                        labelColor = RojoGasto,
                        selectedLabelColor = Crema
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onGuardar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario)
            ) {
                Text("💾 Guardar Movimiento", fontSize = 16.sp, color = Crema)
            }
        }
    }
}

@Composable
fun HistorialMovimientos(movimientos: List<Movimiento>) {
    Column {
        Text(
            text = "📋 Historial",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePrimario,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (movimientos.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Beige),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "📭",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No hay movimientos",
                            fontWeight = FontWeight.Medium,
                            color = VerdeClaro
                        )
                        Text(
                            text = "¡Agrega tu primer movimiento!",
                            fontSize = 14.sp,
                            color = VerdeClaro
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movimientos) { movimiento ->
                    MovimientoCard(movimiento)
                }
            }
        }
    }
}

@Composable
fun MovimientoCard(movimiento: Movimiento) {
    val color = if (movimiento.tipo == "Ingreso") VerdeClaro else RojoGasto

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Beige),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono y tipo
            Card(
                colors = CardDefaults.cardColors(containerColor = color),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (movimiento.tipo == "Ingreso") "📈" else "📉",
                        fontSize = 20.sp,
                        color = Crema
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movimiento.descripcion,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = VerdePrimario
                )
                Text(
                    text = "$${movimiento.monto}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }

            // Botón eliminar (placeholder)
            IconButton(
                onClick = {
                    // TODO: Implementar confirmación de eliminación
                }
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = RojoGasto
                )
            }
        }
    }
}