package com.king.easychat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.king.easychat.bean.RecentGroupChat

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Dao
interface RecentGroupChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RecentGroupChat)

    @Query("DELETE FROM RecentGroupChat WHERE userId = :userId AND groupChatId = :groupChatId")
    fun delete(userId: String,groupChatId: String)

    @Query("DELETE FROM RecentGroupChat WHERE userId = :userId")
    fun delete(userId: String)

    @Query("SELECT * FROM RecentGroupChat WHERE userId = :userId")
    fun getRecentGroupChats(userId: String): List<RecentGroupChat>

}