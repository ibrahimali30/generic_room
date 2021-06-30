package com.example.testroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.room.*
import com.example.testroom.local_data_base.base.BaseLocalDataSource
import com.example.testroom.local_data_base.realm.RealmProvider
import com.example.testroom.local_data_base.room.RoomProvider
import com.example.testroom.local_data_base.room.TestDatabaseInstance
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread

val TAG = "TAGTAG"

class MainActivity : AppCompatActivity() {

    object RealmSingeleton{
        val config by lazy {
            RealmConfiguration.Builder()
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build()
        }
        val realm = Realm.getInstance(config)
    }


//    val className = TestModel1::class.java
    val className = TestModel2Realm::class.java


    lateinit var provider: BaseLocalDataSource

//    fun getTestObject2Realm() = TestModel1(getCalenderString(), "TestObject1 description")
    fun getTestObject2() = TestModel2(getCalenderString(), "TestObject2 description")
fun getTestObject2Realm() = TestModel2Realm( "TestObject2 description")
//fun getTestObject2Realm() = Frog("name",1,species = "species",owner = "owner")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestDatabaseInstance.init(this)
        Realm.init(getApplicationContext());

        handler {
            provider  = RealmProvider(RealmSingeleton.realm)
//            provider  = RoomProvider()
        }

//        RealmTest(provider)

//        insertModel1()
//        testGetAllModel1()
//        testGet(id = "262efa34-0bdf-4ffa-a22c-ec72bb3c6871")


    }


    private fun testGetAllModel1() {
        handler {
            val list = provider.getAll(className)
            list
            Log.d(TAG, "onCreate: provider.getAll() list1 ${list.toString()}")
        }
    }

    private fun testGet(id: String) {
        handler {
            val v =  provider.get(className, id)
            Log.d(TAG, "onCreate: provider.get ${v.toString()}")
        }
    }


    private fun insertModel1() {
        val clazz = className
        handler {
//            val provider = RealmProvider(realm)
            provider.apply {
deleteAll(clazz)
return@handler
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
                insert(clazz, getTestObject2Realm())
            }
        }
    }

    fun insert(view: View) = handler {
        provider.insert(className, getTestObject2Realm())
    }
    fun deleteAll(view: View) = handler {
        provider.deleteAll(className)
    }
    fun getAll(view: View) = handler {

        val list = provider.getAll(className)

        val str = StringBuilder()

        list.forEach {
            str.append("\n ${it.toString()} \n")
        }



        findViewById<TextView>(R.id.tvResult).text = str
    }


}



fun getCalenderString() = Calendar.getInstance().timeInMillis.toString()

fun handler(function: () -> Unit) {

    CoroutineScope(Dispatchers.IO).launch {
        function()
    }

//    Executor.mExecutorService.execute {
//        function()
//    }
//    Executor.handler { function() }

}

object Executor{
    val thread = thread {

    }
    val mExecutorService = Executors.newSingleThreadExecutor();

    fun handler(function: () -> Unit) {
        thread {
            function()
        }
    }
}

