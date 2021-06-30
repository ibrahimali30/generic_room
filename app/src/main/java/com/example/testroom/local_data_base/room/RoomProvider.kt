package com.example.testroom.local_data_base.room



import com.example.testroom.TestModel1
import com.example.testroom.TestModel2
import com.example.testroom.local_data_base.base.BaseLocalDataSource
import com.example.testroom.local_data_base.room.dao.BaseDao
import java.lang.Exception


class RoomProvider(): BaseLocalDataSource {
    val db = TestDatabaseInstance.mInstance

    override fun<T> getAll(clazz: Class<T>): List<T> {
        val dao  = getBaseDao(clazz)
        return dao.getAll()
    }

    override fun <T> deleteAll(clazz: Class<T>) {
        val dao  = getBaseDao(clazz)
        dao.deleteAll()
    }

    override fun <T> insert(clazz: Class<T>, obj: T) {
        val dao  = getBaseDao(clazz)
        dao.insert(obj)
    }


    override fun <T> get(clazz: Class<T>, id: String): T {
        val dao  = getBaseDao(clazz)
        return dao.get(id)
    }

    override fun <T> delete(clazz: Class<T>, id: String) {
        val dao  = getBaseDao(clazz)
        dao.delete(id)
    }

    override fun <T> insertAll(clazz: Class<T>, obj: List<T>) {
        val dao  = getBaseDao(clazz)
        dao.insertAll(obj)
    }




    //dao factory
    private fun<T> getBaseDao(clazz: Class<T>): BaseDao<T> {
        return when (clazz.name) {
            TestModel1::class.java.name -> db.testDao() as BaseDao<T>
            TestModel2::class.java.name -> db.testDao2() as BaseDao<T>

            else -> throw Exception("no impl for this")
        }
    }



}
