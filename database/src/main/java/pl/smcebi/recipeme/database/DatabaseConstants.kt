package pl.smcebi.recipeme.database

internal object DatabaseConstants {
    const val DB_NAME = "recipeme_database"

    // Table names
    const val TABLE_RECIPE = "table_recipe"
    const val TABLE_PRODUCTS = "table_products"

    // Recipe entity
    const val COLUMN_RECIPE_ENTITY_ID = "column_recipe_entity_id"
    const val COLUMN_RECIPE_ID = "column_recipe_id"
    const val COLUMN_RECIPE_TITLE = "column_recipe_title"
    const val COLUMN_RECIPE_DESCRIPTION = "column_recipe_description"
    const val COLUMN_RECIPE_READY_IN_MINUTES = "column_recipe_ready_in_minutes"
    const val COLUMN_RECIPE_SERVINGS = "column_recipe_servings"
    const val COLUMN_RECIPE_IMAGE_URL = "column_recipe_image_url"
    const val COLUMN_RECIPE_DISH_TYPE = "column_recipe_dish_type"
    const val COLUMN_RECIPE_DURATION_AND_SERVINGS = "column_recipe_duration_and_servings"

    // Product entity
    const val COLUMN_PRODUCT_EAN = "column_product_ean"
    const val COLUMN_PRODUCT_BRAND = "column_product_brand"
    const val COLUMN_PRODUCT_NAME = "column_product_name"
    const val COLUMN_PRODUCT_IMAGE_URL = "column_product_image_url"
}