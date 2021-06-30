package com.example.testroom.local_data_base.room

import android.content.Context
import androidx.room.*
import com.example.testroom.TestModel1
import com.example.testroom.TestModel2
import com.example.testroom.local_data_base.room.dao.TestDao
import com.example.testroom.local_data_base.room.dao.TestDao2


@Database(
    entities = [TestModel1::class, TestModel2::class],
    version = 4,
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

