package com.example.testroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    lateinit var campaignDatabase: TestDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestDatabaseInstance.init(this)


        insertModel1()
        insertModel2()

        tetGetAllModel1()
        tetGetAllModel2()

        testGet(id = "1624954922850")


    }

    private fun tetGetAllModel2() {
        handler {
            val provider2 = RoomProvider(TestModel2::class.java)
            val list2 = provider2.getAll()
            Log.d("TAGTAG", "onCreate: provider.getAll() list2 ${list2.toString()}")
        }
    }

    private fun tetGetAllModel1() {
        handler {
            val provider = RoomProvider(TestModel1::class.java)
            val list = provider.getAll()
            list
            Log.d("TAGTAG", "onCreate: provider.getAll() list1 ${list.toString()}")
        }
    }

    private fun testGet(id: String) {
        handler {
            val provider = RoomProvider(TestModel1::class.java)
            val v =  provider.get(id)
            Log.d("TAGTAG", "onCreate: provider.get ${v.toString()}")
        }
    }

    private fun insertModel2() {
        handler {
            val provider = RoomProvider(TestModel2::class.java)
            provider.apply {
                deleteAll()

                insert(getTestObject2())
                insert(getTestObject2())
                insert(getTestObject2())
                insert(getTestObject2())
                insert(getTestObject2())
                insert(getTestObject2())
                insert(getTestObject2())
            }
        }
    }

    private fun insertModel1() {
        handler {
            val provider1 = RoomProvider(TestModel1::class.java)

            provider1.apply {
                deleteAll()

                insert(getTestObject())
                insert(getTestObject())
                insert(getTestObject())
                insert(getTestObject())
                insert(getTestObject())
                insert(getTestObject())
                insert(getTestObject())

            }
        }
    }


}

interface BaseLocalDataSource<T>{
    fun getAll(): List<T>
    fun deleteAll()
    fun insert(obj: T)
    fun get(id: String) : T
    fun insertAll(obj: List<T>)

}

class RoomProvider<T>(val clazz: Class<T>): BaseLocalDataSource<T> {
    val db = TestDatabaseInstance.mInstance

    val dao by lazy {
        getBaseDao()
    }

    override fun getAll(): List<T> {
        return dao.getAll()
    }

    override fun insert(obj: T){
        dao.insert(obj)
    }

    override fun insertAll(objList: List<T>){
        dao.insertAll(objList)
    }


    //dao factory
    private fun getBaseDao(): BaseDao<T>{
        return when (clazz.name) {
            TestModel1::class.java.name -> db.testDao() as BaseDao<T>
            TestModel2::class.java.name -> db.testDao2() as BaseDao<T>

            else -> throw Exception("no impl for this")
        }
    }

    override fun get(id: String): T {
        return dao.get(id)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

}

interface BaseDao<T>{
    @Insert
    open fun insert(campaign: T?){}
    @Insert @JvmSuppressWildcards
    fun insertAll(campaigns: List<T?>?)
    fun deleteAll()
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

