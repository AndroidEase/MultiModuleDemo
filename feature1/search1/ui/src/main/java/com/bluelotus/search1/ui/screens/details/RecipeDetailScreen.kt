package com.bluelotus.search1.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bluelotus.common.utils.UiText
import com.bluelotus.search1.ui.screens.recipe_list.getIngredientsImageUrl
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(modifier: Modifier = Modifier,
                       viewModel: RecipeDetailsViewModel,
                       navHostController: NavHostController,
                       onNavigationClick : () -> Unit = {},
                       onDelete:(com.bluelotus.domain.model.RecipeDetails) -> Unit,
                       onFavouriteClick:(com.bluelotus.domain.model.RecipeDetails) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { navigation ->
                when(navigation) {
                    RecipeDetails.Navigation.GoToRecipeListScreen -> navHostController.popBackStack()
                    is RecipeDetails.Navigation.GoToMediaPlayer -> TODO()
                }
            }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = uiState.value.data?.strMeal ?: "",
                style = MaterialTheme.typography.titleLarge
            )
        }, navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription =  null,
                modifier = Modifier.clickable {
                    uiState.value.data?.let {
                        onNavigationClick.invoke()
                    }
                })
        } , actions = {
            IconButton(onClick = {  }) {
                Icon(imageVector = Icons.Default.Star ,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onNavigationClick.invoke()
                    })
            }

            IconButton(onClick = {
                uiState.value.data?.let {
                    onDelete.invoke(it)
                }
            }) {
                Icon(imageVector = Icons.Default.Delete ,
                    contentDescription = null)
            }
        })
    }) {
            if(uiState.value.isLoading) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

        if(uiState.value.error !is UiText.Idle) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(text = uiState.value.error?.getString() ?:"")
            }
        }

        uiState.value.data?.let { recipeDetails ->

            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                ) {
                AsyncImage(model = recipeDetails.strMealThumb,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp))

                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = recipeDetails.strInstructions,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    recipeDetails.ingredientsPair.forEach {

                        if(it.first.isNotEmpty() || it.second.isNotEmpty()) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            )    {
                                AsyncImage(
                                    model = getIngredientsImageUrl(it.first),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(color = Color.White, shape = CircleShape)
                                        .clip(CircleShape))
                                Text(text =  it.second,
                                    style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Watch Youtube Video",
                        style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(32.dp))
                }

            }
        }
    }

}