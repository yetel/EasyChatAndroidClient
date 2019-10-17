package com.king.easychat.dao

import androidx.room.*
import com.king.easychat.bean.RecentChat

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
interface RecentChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RecentChat)

    @Query("DELETE FROM RecentChat WHERE userId = :userId AND chatId = :chatId")
    fun delete(userId: String,chatId: String)

    @Query("DELETE FROM RecentChat WHERE userId = :userId")
    fun delete(userId: String)

    @Query("SELECT * FROM RecentChat WHERE userId = :userId")
    fun getRecentChats(userId: String): List<RecentChat>

}