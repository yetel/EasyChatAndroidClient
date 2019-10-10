package com.king.easychat.di.module

import androidx.lifecycle.ViewModel
import com.king.easychat.app.account.LoginViewModel
import com.king.easychat.app.account.RegisterViewModel
import com.king.easychat.app.friend.FriendViewModel
import com.king.easychat.app.group.GroupViewModel
import com.king.easychat.app.home.HomeViewModel
import com.king.easychat.app.me.MeViewModel
import com.king.easychat.app.splash.SplashViewModel
import com.king.frame.mvvmframe.di.scope.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: RegisterViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FriendViewModel::class)
    abstract fun bindFriendViewModel(viewModel: FriendViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel::class)
    abstract fun bindGroupViewModel(viewModel: GroupViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MeViewModel::class)
    abstract fun bindMeViewModel(viewModel: MeViewModel) : ViewModel
}