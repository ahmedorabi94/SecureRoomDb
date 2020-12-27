package com.example.secureroomdb


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.secureroomdb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding


    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        setObservers()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->


            when (checkedId) {
                R.id.noEncryptBtn -> {
                    id = 1
                    Timber.e("noEncryptBtn")
                    viewModel.setType(AppConstants.NOTENCRYPT)
                }
                R.id.encryptBtn -> {
                    id = 2
                    Timber.e("encryptBtn")
                    viewModel.setType(AppConstants.ENCRYPT)

                }
                R.id.encryptMemoryBtn -> {
                    id = 3
                    Timber.e("encryptMemoryBtn")
                    viewModel.setType(AppConstants.MEMORYENCRYPT)

                }

            }

        }



        binding.insertBtn.setOnClickListener {

            if (id != -1) {
                viewModel.insertAllUsers()
            } else {
                Toast.makeText(this, "Choose Select Type", Toast.LENGTH_SHORT).show()
            }

        }

        binding.insertOneRecordBtn.setOnClickListener {

            if (id != -1) {
                viewModel.insert()
            } else {
                Toast.makeText(this, "Choose Select Type", Toast.LENGTH_SHORT).show()
            }

        }



        binding.selectBtn.setOnClickListener {

            if (id != -1) {
                viewModel.getAllUsers()
            } else {
                Toast.makeText(this, "Choose Select Type", Toast.LENGTH_SHORT).show()
            }

        }

    }

    @SuppressLint("SetTextI18n")
    fun setObservers() {
        viewModel.spinner.observe(this, { isShow ->
            if (isShow) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE

            }
        })


        viewModel.users.observe(this, { users ->

            users?.let {
                Timber.e("$users.size")

            }

        })


        viewModel.timeResults.observe(this, { time ->

            time?.let {
                Timber.e("Select Time in ms : $time")
                binding.selectTimeTv.text = "Select Time in ms : $time"

            }

        })

        viewModel.timeResultsInsert.observe(this, { time ->

            time?.let {
                Timber.e("Insert Time in ms : $time")
                binding.insertTimeTv.text = "Insert Time in ms : $time"

            }

        })

    }


}