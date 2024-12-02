package com.bluelotus.coroutine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bluelotus.common.navigation.NavigationSubGraphRoute

@Composable
fun RecipeNavigation(modifier: Modifier = Modifier, navigationSubGraph : NavigationSubGraph) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController,
        startDestination = NavigationSubGraphRoute.Search.route) {

        navigationSubGraph.searchFeatureApi.registerGraph(
            navHostController = navHostController,
            navGraphBuilder = this
        )
    }
}