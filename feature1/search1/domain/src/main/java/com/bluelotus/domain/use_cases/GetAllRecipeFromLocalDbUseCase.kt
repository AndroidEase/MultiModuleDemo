package com.bluelotus.domain.use_cases

import com.bluelotus.domain.repository.SearchRepository
import javax.inject.Inject

class GetAllRecipeFromLocalDbUseCase @Inject constructor(private  val searchRepository: SearchRepository){

    operator fun invoke() = searchRepository.getAllRecipes()
}