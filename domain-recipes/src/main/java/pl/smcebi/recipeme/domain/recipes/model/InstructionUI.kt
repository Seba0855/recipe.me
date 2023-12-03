package pl.smcebi.recipeme.domain.recipes.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class InstructionUI(
    val number: String,
    val instruction: String,
) : Parcelable
