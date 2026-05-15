package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.remote.FakeApiService
import com.example.myapplication.data.repository.DebtRepository
import com.example.myapplication.navigation.NavRoutes
import com.example.myapplication.ui.screens.*
import com.example.myapplication.viewmodel.DebtViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database =
            AppDatabase.getDatabase(this)

        val repository = DebtRepository(
            debtDao = database.debtDao(),
            personDao = database.personDao(),
            paymentDao = database.paymentDao(),
            api = FakeApiService()
        )

        setContent {

            val navController =
                rememberNavController()

            val viewModel: DebtViewModel =
                viewModel(
                    factory = object :
                        ViewModelProvider.Factory {

                        override fun <T : ViewModel>
                                create(
                            modelClass: Class<T>
                        ): T {

                            return DebtViewModel(
                                repository
                            ) as T
                        }
                    }
                )

            NavHost(
                navController = navController,
                startDestination = NavRoutes.LIST
            ) {

                composable(NavRoutes.LIST) {

                    DebtListScreen(
                        navController,
                        viewModel
                    )
                }

                composable(
                    "${NavRoutes.DETAIL}/{debtId}",
                    arguments = listOf(
                        navArgument("debtId") {
                            type = NavType.IntType
                        }
                    )
                ) {

                    val debtId =
                        it.arguments?.getInt("debtId")
                            ?: 0

                    DebtDetailScreen(
                        navController,
                        viewModel,
                        debtId
                    )
                }

                composable(
                    NavRoutes.ADD_PERSON_WITH_DEBT
                ) {

                    AddPersonWithDebtScreen(
                        navController,
                        viewModel
                    )
                }

                composable(
                    "${NavRoutes.RETURN_DEBT}/{debtId}",
                    arguments = listOf(
                        navArgument("debtId") {
                            type = NavType.IntType
                        }
                    )
                ) {

                    val debtId =
                        it.arguments?.getInt("debtId")
                            ?: 0

                    ReturnDebtScreen(
                        navController,
                        viewModel,
                        debtId
                    )
                }

                composable(
                    NavRoutes.PROFILE
                ) {
                    ProfileScreen(navController)
                }
            }
        }
    }
}