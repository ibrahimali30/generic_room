package com.example.testroom

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class TestModel1 (
    @PrimaryKey
    var id: String,
    var description: String =  ""


)

@Entity
data class TestModel2 (
    @PrimaryKey
    var id: String,
    var description: String =  ""


)