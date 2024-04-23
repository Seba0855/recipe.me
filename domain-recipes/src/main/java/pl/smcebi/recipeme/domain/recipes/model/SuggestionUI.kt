package pl.smcebi.recipeme.domain.recipes.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class SuggestionUI(
    val id: String,
    val title: String,
    val imageUrl: String,
) : Parcelable
