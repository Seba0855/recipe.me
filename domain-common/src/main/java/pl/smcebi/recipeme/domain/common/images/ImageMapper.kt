package pl.smcebi.recipeme.domain.common.images

import dagger.Reusable
import javax.inject.Inject

/**
 * Based on spoonacular documentation, we have to manually construct a link,
 * which will allow us to retrieve image from their API.
 *
 * @see: https://spoonacular.com/food-api/docs#Show-Images
 */

@Reusable
class ImageMapper @Inject constructor() {
    fun mapIngredients(imageName: String): String = buildString {
        append(BASE_URL, INGREDIENTS_PATH, ImageSize.MEDIUM.size)
        append("/", imageName)
    }

    fun mapCookingEquipment(equipmentName: String): String = buildString {
        append(BASE_URL, EQUIPMENT_PATH, ImageSize.MEDIUM.size)
        append("/", equipmentName)
    }

    fun mapRecipes(
        recipeID: String,
        imageSize: ImageSize = ImageSize.WIDE,
        imageType: String
    ): String = buildString {
        append(BASE_URL, RECIPE_PATH)
        append(recipeID, "-", imageSize.size, ".", imageType)
    }

    fun mapMenuItems() {
        throw NotImplementedError("Currently we won't use that")
    }

    fun mapGroceryProducts() {
        throw NotImplementedError("Currently we won't use that")
    }

    enum class ImageSize(val size: String) {
        SMALL(size = "90x90"),
        MEDIUM(size = "250x250"),
        WIDE(size = "636x393"),
    }

    private companion object {
        const val BASE_URL = "https://spoonacular.com/"
        const val INGREDIENTS_PATH = "cdn/ingredients_"
        const val EQUIPMENT_PATH = "cdn/equipment_"
        const val RECIPE_PATH = "/recipeimages/"
    }
}
