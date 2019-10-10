package com.king.easychat.di.module

import com.king.easychat.app.account.LoginActivity
import com.king.easychat.app.account.RegisterActivity
import com.king.easychat.app.home.HomeActivity
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


}