package com.example.secureroomdb.db

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Insert
    suspend fun insertUser(user: User): Long

    @Insert
    suspend fun insertUserList(users: List<User>): List<Long>


    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Long): User

    @Query("SELECT * FROM user WHERE email LIKE :find")
    fun findByEmail(find: String): List<User>

    @Query("SELECT * FROM user WHERE job LIKE :find")
    fun findByJob(find: String): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg user: User)


    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("DELETE FROM user")
    fun deleteAll()
}