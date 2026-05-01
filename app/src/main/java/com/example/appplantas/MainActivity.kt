package com.example.appplantas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appplantas.ui.theme.AppPlantasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppPlantasTheme {
                val navController = rememberNavController()
                
                NavHost(navController = navController, startDestination = "inicio") {
                    composable("inicio") { InicioScreen(navController) }
                    composable("hierbas") { HierbasMedicinalesScreen(navController) }
                    composable("consejos") { ConsejosSaludScreen(navController) }
                    composable("jardin") { MisFavoritosScreen(navController) }
                    composable("perfil") { MiPerfilScreen(navController) }
                    composable("detalle") { DetalleHierbaScreen(navController) }
                }
            }
        }
    }
}
