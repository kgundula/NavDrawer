package za.co.gundula.navdrawer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import za.co.gundula.navdrawer.model.Cat

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    private class WCatRoomDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            DATABASE_INSTANCE?.let { database ->
                scope.launch {
                    val catDao = database.catDao()

                    // Delete all content here.
                    catDao.deleteAll()

                }
            }
        }
    }

    companion object {

        @Volatile
        private var DATABASE_INSTANCE: CatRoomDatabase? = null

        fun getDatabase(context: Context): CatRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return DATABASE_INSTANCE ?: synchronized(this) {
                val datebaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    CatRoomDatabase::class.java,
                    "cat_database"
                ).build()
                DATABASE_INSTANCE = datebaseInstance
                // return instance
                datebaseInstance
            }
        }
    }

}