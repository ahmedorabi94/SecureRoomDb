package com.example.secureroomdb

import android.os.SystemClock
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secureroomdb.db.User
import com.example.secureroomdb.repo.MainRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _spinner = MutableLiveData(false)
    private val _users = MutableLiveData<List<User>>()
    private val _timeResults = MutableLiveData<String>()
    private val _timeResultsInsert = MutableLiveData<String>()

    val spinner get() = _spinner
    val users get() = _users
    val timeResults get() = _timeResults
    val timeResultsInsert get() = _timeResultsInsert

    fun getAllUsers() {

        viewModelScope.launch {
            try {
                _spinner.value = true
                val start = SystemClock.elapsedRealtime()

                _users.value = repository.getAllUsers()

                val stop = SystemClock.elapsedRealtime()
                val time = (stop - start)
                _timeResults.value = "$time"

            } catch (e: Exception) {
                Timber.e(e)
                _spinner.value = false
            } finally {
                _spinner.value = false
            }
        }
    }


    fun insert() {
        viewModelScope.launch {
            try {
                _spinner.value = true

                val start = SystemClock.elapsedRealtime()

                val rowId = repository.insertUser(
                    User(
                        "Ahmed Orabi",
                        "eng.ahmedorabi@gmail.com",
                        "Cairo",
                        26,
                        "Android Developer"
                    )
                )

                val stop = SystemClock.elapsedRealtime()
                val time = (stop - start)
                _timeResultsInsert.value = "$time"

                Timber.e("Row ID $rowId")

            } catch (e: Exception) {
                Timber.e(e)
                _spinner.value = false
            } finally {
                _spinner.value = false
            }
        }
    }


    fun insertAllUsers() {
        viewModelScope.launch {
            try {
                _spinner.value = true

                val list = mutableListOf<User>()

                for (i in 0..100000) {
                    list.add(
                        User(
                            "Ahmed Orabi$i",
                            "eng.ahmedorabi@gmail.com$i",
                            "Cairo$i",
                            26 + i,
                            "Android Developer$i"
                        )
                    )
                }

                val start = SystemClock.elapsedRealtime()

                val rowId = repository.insertUsers(list)

                val stop = SystemClock.elapsedRealtime()
                val time = (stop - start)
                _timeResultsInsert.value = "$time"


                Timber.e("Row ID $rowId")

            } catch (e: Exception) {
                Timber.e(e)
                _spinner.value = false
            } finally {
                _spinner.value = false
            }
        }
    }


    fun setType(value: String) {
        repository.type = value
    }

}