package com.example.testroom.local_data_base.room.dao

import androidx.room.Insert
import androidx.room.Query

interface BaseDao<T>{
    @Insert
    open fun insert(campaign: T?){}
    @Insert
    @JvmSuppressWildcards
    fun insertAll(campaigns: List<T?>?)
    fun deleteAll()
    fun delete(id2: String)
    fun getAll(): List<T>
    @Query("select * from TestModel1 where id =:id")
    fun get(id: String): T

}