package pl.smcebi.recipeme.domain.recipes.model

enum class MealType(val externalName: String? = null) {
    // PERSONAL_RECOMMENDATION, TODO: not implemented yet
    RANDOM,
    BREAKFAST("breakfast"),
    DINNER("main course"),
    DESSERT("dessert"),
    SALAD("salad"),
    SOUP("soup"),
    SNACK("snack"),
    DRINK("drink")
}