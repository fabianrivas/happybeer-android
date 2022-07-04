package com.fab.happybeer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fab.happybeer.core.AppConstants
import com.fab.happybeer.data.local.favorite.FavoriteDao
import com.fab.happybeer.data.local.user.UserDao
import com.fab.happybeer.data.model.Favorite
import com.fab.happybeer.data.model.User

@Database(
    entities = [User::class, Favorite::class],
    version = AppConstants.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                AppConstants.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}