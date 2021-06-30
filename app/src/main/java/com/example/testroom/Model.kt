package com.example.testroom

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.realm.RealmObject
import java.util.*


@Entity
 class TestModel1 (
    @PrimaryKey
    var id: String = "",
    var description: String =  ""


)

@Entity
class TestModel2 (
    @PrimaryKey
    var id: String = "",
    var description: String =  "",
    var name: String =  ""


)


open class TestModel2Realm (

    var description: String =  "description description",
    var name: String =  "name name",
    @io.realm.annotations.PrimaryKey
    var id: String = UUID.randomUUID().toString()


): RealmObject()

open class Frog(var name: String = "",
                var id: Int = 0,
                @io.realm.annotations.PrimaryKey
                var id2: String = UUID.randomUUID().toString(),
                var species: String? = null,
                var owner: String? = null): RealmObject()


