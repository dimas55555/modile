package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.navigation.NavRoutes
import com.example.myapplication.ui.screens.AddPersonWithDebtScreen
import com.example.myapplication.ui.screens.DebtDetailScreen
import com.example.myapplication.ui.screens.DebtListScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.ReturnDebtScreen
import com.example.myapplication.viewmodel.DebtViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val viewModel: DebtViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = NavRoutes.LIST
            ) {

                // Головний екран зі списком боргів
                composable(NavRoutes.LIST) {

                    DebtListScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }

                // Детальний екран боргу
                composable(
                    route = "${NavRoutes.DETAIL}/{debtId}",
                    arguments = listOf(
                        navArgument("debtId") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->

                    val debtId =
                        backStackEntry.arguments
                            ?.getInt("debtId") ?: 0

                    DebtDetailScreen(
                        navController = navController,
                        viewModel = viewModel,
                        debtId = debtId
                    )
                }

                // Створення людини разом із боргом
                composable(
                    NavRoutes.ADD_PERSON_WITH_DEBT
                ) {

                    AddPersonWithDebtScreen(
                        navController = navController,
                        viewModel = viewModel
                    )
                }

                // Повернення частини боргу
                composable(
                    route = "${NavRoutes.RETURN_DEBT}/{debtId}",
                    arguments = listOf(
                        navArgument("debtId") {
                            type = NavType.IntType
                        }
                    )
                ) { backStackEntry ->

                    val debtId =
                        backStackEntry.arguments
                            ?.getInt("debtId") ?: 0

                    ReturnDebtScreen(
                        navController = navController,
                        viewModel = viewModel,
                        debtId = debtId
                    )
                }

                // Профіль
                composable(NavRoutes.PROFILE) {

                    ProfileScreen()
                }
            }
        }
    }
}