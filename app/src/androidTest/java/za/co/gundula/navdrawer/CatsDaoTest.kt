package za.co.gundula.navdrawer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import za.co.gundula.navdrawer.database.CatDao
import za.co.gundula.navdrawer.database.CatRoomDatabase
import za.co.gundula.navdrawer.model.Cat
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class CatsDaoTest {

    private lateinit var catDao: CatDao
    private lateinit var db: CatRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CatRoomDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        catDao = db.catDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCat() = runBlocking {
        val cat = Cat("id",anyString(),anyString(),anyString())
        catDao.insert(cat)
        val allCats = catDao.getCats().first()
        assertEquals(allCats[0].title, cat.title)
    }

    @Test
    @Throws(Exception::class)
    fun getAllCats() = runBlocking {
        val cat = Cat("id1",anyString(),anyString(),anyString())
        catDao.insert(cat)
        val cat2 =  Cat("id2",anyString(),anyString(),anyString())
        catDao.insert(cat2)
        val allCats = catDao.getCats().first()
        assertEquals(allCats[0].description, cat.description)
        assertEquals(allCats[1].description, cat2.description)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val cat = Cat("id3",anyString(),anyString(),anyString())
        catDao.insert(cat)
        val cat2 =  Cat("id4",anyString(),anyString(),anyString())
        catDao.insert(cat2)
        catDao.deleteAll()
        val allCats = catDao.getCats().first()
        assertTrue(allCats.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun testGetCat() = runBlocking {
        val cat = Cat("id5",anyString(),anyString(),anyString())
        catDao.insert(cat)
        val cat2 = catDao.getCat(cat.id).first()
        assertThat(cat2.id, equalTo(cat.id))
    }
}