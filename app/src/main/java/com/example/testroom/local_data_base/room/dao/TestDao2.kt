package com.example.testroom.local_data_base.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testroom.TestModel2

@Dao
interface TestDao2: BaseDao<TestModel2> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(campaign: TestModel2?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(campaigns: List<TestModel2?>?)

    @Query("DELETE FROM TestModel2")
    override fun deleteAll()

    @Query("select * from TestModel2")
    override fun getAll(): List<TestModel2>

    @Query("select * from TestModel2 where id =:id2")
    override fun get(id2: String): TestModel2

    @Query("DELETE FROM TestModel2 where id=:id")
    override fun delete(id: String)
}
