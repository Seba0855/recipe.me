package pl.smcebi.recipeme.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pl.smcebi.recipeme.database.DatabaseConstants.TABLE_PRODUCTS
import pl.smcebi.recipeme.database.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM $TABLE_PRODUCTS")
    fun getProductsStream(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Transaction
    @Delete
    suspend fun deleteProduct(product: ProductEntity)
}