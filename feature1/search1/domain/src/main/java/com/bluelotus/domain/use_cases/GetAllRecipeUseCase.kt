package com.bluelotus.domain.use_cases

import com.bluelotus.common.utils.NetworkResult
import com.bluelotus.domain.model.Recipe
import com.bluelotus.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecipeUseCase @Inject constructor(private val searchRepository: SearchRepository) {

    operator fun invoke(q : String) = flow<NetworkResult<List<Recipe>>> {
        emit(NetworkResult.Loading())

        val response = searchRepository.getRecipes(q)
        println("Respooo::" + response)
        if(response.isSuccess) {
            println("Use case success")
            emit(NetworkResult.Success(response.getOrNull()))
        } else {
            println("Use case fail")

            emit(NetworkResult.Error(response.exceptionOrNull()?.message))
        }
    }.catch {
        emit(NetworkResult.Error(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}