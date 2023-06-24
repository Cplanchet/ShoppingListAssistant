package me.cplanchet.shoppinglistassistant.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.cplanchet.shoppinglistassistant.data.daos.*
import me.cplanchet.shoppinglistassistant.data.entities.*

@Database(entities = [Aisle::class, Category::class, Item::class, AisleItem::class, AisleCategory::class, ListItem::class, ShoppingList::class, Store::class], version = 3, exportSchema = false)
abstract class ListDatabase: RoomDatabase() {
    abstract fun aisleDao(): AisleDao
    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao
    abstract fun aisleCategoryDao(): AisleCategoryDao
    abstract fun aisleItemDao(): AisleItemDao
    abstract fun listItemDao(): ListItemDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun storeDao(): StoreDao

    companion object {
        @Volatile
        private var Instance: ListDatabase? = null
        fun getDatabase(context: Context): ListDatabase {
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, ListDatabase::class.java, "list_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{ Instance = it }
            }
        }
    }
}