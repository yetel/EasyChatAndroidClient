package com.king.easychat.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.easychat.bean.User
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Database(entities = [User::class, GroupMessageResp::class, MessageResp::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun groupMessageDao(): GroupMessageDao
    abstract fun messageDao(): MessageDao
}