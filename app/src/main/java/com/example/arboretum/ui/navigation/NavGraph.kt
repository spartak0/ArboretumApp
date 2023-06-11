package com.example.arboretum.ui.navigation

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.arboretum.domain.model.Plant
import com.example.arboretum.ui.details_screen.DetailsScreen
import com.example.arboretum.ui.main_activity.SnackbarAction
import com.example.arboretum.ui.main_screen.MainScreen
import com.example.arboretum.ui.search_screen.SearchScreen
import com.example.arboretum.ui.splash_screen.SplashScreen
import com.example.arboretum.utils.Constant
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String = Screen.SplashScreen.route,
    showSnackbar: (String, SnackbarAction) -> Unit,
) {
    AnimatedNavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navigateToMain = {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(0)
                }
            })
        }

        composable(
            Screen.MainScreen.route,
        ) {
            MainScreen(
                showSnackbar = showSnackbar,
                navigateToDetails = { movie ->
                    navController.navigate(
                        Screen.DetailsScreen.route,
                        Constant.MOVIE_KEY to movie
                    )
                },
                navigateToSearch = { navController.navigate(Screen.SearchScreen.route) },
            )
        }

        composable(
            Screen.DetailsScreen.route,
            enterTransition = {
                slideInHorizontally { it }
            },
            exitTransition = { slideOutHorizontally { it } },
        ) {
            val plant: Plant? =
                navController.previousBackStackEntry?.arguments?.getParcelable(Constant.MOVIE_KEY)
                    ?: navController.currentBackStackEntry?.arguments?.getParcelable(Constant.MOVIE_KEY)
            plant?.let {
                DetailsScreen(
                    plant = it,
                    navigateUp = { navController.navigateUp() },
                    showSnackbar = showSnackbar
                )
            }
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(
                showSnackbar = showSnackbar,
                navigateUp = { navController.navigateUp() },
                navigateToDetails = { movie ->
                    navController.navigate(
                        Screen.DetailsScreen.route,
                        Constant.MOVIE_KEY to movie
                    )
                })
        }
    }
}


fun NavHostController.navigate(route: String, parcelableArg: Pair<String, Parcelable>) {
    currentBackStackEntry?.arguments?.putParcelable(parcelableArg.first, parcelableArg.second)
    navigate(route)
}