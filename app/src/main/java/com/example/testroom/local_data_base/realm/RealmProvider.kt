package com.example.testroom.local_data_base.realm

import com.example.testroom.Frog
import com.example.testroom.TestModel1
import com.example.testroom.TestModel2
import com.example.testroom.TestModel2Realm
import com.example.testroom.local_data_base.base.BaseLocalDataSource
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import java.lang.Exception

class RealmProvider(val realm: Realm) : BaseLocalDataSource {


    override fun <T> get(clazz: Class<T>, id: String): T {

        val obj = realm.where(clazz as Class<RealmModel>)
            .equalTo(getIdKey(clazz), id)
            .findFirst()

        return obj as T


    }

    override fun <T> insert(clazz: Class<T>, obj: T) {
        realm.executeTransaction {
            val frog = obj as RealmObject
            it.insertOrUpdate(frog)

        }
    }

    override fun <T> insertAll(clazz: Class<T>, obj: List<T>) {
        realm.executeTransaction {
            it.insertOrUpdate(obj as List<RealmObject>)
        }
    }

    override fun <T> getAll(clazz: Class<T>): List<T> {
        val results = realm.where(clazz as Class<RealmModel>).findAll()
        return results.toList() as List<T>
    }

    override fun <T> delete(clazz: Class<T>, id: String) {
        TODO("Not yet implemented")
    }

    override fun <T> deleteAll(clazz: Class<T>) {
        realm.executeTransaction {
            it.delete(clazz as Class<out RealmModel>)
        }

    }

    fun<T> getIdKey(clazz: Class<T>): String{
        return when(clazz.name){
            TestModel1::class.java.name ->"id"
            TestModel2::class.java.name ->"id"
            Frog::class.java.name ->"id2"

            else -> "id"
        }
    }

}
