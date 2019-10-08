package com.king.easychat.di.module

import com.king.frame.mvvmframe.di.module.ViewModelFactoryModule
import dagger.Module

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module(includes = [ViewModelFactoryModule::class,ViewModelModule::class,ActivityModule::class,FragmentModule::class])
class ApplicationModule {

}