package com.king.easychat.di.module

import com.king.easychat.app.friend.FriendFragment
import com.king.easychat.app.group.GroupFragment
import com.king.easychat.app.home.HomeFragment
import com.king.easychat.app.me.MeFragment
import com.king.easychat.temp.TempFragment
import com.king.frame.mvvmframe.di.component.BaseFragmentSubcomponent
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = [BaseFragmentSubcomponent::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeTempFragment(): TempFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeFriendFragment(): FriendFragment

    @ContributesAndroidInjector
    abstract fun contributeGroupFragment(): GroupFragment

    @ContributesAndroidInjector
    abstract fun contributeMeFragment(): MeFragment

}