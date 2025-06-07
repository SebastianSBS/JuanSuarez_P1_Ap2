package com.example.juansuarez_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.juansuarez_p1_ap2.presentation.navigation.TareaNavHost
import com.example.juansuarez_p1_ap2.ui.theme.JuanSuarez_P1_Ap2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuanSuarez_P1_Ap2Theme {
                val navHost = rememberNavController()
                TareaNavHost(navHost)
            }
        }
    }
}

