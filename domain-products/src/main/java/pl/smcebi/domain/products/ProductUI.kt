package pl.smcebi.domain.products

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ProductUI(
    val ean: String,
    val brand: String,
    val name: String,
    val imageUrl: String,
) : Parcelable
