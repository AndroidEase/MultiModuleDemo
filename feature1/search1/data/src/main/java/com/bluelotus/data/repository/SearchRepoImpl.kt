package com.bluelotus.data.repository

import com.bluelotus.data.local.RecipeDao
import com.bluelotus.data.mappers.toDomain
import com.bluelotus.data.remote.SearchApiService
import com.bluelotus.domain.model.Recipe
import com.bluelotus.domain.model.RecipeDetails
import com.bluelotus.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchRepoImpl(
    private val searchApiService : SearchApiService,
    private val recipeDao : RecipeDao
) : SearchRepository {

    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
       return try {

            val response = searchApiService.getReceipes(s)
            return if(response.isSuccessful) {
                response.body()?.meals?.let {
                    Result.success(it.toDomain())
                } ?: run {
                    Result.failure(Exception("No Data Found"))
                }
            } else {
                Result.failure(Exception("Error Occurred"))
            }
        } catch (e: Exception) {
             Result.failure(e)
        }
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {

        return try {
            val response = searchApiService.getRecipeDetails(id)
            return if(response.isSuccessful) {
                response.body()?.meals?.let {
                    return Result.success(it.first().toDomain())
                } ?: run {
                    return Result.failure(Exception("error occured"))
                }
            } else {
                return Result.failure(Exception("error occured"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipe()
    }

}