package com.king.easychat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.king.easychat.bean.Group

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
interface GroupDao {

    /**
     * 插入并去重
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(group: Group)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(groups: List<Group>)

    @Delete
    fun delete(group: Group)

    /**
     * 删除所有
     */
    @Query("DELETE FROM `group`")
    fun deleteAll()

    @Query("DELETE FROM `group` WHERE groupId = :groupId")
    fun delete(groupId: String)

    /**
     * 查询所有群组列表
     */
    @Query("SELECT * FROM `group`")
    fun getAllGroups(): LiveData<List<Group>>

    @Query("SELECT * FROM `group`")
    fun getGroups(): List<Group>

    /**
     * 查询群组列表
     */
    @Query("SELECT * FROM `group` ORDER BY groupId DESC LIMIT :count")
    fun getGroups(count: Int): LiveData<List<Group>>

    /**
     * 修改群组
     */
//    @Update
//    fun updateGroup(group: Group)
}