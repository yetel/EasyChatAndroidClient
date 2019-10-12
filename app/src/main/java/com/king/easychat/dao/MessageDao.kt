package com.king.easychat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.king.easychat.bean.User
import com.king.easychat.netty.packet.resp.GroupMessageResp
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
    fun insert(message: MessageResp)

    @Delete
    fun delete(message: MessageResp)

    /**
     * 删除所有
     */
    @Query("DELETE FROM MessageResp")
    fun queryAll()

    /**
     * 获取所有的聊天好友id
     */
    @Query(value = "select sender from MessageResp group by sender")
    fun queryAllFriends() : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("select * from MessageResp where sender = :sender order by dateTime desc limit 1")
    fun getLastMessageBySenderId(sender: String): MessageResp

    /**
     * 根据好友id获取好友的最近几条聊天记录
     */
    @Query(value = "select * from MessageResp where sender = :senderId order by dateTime desc limit :currentPage, :pageSize")
    fun getMessageBySenderId(senderId : String, currentPage : Int, pageSize : Int) : LiveData<List<MessageResp>>

}