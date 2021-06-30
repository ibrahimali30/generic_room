package com.example.testroom.local_data_base.base

interface BaseLocalDataSource{
    fun <T> get(clazz: Class<T>, id: String) : T
    fun <T> insert(clazz: Class<T>, obj: T)
    fun <T> insertAll(clazz: Class<T>, obj: List<T>)
    fun <T> getAll(clazz: Class<T>): List<T>
    fun <T> delete(clazz: Class<T>, id: String)
    fun <T> deleteAll(clazz: Class<T>)
}