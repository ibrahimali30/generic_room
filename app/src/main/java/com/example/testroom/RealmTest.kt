package com.example.testroom


import android.util.Log
import io.realm.*
import io.realm.annotations.PrimaryKey
import java.lang.Exception
import java.util.*


class RealmTest {

    val clazz = Frog::class.java
val config = RealmConfiguration.Builder()
    .allowQueriesOnUiThread(true)
    .allowWritesOnUiThread(true)
    .build()
    val realm = Realm.getInstance(config)

    val provider = RealmProvider(realm)

    init {

//        Watch_for_Object_Updates()
        insert()
    }

    private fun insert() {
        val v = getFrogObject()
        val listModel = listOf(getFrogObject(),getFrogObject(),getFrogObject(),getFrogObject(),getFrogObject(),getFrogObject())
        provider.insertAll(clazz, listModel)
        provider.insert(clazz, v)
//        val obj = provider.get(clazz,"a5521429-6176-4d9c-ab6c-924ef23317ca")
//        Log.d(TAG, "insert:provider.get ${obj?.id2} ")
//        Log.d(TAG, "insert:provider.get ${obj.name} ")
        val list = provider.getAll(clazz)
        list.forEach {
            Log.d(TAG, "provider.getAll: ${it.id2}")
        }
        Log.d(TAG, "provider.getAll: $list")


//        provider.deleteAll(clazz)
    }



    private fun Watch_for_Object_Updates() {
//
//        // configure and open a local realm
//
//// create an reference to a frog
//        var frog : Frog? = null
//// insert a new frog into the database and store it in our reference
//            frog = Frog()
//            frog.name = "Doctor Cucumber"
//            frog.id = 3
//            frog.species = "Tree Frog"
//            frog.owner = "Greg"
//        frog = toRealmObject(realm, frog)
//// create a listener that logs new changes to the frog
//        val listener = RealmObjectChangeListener { changedFrog: Frog?,
//                                                   changeSet: ObjectChangeSet? ->
//            if (changeSet!!.isDeleted) {
//                Log.i(TAG, "The frog was deleted")
//            } else {
//                for (fieldName in changeSet.changedFields) {
//                    Log.i(TAG, "Field '$fieldName' changed.")
//                }
//            }
//        }
//// attach the listener we just created to the frog
//        frog?.addChangeListener(listener)
//// update the frog
//        realm.executeTransaction { frog?.name = "Ronald" }

    }


}



class RealmProvider(val realm: Realm) : BaseLocalDataSource{
    override fun <T> get(clazz: Class<T>, id: String): T {

        val obj = realm.where(Frog::class.java)
            .equalTo("id2",id)
            .findFirst()

        return obj as T


    }

    override fun <T> insert(clazz: Class<T>, obj: T) {
        realm.executeTransaction {
            try {
                it.insertOrUpdate(obj as RealmObject)
            }catch (e:Exception){}
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

}


 fun getFrogObject(): Frog {
     return  Frog("name",1,species = "species",owner = "owner")
//    return toRealmObject(realm, frog)
 }


interface RealmInterface{
    val realmIdValue: String
    val realmIdKey: String
}
open class Frog(var name: String = "",
                var id: Int = 0,
                @PrimaryKey
                var id2: String = UUID.randomUUID().toString(),
                var species: String? = null,
                var owner: String? = null): RealmObject()