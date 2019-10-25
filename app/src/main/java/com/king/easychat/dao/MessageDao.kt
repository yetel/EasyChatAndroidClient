package com.king.easychat.dao

import androidx.room.*
import com.king.easychat.bean.MessageDbo

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
interface MessageDao {
    /**
     * 插入并去重
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MessageDbo)

    @Delete
    fun delete(message: MessageDbo)

    /**
     * 删除所有
     */
    @Query("DELETE FROM MessageDbo")
    fun deleteAll()

    /**
     * 获取所有的聊天好友id
     */
    @Query("SELECT sender FROM MessageDbo WHERE userId = :userId GROUP BY sender")
    fun queryAllFriends(userId : String) : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("SELECT * FROM MessageDbo WHERE  userId = :userId AND (sender = :sender OR receiver = :receiver) ORDER BY dateTime DESC LIMIT 1")
    fun getLastMessageBySenderId(userId : String, sender: String,receiver: String): MessageDbo?

    /**
     * 根据好友id获取好友的最近几条聊天记录
     */
    @Query("SELECT * FROM MessageDbo WHERE userId = :userId AND (sender = :senderId OR receiver = :receiver) ORDER BY dateTime DESC LIMIT :start, :pageSize")
    fun getMessageBySenderId(userId : String,senderId : String,receiver : String, start : Int, pageSize : Int) : List<MessageDbo>

    /**
     * 查询未读消息记录数
     */
    @Query("SELECT COUNT(*) FROM MessageDbo WHERE  userId = :userId AND sender = :senderId AND read = '0'")
    fun getUnreadNumBySenderId(userId : String, senderId : String) : Int

    /**
     * 查询未读消息记录数
     */
    @Query("SELECT id FROM MessageDbo WHERE  userId = :userId AND sender = :senderId AND read = '0' GROUP BY id")
    fun getUnreadNumBySenderId1(userId : String, senderId : String) : List<Long>

    /**
     * 更新为已读消息
     */
    @Query("UPDATE MessageDbo SET read = '1' WHERE userId =:userId AND (sender = :senderId OR receiver = :receiver)")
    fun updateRead(userId : String, senderId : String,receiver : String) : Int

    /**
     * 更新为已读消息
     */
    @Query("UPDATE MessageDbo SET read = '1' WHERE userId =:userId")
    fun updateRead(userId : String) : Int
}