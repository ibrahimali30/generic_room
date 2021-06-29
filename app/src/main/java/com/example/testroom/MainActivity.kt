package com.example.testroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
val TAG = "TAGTAG"
    lateinit var campaignDatabase: TestDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestDatabaseInstance.init(this)


        insertModel1()
        insertModel2()
        testGetAllModel1()
        testGetAllModel2()
        testGet(id = "1624954922850")


    }

    private fun testGetAllModel2() {
        handler {
            val provider2 = RoomProvider()
            val list2 = provider2.getAll(TestModel2::class.java)
            Log.d(TAG, "onCreate: provider.getAll() list2 ${list2.toString()}")
        }
    }

    private fun testGetAllModel1() {
        handler {
            val provider = RoomProvider()
            val list = provider.getAll(TestModel1::class.java)
            list
            Log.d(TAG, "onCreate: provider.getAll() list1 ${list.toString()}")
        }
    }

    private fun testGet(id: String) {
        handler {
            val provider = RoomProvider()
            val v =  provider.get(TestModel1::class.java, id)
            Log.d(TAG, "onCreate: provider.get ${v.toString()}")
        }
    }

    private fun insertModel2() {

        val clazz = TestModel2::class.java
        handler {
            val provider = RoomProvider()
            provider.apply {

                insertAll(clazz,listOf(getTestObject2(), getTestObject2(), getTestObject2(), getTestObject2()))

//                deleteAll(clazz)
                return@handler

                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
                insert(clazz, getTestObject2())
            }
        }
    }

    private fun insertModel1() {
        val clazz = TestModel1::class.java
        handler {
            val provider = RoomProvider()
            provider.apply {
//deleteAll(clazz)
//return@handler
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
                insert(clazz, getTestObject())
            }
        }
    }


}

sealed class DataBaseParams<T>{
    class Insert<T>(obj: T): DataBaseParams<T>()
    class insertAll<T>(obj: List<T>): DataBaseParams<T>()
}

interface BaseLocalDataSource{
    fun <T> get(clazz: Class<T>, id: String) : T
    fun <T> insert(clazz: Class<T>, obj: T)
    fun <T> insertAll(clazz: Class<T>, obj: List<T>)
    fun <T> getAll(clazz: Class<T>): List<T>
    fun <T> delete(clazz: Class<T>, id: String)
    fun <T> deleteAll(clazz: Class<T>)
}

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

    class Provider2(): BaseLocalDataSource{
        override fun <T> get(clazz: Class<T>, id: String): T {
            TODO("Not yet implemented")
        }

        override fun <T> insert(clazz: Class<T>, obj: T) {
            TODO("Not yet implemented")
        }

        override fun <T> insertAll(clazz: Class<T>, obj: List<T>) {
            TODO("Not yet implemented")
        }

        override fun <T> getAll(clazz: Class<T>): List<T> {
            TODO("Not yet implemented")
        }

        override fun <T> delete(clazz: Class<T>, id: String) {
            TODO("Not yet implemented")
        }

        override fun <T> deleteAll(clazz: Class<T>) {
            TODO("Not yet implemented")
        }

    }



    //dao factory
    private fun<T> getBaseDao(clazz: Class<T>): BaseDao<T>{
        return when (clazz.name) {
            TestModel1::class.java.name -> db.testDao() as BaseDao<T>
            TestModel2::class.java.name -> db.testDao2() as BaseDao<T>

            else -> throw Exception("no impl for this")
        }
    }



}

interface BaseDao<T>{
    @Insert
    open fun insert(campaign: T?){}
    @Insert @JvmSuppressWildcards
    fun insertAll(campaigns: List<T?>?)
    fun deleteAll()
    fun delete(id2: String)
    fun getAll(): List<T>
    @Query("select * from TestModel1 where id =:id2")
    fun get(id2: String): T

}

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






fun getTestObject() = TestModel1(getCalenderString(), "TestObject1 description")
fun getTestObject2() = TestModel2(getCalenderString(), "TestObject2 description")

fun getCalenderString() = Calendar.getInstance().timeInMillis.toString()

fun handler(function: () -> Unit) {
    val mExecutorService = Executors.newSingleThreadExecutor();
    mExecutorService.execute {
        function()
    }
}

