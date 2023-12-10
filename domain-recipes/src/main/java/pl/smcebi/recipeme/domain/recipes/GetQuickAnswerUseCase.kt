package pl.smcebi.recipeme.domain.recipes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import pl.smcebi.recipeme.domain.recipes.model.QuickAnswerUI
import pl.smcebi.recipeme.recipes.QuickAnswerResponse
import pl.smcebi.recipeme.recipes.RecipesDataSource
import javax.inject.Inject

@Deprecated("No longer in use, should be deleted")
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
