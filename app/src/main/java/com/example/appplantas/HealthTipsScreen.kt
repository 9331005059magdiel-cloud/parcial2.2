package com.example.appplantas

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.AsyncImage

val PrimaryGreen = Color(0xFF6EE619)
val BackgroundLight = Color(0xFFF7F8F6)
val Sage = Color(0xFF728863)
val Earth = Color(0xFF4A3728)
val DarkText = Color(0xFF182111)

// 1. PANTALLA INICIO (Herbolaria)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        containerColor = BackgroundLight,
        bottomBar = { AppBottomNavigation(0, navController) },
        floatingActionButton = { AccessibilityFab() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text("Hola,", fontSize = 20.sp, color = Sage)
                Text("¿Qué deseas curar hoy?", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar plantas, síntomas...", color = Sage) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Sage) },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                        focusedBorderColor = PrimaryGreen, unfocusedBorderColor = Sage.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
            }
            item {
                Text("Plantas del día", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Earth)
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(3) { index ->
                        PlantaDiaCard(
                            title = listOf("Aloe Vera", "Romero", "Eucalipto")[index],
                            onClick = { navController.navigate("detalle") }
                        )
                    }
                }
            }
            item {
                Text("Categorías", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Earth)
                Spacer(modifier = Modifier.height(12.dp))
            }
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CategoriaItem(Icons.Default.Spa, "Relajación")
                    CategoriaItem(Icons.Default.LocalDining, "Digestión")
                    CategoriaItem(Icons.Default.Healing, "Curación")
                    CategoriaItem(Icons.Default.MonitorWeight, "Energía")
                }
            }
        }
    }
}

@Composable
fun PlantaDiaCard(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.width(200.dp).clickable(onClick = onClick)
    ) {
        Column {
            AsyncImage(
                model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBCnUCptZdYscCj-w44rvbjii2n605KbGmXGWkDGuzdGD7DDWB1-CetzBfeTpD0nQfG2_viE336457cSMOCtCAONpGw-GaF9DQ3_IU06TdKFsTUegMMfUg_YOKqfVtUztf19QDgB2p_OQwkH18j6dsSJDZraWl5VvXTkuG5qkj3UfxYoEE2wO4dg5sfMrz3Krmn5HERZg0Jdke-441xikQfGHZQLYkTCTteGG2uROaKhZ_HruEPD7ipaE0ychc_6unLEb7aD4SlVqg",
                contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(120.dp)
            )
            Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun CategoriaItem(icon: ImageVector, title: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(64.dp).background(PrimaryGreen.copy(alpha = 0.2f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Earth, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, fontSize = 14.sp, color = DarkText)
    }
}

// 2. PANTALLA BÚSQUEDA / LISTA
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HierbasMedicinalesScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            Surface(color = BackgroundLight) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Sage) }
                        Text("Hierbas Medicinales", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = searchQuery, onValueChange = { searchQuery = it }, modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Buscar...", color = Sage) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Sage) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        item { FilterChipItem("Digestión", true) }
                        item { FilterChipItem("Sueño", false) }
                        item { FilterChipItem("Dolor", false) }
                    }
                }
            }
        },
        bottomBar = { AppBottomNavigation(1, navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item { HerbCard("Manzanilla", "Ideal para la digestión", "Ayuda a calmar molestias.", "url1", true, onClick = { navController.navigate("detalle") }) }
            item { HerbCard("Menta", "Alivio dolor de cabeza", "Refrescante.", "url2", false, onClick = { navController.navigate("detalle") }) }
        }
    }
}

// 3. PANTALLA DETALLE (Diente de León)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleHierbaScreen(navController: NavController) {
    Scaffold(containerColor = BackgroundLight, bottomBar = { AppBottomNavigation(1, navController) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
            item {
                Box {
                    AsyncImage(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBCnUCptZdYscCj-w44rvbjii2n605KbGmXGWkDGuzdGD7DDWB1-CetzBfeTpD0nQfG2_viE336457cSMOCtCAONpGw-GaF9DQ3_IU06TdKFsTUegMMfUg_YOKqfVtUztf19QDgB2p_OQwkH18j6dsSJDZraWl5VvXTkuG5qkj3UfxYoEE2wO4dg5sfMrz3Krmn5HERZg0Jdke-441xikQfGHZQLYkTCTteGG2uROaKhZ_HruEPD7ipaE0ychc_6unLEb7aD4SlVqg", contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(250.dp))
                    IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(16.dp).background(Color.White.copy(alpha = 0.7f), CircleShape)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = DarkText)
                    }
                }
            }
            item {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Diente de León", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = DarkText)
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Sage)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChipItem("Desintoxicante", true)
                        FilterChipItem("Digestivo", false)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Poderoso depurativo natural que ayuda a limpiar el hígado y los riñones, mejorando la digestión.", fontSize = 16.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("¿Qué cura?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Earth)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Problemas hepáticos\n• Retención de líquidos\n• Indigestión", fontSize = 16.sp, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Guía de Preparación", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Earth)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp)) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalCafe, contentDescription = null, tint = PrimaryGreen)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Hervir 1 taza de agua, añadir 1 cda de hojas secas, reposar 5 min.", fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen), shape = RoundedCornerShape(16.dp)) {
                        Text("Añadir a Mi Jardín", color = DarkText, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

// 4. PANTALLA MIS FAVORITOS / MI JARDÍN
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisFavoritosScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Remedios", "Plantas", "Recetas")

    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text("Mi Jardín", fontWeight = FontWeight.Bold, color = Earth) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundLight)
                )
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = BackgroundLight,
                    contentColor = PrimaryGreen,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(Modifier.tabIndicatorOffset(tabPositions[selectedTab]), color = PrimaryGreen)
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title, color = if (selectedTab == index) DarkText else Sage, fontWeight = FontWeight.Bold) }
                        )
                    }
                }
            }
        },
        bottomBar = { AppBottomNavigation(3, navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(4) {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBCnUCptZdYscCj-w44rvbjii2n605KbGmXGWkDGuzdGD7DDWB1-CetzBfeTpD0nQfG2_viE336457cSMOCtCAONpGw-GaF9DQ3_IU06TdKFsTUegMMfUg_YOKqfVtUztf19QDgB2p_OQwkH18j6dsSJDZraWl5VvXTkuG5qkj3UfxYoEE2wO4dg5sfMrz3Krmn5HERZg0Jdke-441xikQfGHZQLYkTCTteGG2uROaKhZ_HruEPD7ipaE0ychc_6unLEb7aD4SlVqg", contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Té Relajante", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("15 min", color = Sage, fontSize = 14.sp)
                            Text("Ver receta", color = PrimaryGreen, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { }.padding(top = 4.dp))
                        }
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = PrimaryGreen)
                    }
                }
            }
        }
    }
}

// 5. PANTALLA CONSEJOS DE SALUD
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsejosSaludScreen(navController: NavController) {
    Scaffold(
        containerColor = BackgroundLight,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Consejos de Salud", color = Earth, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = { AppBottomNavigation(2, navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            item {
                Text("Consejos para su Bienestar", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Earth)
                Text("Aprenda a almacenar sus hierbas de forma segura.", fontSize = 16.sp, color = Color.DarkGray)
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Inventory2, contentDescription = null, tint = PrimaryGreen)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Cómo almacenar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("1. Guarde en lugar fresco y oscuro.\n2. Frascos de vidrio.\n3. Etiquete con fecha.", lineHeight = 24.sp)
                    }
                }
            }
        }
    }
}

// 6. PANTALLA MI PERFIL
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiPerfilScreen(navController: NavController) {
    Scaffold(containerColor = BackgroundLight, bottomBar = { AppBottomNavigation(4, navController) }) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item {
                AsyncImage(model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBCnUCptZdYscCj-w44rvbjii2n605KbGmXGWkDGuzdGD7DDWB1-CetzBfeTpD0nQfG2_viE336457cSMOCtCAONpGw-GaF9DQ3_IU06TdKFsTUegMMfUg_YOKqfVtUztf19QDgB2p_OQwkH18j6dsSJDZraWl5VvXTkuG5qkj3UfxYoEE2wO4dg5sfMrz3Krmn5HERZg0Jdke-441xikQfGHZQLYkTCTteGG2uROaKhZ_HruEPD7ipaE0ychc_6unLEb7aD4SlVqg", contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.size(120.dp).clip(CircleShape).border(4.dp, PrimaryGreen, CircleShape))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Usuario Herbal", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                Text("Amante de la naturaleza", fontSize = 16.sp, color = Sage)
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                PerfilOptionItem(Icons.Default.Settings, "Configuración")
                PerfilOptionItem(Icons.Default.Notifications, "Notificaciones")
                PerfilOptionItem(Icons.Default.Accessibility, "Accesibilidad")
                PerfilOptionItem(Icons.Default.Help, "Ayuda y Soporte")
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEAEA), contentColor = Color.Red), modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(16.dp)) {
                    Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun PerfilOptionItem(icon: ImageVector, title: String) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(48.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Earth)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 18.sp, color = DarkText, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Sage)
    }
}

// COMPONENTES COMPARTIDOS
@Composable
fun AppBottomNavigation(selectedIndex: Int, navController: NavController) {
    val context = LocalContext.current
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        val items = listOf(
            Triple("Inicio", Icons.Default.Home, "inicio"),
            Triple("Buscar", Icons.Default.Search, "hierbas"),
            Triple("Consejos", Icons.Default.Lightbulb, "consejos"),
            Triple("Mi Jardín", Icons.Default.LocalFlorist, "jardin"),
            Triple("Perfil", Icons.Default.Person, "perfil"),
            Triple("Videos", Icons.Default.PlayCircle, "videos")
        )
        items.forEachIndexed { index, (label, icon, route) ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    if (selectedIndex != index) {
                        if (route == "videos") {
                            // Lanzar VideosActivity (Activity2) desde la BottomNavBar
                            context.startActivity(Intent(context, VideosActivity::class.java))
                        } else {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Normal) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryGreen, selectedTextColor = DarkText, unselectedIconColor = Sage, unselectedTextColor = Sage, indicatorColor = Color.Transparent)
            )
        }
    }
}

@Composable
fun FilterChipItem(text: String, isSelected: Boolean) {
    Surface(color = if (isSelected) PrimaryGreen else Color.White, shape = RoundedCornerShape(50), border = if (!isSelected) BorderStroke(1.dp, Sage.copy(alpha = 0.3f)) else null, modifier = Modifier.clickable { }) {
        Text(text = text, color = if (isSelected) DarkText else Sage, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp))
    }
}

@Composable
fun HerbCard(title: String, subtitle: String, description: String, imageUrl: String, isFavorite: Boolean, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column {
            AsyncImage(model = imageUrl, contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(200.dp))
            Column(modifier = Modifier.padding(20.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    Icon(Icons.Default.Star, contentDescription = null, tint = if (isFavorite) PrimaryGreen else Sage.copy(alpha = 0.5f))
                }
                Text(subtitle, fontSize = 16.sp, color = Sage)
                Spacer(modifier = Modifier.height(12.dp))
                Text(description, fontSize = 16.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen, contentColor = DarkText), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().height(56.dp)) {
                    Text("Ver Detalles", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AccessibilityFab() {
    Surface(shape = CircleShape, color = Sage, border = BorderStroke(4.dp, Color.White), modifier = Modifier.size(64.dp), shadowElevation = 8.dp, onClick = { }) {
        Box(contentAlignment = Alignment.Center) { Icon(Icons.Default.FormatSize, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp)) }
    }
}
