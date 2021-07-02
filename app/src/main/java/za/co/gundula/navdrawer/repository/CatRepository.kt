package za.co.gundula.navdrawer.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import za.co.gundula.navdrawer.database.CatDao
import za.co.gundula.navdrawer.model.Cat

class CatRepository(private val catDao: CatDao) {

    val allCats: Flow<List<Cat>> = catDao.getCats()

    fun getCat(catId: String): Flow<Cat> = catDao.getCat(catId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cat: Cat) {
        catDao.insert(cat)
    }
}