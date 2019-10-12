package com.king.easychat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.king.easychat.netty.packet.resp.GroupMessageResp

/**
 * @author Zed
 * date: 2019/10/12.
 * description:
 */
@Dao
interface GroupMessageDao {
    /**
     * 插入并去重
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: GroupMessageResp)

    @Delete
    fun delete(message: GroupMessageResp)

    /**
     * 删除所有
     */
    @Query("DELETE FROM GroupMessageResp")
    fun deleteAll()

    /**
     * 获取所有的群聊id
     */
    @Query(value = "select groupId from GroupMessageResp group by groupId")
    fun queryAllGroups() : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("select * from GroupMessageResp where groupId = :groupId order by dateTime desc limit 1")
    fun getLastMessageByGroupId(groupId: String): GroupMessageResp

    /**
     * 根据群id获取好友的最近几条聊天记录
     */
    @Query(value = "select * from GroupMessageResp where sender = :groupId order by dateTime desc limit :currentPage, :pageSize")
    fun getGroupMessageByGroupId(groupId : String, currentPage : Int, pageSize : Int) : LiveData<List<GroupMessageResp>>
}