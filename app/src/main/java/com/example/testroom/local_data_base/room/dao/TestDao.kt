package com.example.testroom.local_data_base.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testroom.TestModel1

@Dao
interface TestDao: BaseDao<TestModel1> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(campaign: TestModel1?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(campaigns: List<TestModel1?>?)

    @Query("DELETE FROM TestModel1")
    override fun deleteAll()

    @Query("select * from TestModel1")
    override fun getAll(): List<TestModel1>

    @Query("DELETE FROM TestModel1 where id=:id")
    override fun delete(id: String)


}