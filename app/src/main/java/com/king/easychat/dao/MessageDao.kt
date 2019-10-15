package com.king.easychat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.king.easychat.bean.MessageDbo
import com.king.easychat.netty.packet.resp.MessageResp

/**
 * @author Zed
 * date: 2019/10/12.
 * description:
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
    @Query("select sender from MessageDbo where userId = :userId group by sender")
    fun queryAllFriends(userId : String) : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("select * from MessageDbo where  userId = :userId and (sender = :sender or receiver = :receiver) order by dateTime desc limit 1")
    fun getLastMessageBySenderId(userId : String, sender: String,receiver: String): MessageDbo

    /**
     * 根据好友id获取好友的最近几条聊天记录
     */
    @Query("select * from MessageDbo where userId = :userId and (sender = :senderId or receiver = :receiver) order by dateTime desc limit :start, :pageSize")
    fun getMessageBySenderId(userId : String, senderId : String,receiver : String, start : Int, pageSize : Int) : List<MessageDbo>

}