package com.king.easychat.di.module

import com.king.easychat.app.home.HomeActivity
import com.king.frame.mvvmframe.di.component.BaseActivitySubcomponent
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(subcomponents = [BaseActivitySubcomponent::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity
}