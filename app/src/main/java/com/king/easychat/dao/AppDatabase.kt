package com.king.easychat.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.easychat.bean.User

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Database(entities = [User::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}