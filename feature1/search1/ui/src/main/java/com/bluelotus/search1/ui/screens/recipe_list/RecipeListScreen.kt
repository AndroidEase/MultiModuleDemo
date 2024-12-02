package com.bluelotus.search1.ui.screens.recipe_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bluelotus.common.navigation.NavigationRoutes
import com.bluelotus.common.utils.UiText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    navHostController: NavHostController,
    onClick : (String) -> Unit,
) {

    val uiState = viewModel.uiState.collectAsState()

    val query = rememberSaveable {
        mutableStateOf("")
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest {
                when(it) {
                    is RecipeList.Navigation.GoToRecipeDetails -> {
                        navHostController.navigate(NavigationRoutes.RecipeDetails.sendId(it.id))
                    }
                }
            }
    }

    Scaffold(topBar = {
        TextField(
            placeholder = { Text(text = "Search here", style = MaterialTheme.typography.bodySmall)},
            value = query.value,
            onValueChange = {
                query.value = it
                viewModel.onEvent(RecipeList.Event.searchRecipe(query.value))
            }, colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background
            ), modifier = Modifier.fillMaxWidth()
        )
    }) {
        if(uiState.value.isLoading) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize(),
               contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if(uiState.value.error !is UiText.Idle) {
            Box(modifier = Modifier
                .padding(it)
                .fillMaxSize(),
                contentAlignment = Alignment.Center) {
                uiState.value.error?.let { it1 -> Text(text = it1.getString()) }
            }
        }

            uiState.value.data?.let {
                list ->
                LazyColumn (
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    items(list) {
                        Card (modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .clickable { onClick.invoke(it.idMeal) },
                            shape = RoundedCornerShape(12.dp)
                        )
                        {
                            AsyncImage(
                                model = it.strMealThumb,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(text = it.strMeal, style = MaterialTheme.typography.bodyLarge)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = it.strInstructions, style = MaterialTheme.typography.bodyMedium,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 4 )
                                Spacer(modifier = Modifier.height(12.dp))
                                if(it.strTags?.isNotEmpty() == true) {
                                    FlowRow {
                                        it.strTags?.split(",")
                                            ?.forEach {
                                                Box(modifier = Modifier
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                                    .wrapContentSize()
                                                    .background(
                                                        color = Color.White,
                                                        shape = RoundedCornerShape(24.dp)
                                                    )
                                                    .clip(RoundedCornerShape(24.dp))
                                                    .border(
                                                        width = 1.dp,
                                                        color = Color.Red,
                                                        shape = RoundedCornerShape(12.dp)
                                                    ),
                                                    contentAlignment = Alignment.Center) {
                                                    Text(text = it,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        modifier = Modifier.padding(
                                                            horizontal = 12.dp,
                                                            vertical = 6.dp
                                                        ))
                                                }
                                            }

                                    }

                                    Spacer(modifier = Modifier.height(12.dp))
                                }   
                            }
                        }
                    }
                }
            }
    }
}

fun getIngredientsImageUrl(name: String) =
    "https://www.themealdb.com/images/ingredients/${name}.png"