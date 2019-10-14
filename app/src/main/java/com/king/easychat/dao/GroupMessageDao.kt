package com.king.easychat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.king.easychat.bean.GroupMessageDbo
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
    fun insert(message: GroupMessageDbo)

    @Delete
    fun delete(message: GroupMessageDbo)

    /**
     * 删除所有
     */
    @Query("DELETE FROM GroupMessageDbo")
    fun deleteAll()

    /**
     * 获取所有的群聊id
     */
    @Query(value = "select groupId from GroupMessageDbo group by groupId")
    fun queryAllGroups() : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("select * from GroupMessageDbo where groupId = :groupId order by dateTime desc limit 1")
    fun getLastMessageByGroupId(groupId: String): GroupMessageDbo

    /**
     * 根据群id获取好友的最近几条聊天记录
     */
    @Query(value = "select * from GroupMessageDbo where sender = :groupId order by dateTime desc limit :currentPage, :pageSize")
    fun getGroupMessageByGroupId(groupId : String, currentPage : Int, pageSize : Int) : LiveData<List<GroupMessageDbo>>
}