package com.example.juansuarez_p1_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.juansuarez_p1_ap2.presentation.tarea.TareaListScreen
import com.example.juansuarez_p1_ap2.presentation.tarea.TareaScreen
import com.example.juansuarez_p1_ap2.presentation.tarea.TareaViewModel

@Composable
fun TareaNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.TareaList
    ) {
        composable<Screen.TareaList> {
            TareaListScreen(
                goToTarea = {
                    navHostController.navigate(Screen.Tarea(it))
                },
                createTarea = {
                    navHostController.navigate(Screen.Tarea(0))
                },

            )
        }

        composable<Screen.Tarea> {
            val args = it.toRoute<Screen.Tarea>()
            TareaScreen(
                tareaId = args.tareaId,
                goBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
