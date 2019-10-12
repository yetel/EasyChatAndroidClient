package com.king.easychat.app.friend

import com.king.easychat.dao.AppDatabase
import com.king.easychat.dao.UserDao
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.data.IDataRepository
import javax.inject.Inject

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class FriendModel @Inject constructor(repository: IDataRepository?) : BaseModel(repository){


    fun getUserDao(): UserDao{
        return getRoomDatabase(AppDatabase::class.java)
            .userDao()
    }

}