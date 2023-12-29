package pl.smcebi.recipeme.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_BRAND
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_ID
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_IMAGE_URL
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_NAME
import pl.smcebi.recipeme.database.DatabaseConstants.TABLE_PRODUCTS

@Entity(tableName = TABLE_PRODUCTS)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_PRODUCT_ID)
    val productId: Int = 0,
    @ColumnInfo(name = COLUMN_PRODUCT_BRAND)
    val brand: String,
    @ColumnInfo(name = COLUMN_PRODUCT_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_PRODUCT_IMAGE_URL)
    val imageUrl: String,
)