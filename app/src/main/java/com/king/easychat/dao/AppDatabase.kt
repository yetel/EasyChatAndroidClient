package com.king.easychat.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.easychat.bean.*

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Database(entities = [User::class, Group::class, MessageDbo::class,GroupMessageDbo::class, RecentChat::class,RecentGroupChat::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMessageDao(): GroupMessageDao
    abstract fun messageDao(): MessageDao

    abstract fun recentChatDao(): RecentChatDao
    abstract fun recentGroupChatDao(): RecentGroupChatDao
}