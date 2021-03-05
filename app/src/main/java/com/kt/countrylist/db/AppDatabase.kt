package com.kt.countrylist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kt.countrylist.db.Country


@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null
        private val DB_NAME = "MyCountries"

//        val migration_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "ALTER TABLE countries "
//                            + "RENAME COLUMN name TEXT"
//                )
//            }
//        }

        public fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DB_NAME
                        )
//                            .allowMainThreadQueries()
//                            .addMigrations(migration_1_2)
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
    abstract fun countryDao(): CountryDao?
}