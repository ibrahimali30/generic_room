package com.example.testroom

import android.content.Context
import androidx.room.*


@Database(
    entities = [TestModel1::class, TestModel2::class],
    version = 3,
    exportSchema = false
)
abstract class TestDatabase : RoomDatabase() {
    abstract fun testDao(): TestDao
    abstract fun testDao2(): TestDao2



}

object TestDatabaseInstance{
    lateinit var mInstance: TestDatabase


    fun init(context: Context): TestDatabase {
            mInstance = Room.databaseBuilder(context, TestDatabase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
        return mInstance
    }

}

