package com.example.secureroomdb.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//@Entity(indices = arrayOf(Index(value = ["name", "address"])))
//@Entity(tableName = "user", indices = [Index(value = ["id"], unique = true)])
@Entity(tableName = "user")
data class User constructor(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "job") val job: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}