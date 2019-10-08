package com.king.easychat.di.component

import com.king.easychat.App
import com.king.easychat.di.module.ApplicationModule
import com.king.frame.mvvmframe.di.component.AppComponent
import com.king.frame.mvvmframe.di.scope.ApplicationScope
import dagger.Component

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@ApplicationScope
@Component(dependencies = [AppComponent::class], modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: App)
}