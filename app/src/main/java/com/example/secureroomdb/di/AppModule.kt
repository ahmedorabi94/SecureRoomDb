package com.example.secureroomdb.di

import android.app.Application
import androidx.room.Room
import com.example.secureroomdb.db.AppDatabase
import com.example.secureroomdb.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory
import timber.log.Timber
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    private const val Key: String = "P@ssw0rd"


    @NotEncryptedDao
    @Singleton
    @Provides
    fun provideNotEncryptDao(@NotEncryptedDb database: AppDatabase): UserDao {
        return database.userDao
    }

    @EncryptedDao
    @Singleton
    @Provides
    fun provideEncryptDao(@EncryptedDb database: AppDatabase): UserDao {
        return database.userDao
    }


    @SecureMemoryEncryptedDao
    @Singleton
    @Provides
    fun provideMemoryEncryptDao(@SecureMemoryEncryptedDb database: AppDatabase): UserDao {
        return database.userDao
    }


    @NotEncryptedDb
    @Singleton
    @Provides
    fun provideNotEncryptedDb(app: Application): AppDatabase {

        return Room.databaseBuilder(app, AppDatabase::class.java, "users_not_encrypted.db")
            .fallbackToDestructiveMigration()
            .build()

    }


    @EncryptedDb
    @Singleton
    @Provides
    fun provideEncryptedDb(app: Application): AppDatabase {

        val passphrase =
            SQLiteDatabase.getBytes(Key.toCharArray())

        Timber.e(passphrase.toString())

        val factory = SupportFactory(passphrase, object : SQLiteDatabaseHook {
            override fun preKey(database: SQLiteDatabase?) = Unit

            override fun postKey(database: SQLiteDatabase?) {

              //  database?.rawExecSQL("PRAGMA cipher = 'aes-256-cbc'")
                database?.rawExecSQL("PRAGMA cipher_page_size = 4096")
              //  database?.rawExecSQL("PRAGMA cipher_use_hmac = OFF")

                database?.rawExecSQL("PRAGMA cipher_memory_security = OFF")

            }
        })


        return Room.databaseBuilder(app, AppDatabase::class.java, "users_encrypted.db")
            .openHelperFactory(factory)
            .build()

    }

    @SecureMemoryEncryptedDb
    @Singleton
    @Provides
    fun provideSecureMemoryEncryptedDb(app: Application): AppDatabase {

        val passphrase =
            SQLiteDatabase.getBytes(Key.toCharArray())

        val factory = SupportFactory(passphrase, object : SQLiteDatabaseHook {
            override fun preKey(database: SQLiteDatabase?) = Unit

            override fun postKey(database: SQLiteDatabase?) {

             //   database?.rawExecSQL("PRAGMA cipher = aes-256-cbc")
                database?.rawExecSQL("PRAGMA cipher_page_size = 4096")
             //   database?.rawExecSQL("PRAGMA cipher_use_hmac = OFF")

                database?.rawExecSQL("PRAGMA cipher_memory_security = ON")

            }
        })

        return Room.databaseBuilder(app, AppDatabase::class.java, "users_encrypted_memory.db")
            .openHelperFactory(factory)
            .build()

    }


}