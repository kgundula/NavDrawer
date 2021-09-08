package za.co.gundula.navdrawer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import za.co.gundula.navdrawer.model.Cat

@Dao
interface CatDao {

    @Query("SELECT * FROM cat_table ORDER BY id ASC")
    fun getCats(): Flow<List<Cat>>

    @Query("SELECT * FROM cat_table WHERE id=:id")
    fun getCat(id: String): Flow<Cat>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Query("DELETE FROM cat_table")
    suspend fun deleteAll()
}