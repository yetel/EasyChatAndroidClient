package com.king.easychat.dao

import androidx.room.*
import com.king.easychat.bean.User
import androidx.lifecycle.LiveData



/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
interface UserDao {

    /**
     * 插入并去重
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Delete
    fun delete(user: User)

    /**
     * 删除所有
     */
    @Query("DELETE FROM User")
    fun queryAll()

    /**
     * 查询所有用户列表
     */
    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    /**
     * 查询用户列表
     */
    @Query("SELECT * FROM User ORDER BY userId DESC LIMIT :count")
    fun getUsers(count: Int): LiveData<List<User>>

    /**
     * 修改用户
     */
//    @Update
//    fun updateUser(user: User)
}