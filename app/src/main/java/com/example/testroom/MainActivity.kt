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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread

val TAG = "TAGTAG"

class MainActivity : AppCompatActivity() {

    lateinit var provider: BaseLocalDataSource



//    val className = TestModel1::class.java
    val className = TestModel2Realm::class.java

    private fun initProvider() = handler {
        provider = RealmProvider(RealmSingeleton.realm)
//        provider = RoomProvider()
    }


//    fun getTestObject2Realm() = TestModel1(getCalenderString(), "TestObject1 description")
fun getTestObject2Realm() = TestModel2Realm( "TestObject2 description")
//fun getTestObject2Realm() = Frog("name",1,species = "species",owner = "owner")
fun getTestObject2() = TestModel2(getCalenderString(), "TestObject2 description")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TestDatabaseInstance.init(this)
        Realm.init(getApplicationContext());

        initProvider()

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
            Log.d(TAG, "getAll:forEach ${it.id} ")
            str.append("\n ${it.toString()} \n")
        }



        findViewById<TextView>(R.id.tvResult).text = str
    }


}



fun getCalenderString() = Calendar.getInstance().timeInMillis.toString()

fun handler(function: () -> Unit) {

    GlobalScope.launch {
        function()
    }

//    Executor.mExecutorService.execute {
//        function()
//    }
//    Executor.handler { function() }

}

object Executor{
    val job = CoroutineScope(Dispatchers.IO)
    val thread = thread {

    }
    val mExecutorService = Executors.newSingleThreadExecutor();

    fun handler(function: () -> Unit) {
        thread {
            function()
        }
    }
}

object RealmSingeleton{
    val config by lazy {
        RealmConfiguration.Builder()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
    }
    val realm = Realm.getInstance(config)
}

