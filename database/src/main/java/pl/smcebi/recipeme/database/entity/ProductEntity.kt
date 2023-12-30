package pl.smcebi.recipeme.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_BRAND
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_EAN
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_IMAGE_URL
import pl.smcebi.recipeme.database.DatabaseConstants.COLUMN_PRODUCT_NAME
import pl.smcebi.recipeme.database.DatabaseConstants.TABLE_PRODUCTS

@Entity(tableName = TABLE_PRODUCTS)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_PRODUCT_EAN)
    val ean: String,
    @ColumnInfo(name = COLUMN_PRODUCT_BRAND)
    val brand: String,
    @ColumnInfo(name = COLUMN_PRODUCT_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_PRODUCT_IMAGE_URL)
    val imageUrl: String,
)