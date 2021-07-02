package za.co.gundula.navdrawer

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import za.co.gundula.navdrawer.database.CatRoomDatabase
import za.co.gundula.navdrawer.repository.CatRepository

class NavDrawerApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { CatRoomDatabase.getDatabase(this) }
    val repository by lazy { CatRepository(database.catDao()) }
}