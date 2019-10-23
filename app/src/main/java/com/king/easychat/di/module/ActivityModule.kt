package com.king.easychat.di.module

import com.king.easychat.app.account.LoginActivity
import com.king.easychat.app.account.RegisterActivity
import com.king.easychat.app.account.UpdatePwdActivity
import com.king.easychat.app.chat.ChatActivity
import com.king.easychat.app.chat.GroupChatActivity
import com.king.easychat.app.friend.UserProfileActivity
import com.king.easychat.app.group.GroupProfileActivity
import com.king.easychat.app.home.HomeActivity
import com.king.easychat.app.me.about.AboutActivity
import com.king.easychat.app.me.user.ChangeUserInfoActivity
import com.king.easychat.app.me.user.UserInfoActivity
import com.king.easychat.app.photo.PhotoViewActivity
import com.king.easychat.app.search.SearchActivity
import com.king.easychat.app.splash.SplashActivity
import com.king.easychat.temp.TempActivity
import com.king.frame.mvvmframe.di.component.BaseActivitySubcomponent
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = [BaseActivitySubcomponent::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeTempActivity(): TempActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    abstract fun contributeChatActivity(): ChatActivity

    @ContributesAndroidInjector
    abstract fun contributeGroupChatActivity(): GroupChatActivity

    @ContributesAndroidInjector
    abstract fun contributePhotoViewActivity(): PhotoViewActivity

    @ContributesAndroidInjector
    abstract fun contributeUserInfoActivity(): UserInfoActivity

    @ContributesAndroidInjector
    abstract fun contributeChangeUserInfoActivity(): ChangeUserInfoActivity

    @ContributesAndroidInjector
    abstract fun contributeAboutActivity(): AboutActivity

    @ContributesAndroidInjector
    abstract fun contributeUpdatePwdActivity(): UpdatePwdActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributeUserProfileActivity(): UserProfileActivity

    @ContributesAndroidInjector
    abstract fun contributeGroupProfileActivity(): GroupProfileActivity

}