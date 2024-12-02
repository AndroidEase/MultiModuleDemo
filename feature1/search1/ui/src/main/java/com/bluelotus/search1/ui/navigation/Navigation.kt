package com.bluelotus.search1.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bluelotus.common.navigation.FeatureApi
import com.bluelotus.common.navigation.NavigationRoutes
import com.bluelotus.common.navigation.NavigationSubGraphRoute
import com.bluelotus.search1.ui.screens.details.RecipeDetailsViewModel
import com.bluelotus.search1.ui.screens.details.RecipeDetailScreen
import com.bluelotus.search1.ui.screens.details.RecipeDetails
import com.bluelotus.search1.ui.screens.recipe_list.RecipeList
import com.bluelotus.search1.ui.screens.recipe_list.RecipeListScreen
import com.bluelotus.search1.ui.screens.recipe_list.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoutes.RecipeList.route) {

            composable(route = NavigationRoutes.RecipeList.route) {
                val viewmodel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(viewModel = viewmodel, navHostController) {
                    mealId ->
                    viewmodel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }
            }

            composable(route = NavigationRoutes.RecipeDetails.route) {

                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")

                LaunchedEffect (key1 = mealId){
                    mealId?.let {
                        viewModel.onEvent(com.bluelotus.search1.ui.screens.details.RecipeDetails.Event.FetchRecipeDetails(mealId))
                    }
                }
                RecipeDetailScreen(viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
                    },
                    onFavouriteClick = {
                        viewModel.onEvent(RecipeDetails.Event.InsertRecipe(it))
                    },
                    onDelete = {
                        viewModel.onEvent(com.bluelotus.search1.ui.screens.details.RecipeDetails.Event.DeleteRecipe(it))
                    },
                    navHostController = navHostController
                )

            }
        }
    }
}
