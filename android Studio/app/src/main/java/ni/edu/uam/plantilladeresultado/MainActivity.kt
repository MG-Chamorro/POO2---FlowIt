package ni.edu.uam.pantalladeresultados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

// --- PALETA DE COLORES ---
val FlowDarkGreen = Color(0xFF546B41)
val FlowSage = Color(0xFF99AD7A)
val FlowCream = Color(0xFFDCCCAC)
val FlowOffWhite = Color(0xFFFFF8EC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowItResultScreen(ingreso = 1500.0, gasto = 600.0)
        }
    }
}

@Composable
fun FlowItResultScreen(ingreso: Double, gasto: Double) {
    var isCordobas by remember { mutableStateOf(false) }
    val tasaCambio = 36.6243

    val ahorro = ingreso - gasto
    val (mensaje, _) = getSmartMessage(ingreso, gasto)

    fun formatMoney(amount: Double): String {
        return if (isCordobas) {
            "C$ ${String.format("%.2f", amount * tasaCambio)}"
        } else {
            "$ ${String.format("%.2f", amount)}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FlowDarkGreen)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // --- 1. TÍTULO CENTRADO ---
        Text(
            text = "Estado FlowIt",
            style = MaterialTheme.typography.headlineSmall,
            color = FlowOffWhite,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 48.dp)
        )

        // --- 2. CÍRCULO DE PROGRESO ---
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(240.dp)) {
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxSize(),
                color = FlowSage.copy(alpha = 0.2f),
                strokeWidth = 16.dp
            )
            CircularProgressIndicator(
                progress = (gasto / ingreso).toFloat().coerceIn(0f, 1f),
                modifier = Modifier.fillMaxSize(),
                color = FlowCream,
                strokeWidth = 16.dp
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("DISPONIBLE", color = FlowOffWhite.copy(alpha = 0.6f), fontWeight = FontWeight.Medium)
                Text(
                    text = formatMoney(ahorro),
                    style = MaterialTheme.typography.headlineMedium,
                    color = FlowOffWhite,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        // --- 3. BOTÓN DE CONVERSIÓN (CENTRADO ABAJO DEL CÍRCULO) ---
        Button(
            onClick = { isCordobas = !isCordobas },
            colors = ButtonDefaults.buttonColors(containerColor = FlowOffWhite.copy(alpha = 0.1f)),
            shape = CircleShape,
            border = BorderStroke(1.dp, FlowCream),
            modifier = Modifier.size(60.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = if (isCordobas) "$" else "C$",
                color = FlowOffWhite,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleMedium
            )
        }

        // --- 4. RESUMEN (INGRESOS Y GASTOS) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DataColumn(label = "INGRESOS", amountText = formatMoney(ingreso), color = FlowSage)
            DataColumn(label = "GASTOS", amountText = formatMoney(gasto), color = FlowCream)
        }

        // --- 5. FEEDBACK INTELIGENTE ---
        Card(
            colors = CardDefaults.cardColors(containerColor = FlowOffWhite.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(1.dp, FlowCream.copy(alpha = 0.3f)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        ) {
            Text(
                text = mensaje,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                color = FlowOffWhite,
                fontWeight = FontWeight.Medium
            )
        }

        // --- 6. BOTÓN FINAL ---
        Button(
            onClick = { /* Navegar */ },
            colors = ButtonDefaults.buttonColors(containerColor = FlowSage),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(bottom = 8.dp)
        ) {
            Text("ENTENDIDO", color = FlowDarkGreen, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun DataColumn(label: String, amountText: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = FlowOffWhite.copy(alpha = 0.7f), style = MaterialTheme.typography.labelLarge)
        Text(
            text = amountText,
            color = color,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun getSmartMessage(ingreso: Double, gasto: Double): Pair<String, Color> {
    val porc = (gasto / ingreso) * 100
    return when {
        gasto == 0.0 -> "¡Perfecto! No has tocado tu sueldo hoy. 🌿" to FlowSage
        porc <= 30 -> "Vas increíble, el ahorro es salud. 💹" to FlowSage
        porc <= 70 -> "Balanceado. Mantén ese ritmo. ⚖️" to FlowCream
        porc < 100 -> "Estás gastando más de lo ideal. ⚠️" to FlowCream
        else -> "Cuidado: Has superado tus ingresos. 🚩" to Color.Red
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlowIt() {
    FlowItResultScreen(1500.0, 450.0)
}