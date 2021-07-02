package za.co.gundula.navdrawer.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_table")
class Cat(@PrimaryKey @ColumnInfo(name = "id") val id: String, val imageUrl: String, val title: String, val description: String)