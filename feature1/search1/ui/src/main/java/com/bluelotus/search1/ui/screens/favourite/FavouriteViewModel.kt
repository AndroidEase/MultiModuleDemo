package com.bluelotus.search1.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluelotus.common.utils.UiText
import com.bluelotus.domain.model.Recipe
import com.bluelotus.domain.use_cases.GetAllRecipeFromLocalDbUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(private val getAllRecipeFromLocalDbUseCase: GetAllRecipeFromLocalDbUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteScreen.UiState())

    val uiState : StateFlow<FavouriteScreen.UiState> get() = _uiState.asStateFlow()

    init {
        getRecipeList()
    }

    fun onEvent(event: FavouriteScreen.Event) {
        when(event) {
            FavouriteScreen.Event.AlphabeticSort -> {
                _uiState.update {
                    it.copy(data = it.data?.sortedBy { it.strMeal })
                }
            }

            FavouriteScreen.Event.LessIngredientSort -> TODO()
            FavouriteScreen.Event.ResetSort -> TODO()
            FavouriteScreen.Event.ShowDetails -> TODO()
        }
    }

    fun getRecipeList() =
        viewModelScope.launch {
            getAllRecipeFromLocalDbUseCase.invoke().collectLatest { list ->
                _uiState.update {
                    FavouriteScreen.UiState(data = list)
                }
            }
        }

}

object FavouriteScreen {
    data class UiState(
        val isLoading : Boolean = false,
        val error : UiText = UiText.Idle,
        val data : List<Recipe> ?= null
    )

    sealed interface Navigation {
        data class GoToRecipeDetailsScreen(val id: String) : Navigation
    }

    sealed interface Event {
        data object AlphabeticSort : Event
        data object LessIngredientSort : Event
        data object ResetSort : Event
        data object ShowDetails : Event
    }
}