package pl.smcebi.recipeme.domain.common.translation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.datasource.translation.TranslationDataSource
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import javax.inject.Inject

class TranslateTextUseCase @Inject internal constructor(
    private val translationDataSource: TranslationDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(text: String): DomainResult<String, String?> = withContext(dispatcher) {
        translationDataSource.translate(text = text, targetLanguage = TARGET_LANGUAGE).map(
            onSuccess = { translateResponse ->
                DomainResult.Success(translateResponse.translations.first().text)
            },
            onError = { _, errorBody ->
                DomainResult.Failure(errorBody.getErrorMessage())
            }
        )
    }

    private companion object {
        const val TARGET_LANGUAGE = "EN"
    }
}
