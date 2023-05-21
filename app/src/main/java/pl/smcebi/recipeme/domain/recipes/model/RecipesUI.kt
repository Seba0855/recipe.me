package pl.smcebi.recipeme.domain.recipes.model

data class RecipesUI(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val readyInMinutes: Int,
    val servings: Int,
    val durationAndServings: String
)

