package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.di.annotations.DispatcherIO
import pl.smcebi.recipeme.domain.common.DomainResult
import pl.smcebi.recipeme.domain.common.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.QuickAnswerUI
import pl.smcebi.recipeme.infrastructure.model.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.infrastructure.remote.datasource.recipes.RecipesDataSource
import javax.inject.Inject

class GetQuickAnswerUseCase @Inject internal constructor(
    private val dataSource: RecipesDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String): DomainResult<QuickAnswerUI, String?> =
        withContext(dispatcher) {
            dataSource.getQuickAnswer(query).map(
                onSuccess = { response ->
                    DomainResult.Success(response.mapUI())
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }

    private fun QuickAnswerResponse.mapUI(): QuickAnswerUI =
        QuickAnswerUI(
            answer = this.answer,
            image = this.image
        )
}
