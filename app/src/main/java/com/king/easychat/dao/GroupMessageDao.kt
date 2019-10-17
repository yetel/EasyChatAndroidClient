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
    @Query("select groupId from GroupMessageDbo where  userId = :userId  group by groupId")
    fun queryAllGroups(userId : String) : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("select * from GroupMessageDbo where userId = :userId and groupId = :groupId order by dateTime desc limit 1")
    fun getLastMessageByGroupId(userId : String, groupId: String): GroupMessageDbo?

    /**
     * 根据群id获取好友的最近几条聊天记录
     */
    @Query("select * from GroupMessageDbo where  userId = :userId and groupId = :groupId order by dateTime desc limit :start, :pageSize")
    fun getGroupMessageByGroupId(userId : String, groupId : String, start : Int, pageSize : Int) : List<GroupMessageDbo>
}