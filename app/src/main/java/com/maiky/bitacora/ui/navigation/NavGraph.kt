package com.maiky.bitacora.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maiky.bitacora.ui.screen.addedit.AddEditScreen
import com.maiky.bitacora.ui.screen.home.HomeScreen
import com.maiky.bitacora.ui.screen.search.SearchScreen
import com.maiky.bitacora.ui.screen.statistics.StatisticsScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddEdit : Screen("add_edit?activityId={activityId}&date={date}") {
        fun createRoute(activityId: Long = -1L, date: String = "") =
            "add_edit?activityId=$activityId&date=$date"
    }
    data object Search : Screen("search")
    data object Statistics : Screen("statistics?date={date}") {
        fun createRoute(date: String) = "statistics?date=$date"
    }
}

private const val ANIM_DURATION = 300

@Composable
fun BitacoraNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(ANIM_DURATION)
            ) + fadeIn(animationSpec = tween(ANIM_DURATION))
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(ANIM_DURATION)
            ) + fadeOut(animationSpec = tween(ANIM_DURATION))
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(ANIM_DURATION)
            ) + fadeIn(animationSpec = tween(ANIM_DURATION))
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(ANIM_DURATION)
            ) + fadeOut(animationSpec = tween(ANIM_DURATION))
        }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToAddEdit = { activityId, date ->
                    navController.navigate(Screen.AddEdit.createRoute(activityId, date))
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToStatistics = { date ->
                    navController.navigate(Screen.Statistics.createRoute(date))
                }
            )
        }

        composable(
            route = Screen.AddEdit.route,
            arguments = listOf(
                navArgument("activityId") {
                    type = NavType.LongType
                    defaultValue = -1L
                },
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            AddEditScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { activityId, date ->
                    navController.navigate(Screen.AddEdit.createRoute(activityId, date))
                }
            )
        }

        composable(
            route = Screen.Statistics.route,
            arguments = listOf(
                navArgument("date") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            StatisticsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
