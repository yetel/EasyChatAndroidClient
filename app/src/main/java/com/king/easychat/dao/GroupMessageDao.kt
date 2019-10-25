package com.king.easychat.dao

import androidx.room.*
import com.king.easychat.bean.GroupMessageDbo

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
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
    @Query("SELECT groupId FROM GroupMessageDbo WHERE  userId = :userId  GROUP BY groupId")
    fun queryAllGroups(userId : String) : List<String>

    /**
     * 根据时间倒序查询最近聊天的几个用户
     */
    @Query("SELECT * FROM GroupMessageDbo WHERE userId = :userId AND groupId = :groupId order by dateTime desc limit 1")
    fun getLastMessageByGroupId(userId : String, groupId: String): GroupMessageDbo?

    /**
     * 根据群id获取好友的最近几条聊天记录
     */
    @Query("SELECT * FROM GroupMessageDbo WHERE  userId = :userId AND groupId = :groupId order by dateTime desc limit :start, :pageSize")
    fun getGroupMessageByGroupId(userId : String, groupId : String, start : Int, pageSize : Int) : List<GroupMessageDbo>

    /**
     * 查询未读消息记录数
     */
    @Query("SELECT COUNT(*) FROM GroupMessageDbo WHERE  userId = :userId AND groupId = :groupId AND read = '0'")
    fun getUnreadNumByGroupId(userId : String, groupId : String) : Int

    /**
     * 查询未读消息记录数
     */
    @Query("SELECT id FROM GroupMessageDbo WHERE  userId = :userId AND groupId = :groupId AND read = '0'")
    fun getUnreadNumByGroupId1(userId : String, groupId : String) : List<Long>

    /**
     * 更新为已读消息
     */
    @Query("UPDATE GroupMessageDbo SET read = '1' WHERE userId =:userId AND groupId = :groupId")
    fun updateRead(userId : String, groupId : String) : Int

    /**
     * 更新为已读消息
     */
    @Query("UPDATE GroupMessageDbo SET read = '1' WHERE userId =:userId")
    fun updateRead(userId : String) : Int


}