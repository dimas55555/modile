package com.example.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplication.biometric.AppBiometricManager
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.preferences.SecurityPreferences
import com.example.myapplication.data.remote.FakeApiService
import com.example.myapplication.data.repository.DebtRepository
import com.example.myapplication.navigation.NavRoutes
import com.example.myapplication.ui.screens.*
import com.example.myapplication.viewmodel.DebtViewModel
import com.example.myapplication.viewmodel.SecurityViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val database =
            AppDatabase.getDatabase(this)

        val repository =
            DebtRepository(
                debtDao = database.debtDao(),
                personDao = database.personDao(),
                paymentDao = database.paymentDao(),
                api = FakeApiService()
            )

        val biometricManager =
            AppBiometricManager(this)

        val securityPreferences =
            SecurityPreferences(this)

        val startDestination =
            if (
                securityPreferences
                    .isBiometricEnabled()
            ) {
                NavRoutes.LOCK
            } else {
                NavRoutes.LIST
            }

        setContent {

            val navController =
                rememberNavController()

            val debtViewModel:
                    DebtViewModel =
                viewModel(
                    factory =
                        object :
                            ViewModelProvider.Factory {

                            override fun <T : ViewModel>
                                    create(
                                modelClass:
                                Class<T>
                            ): T {

                                return DebtViewModel(
                                    repository
                                ) as T
                            }
                        }
                )

            val securityViewModel:
                    SecurityViewModel =
                viewModel(
                    factory =
                        object :
                            ViewModelProvider.Factory {

                            override fun <T : ViewModel>
                                    create(
                                modelClass:
                                Class<T>
                            ): T {

                                return SecurityViewModel(
                                    biometricManager,
                                    securityPreferences
                                ) as T
                            }
                        }
                )

            NavHost(
                navController =
                    navController,

                startDestination =
                    startDestination
            ) {

                composable(
                    NavRoutes.LOCK
                ) {

                    LockScreen(
                        activity =
                            this@MainActivity,

                        viewModel =
                            securityViewModel,

                        onSuccess = {

                            navController
                                .navigate(
                                    NavRoutes.LIST
                                ) {

                                    popUpTo(
                                        NavRoutes.LOCK
                                    ) {
                                        inclusive = true
                                    }
                                }
                        }
                    )
                }

                composable(
                    NavRoutes.LIST
                ) {

                    DebtListScreen(
                        navController,
                        debtViewModel
                    )
                }

                composable(
                    "${NavRoutes.DETAIL}/{debtId}",
                    arguments =
                        listOf(
                            navArgument(
                                "debtId"
                            ) {
                                type =
                                    NavType.IntType
                            }
                        )
                ) {

                    val debtId =
                        it.arguments
                            ?.getInt(
                                "debtId"
                            ) ?: 0

                    DebtDetailScreen(
                        navController,
                        debtViewModel,
                        debtId
                    )
                }

                composable(
                    NavRoutes
                        .ADD_PERSON_WITH_DEBT
                ) {

                    AddPersonWithDebtScreen(
                        navController,
                        debtViewModel
                    )
                }

                composable(
                    "${NavRoutes.RETURN_DEBT}/{debtId}",
                    arguments =
                        listOf(
                            navArgument(
                                "debtId"
                            ) {
                                type =
                                    NavType.IntType
                            }
                        )
                ) {

                    val debtId =
                        it.arguments
                            ?.getInt(
                                "debtId"
                            ) ?: 0

                    ReturnDebtScreen(
                        navController,
                        debtViewModel,
                        debtId
                    )
                }

                composable(
                    NavRoutes.PROFILE
                ) {

                    ProfileScreen(
                        navController
                    )
                }

                composable(
                    NavRoutes.SECURITY
                ) {

                    SecuritySettingsScreen(
                        securityViewModel
                    )
                }

                composable(
                    NavRoutes
                        .BIOMETRIC_CONFIRM
                ) {

                    BiometricConfirmScreen(
                        activity =
                            this@MainActivity,

                        viewModel =
                            securityViewModel,

                        onSuccess = {

                            debtViewModel
                                .clearReturnedDebts()

                            navController
                                .popBackStack()
                        }
                    )
                }
            }
        }
    }
}