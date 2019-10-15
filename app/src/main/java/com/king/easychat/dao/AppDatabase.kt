package com.king.easychat.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.king.easychat.bean.Group
import com.king.easychat.bean.GroupMessageDbo
import com.king.easychat.bean.MessageDbo
import com.king.easychat.bean.User
import com.king.easychat.netty.packet.resp.GroupMessageResp
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Database(entities = [User::class, Group::class, MessageDbo::class,GroupMessageDbo::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMessageDao(): GroupMessageDao
    abstract fun messageDao(): MessageDao
}