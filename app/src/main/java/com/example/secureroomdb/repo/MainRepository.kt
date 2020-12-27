package com.example.secureroomdb.repo

import com.example.secureroomdb.AppConstants
import com.example.secureroomdb.db.User
import com.example.secureroomdb.db.UserDao
import com.example.secureroomdb.di.EncryptedDao
import com.example.secureroomdb.di.NotEncryptedDao
import com.example.secureroomdb.di.SecureMemoryEncryptedDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MainRepository @Inject constructor(
    @NotEncryptedDao private val userDao: UserDao,
    @EncryptedDao private val userDaoEncrypt: UserDao,
    @SecureMemoryEncryptedDao private val userDaoSecureEncrypt: UserDao
) {


    var type: String = ""


    suspend fun insertUser(users: User): Long {

        Timber.e("insertUser $type")

        when (type) {
            AppConstants.NOTENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDao.insertUser(users)
                }
            }

            AppConstants.ENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoEncrypt.insertUser(users)
                }
            }
            AppConstants.MEMORYENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoSecureEncrypt.insertUser(users)
                }
            }
            else -> {
                return -1
            }

        }


    }

    suspend fun insertUsers(users: List<User>): List<Long> {

        Timber.e("insertAllUsers $type")


        when (type) {
            AppConstants.NOTENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDao.insertUserList(users)
                }
            }

            AppConstants.ENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoEncrypt.insertUserList(users)
                }
            }
            AppConstants.MEMORYENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoSecureEncrypt.insertUserList(users)
                }
            }
            else -> {
                return emptyList()
            }
        }

    }


    suspend fun getAllUsers(): List<User> {


        Timber.e("getAllUsers $type")


        when (type) {
            AppConstants.NOTENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDao.getAllUsers()
                }
            }

            AppConstants.ENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoEncrypt.getAllUsers()
                }
            }
            AppConstants.MEMORYENCRYPT -> {
                return withContext(Dispatchers.IO) {
                    userDaoSecureEncrypt.getAllUsers()
                }
            }
            else -> {
                return emptyList()
            }
        }


    }


//    fun setTypeValue(value: String) {
//        this.type = value
//    }

}