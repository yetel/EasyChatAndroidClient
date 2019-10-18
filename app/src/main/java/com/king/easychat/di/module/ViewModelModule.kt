package com.king.easychat.di.module

import androidx.lifecycle.ViewModel
import com.king.easychat.app.account.LoginViewModel
import com.king.easychat.app.account.RegisterViewModel
import com.king.easychat.app.base.MessageModel
import com.king.easychat.app.base.MessageViewModel
import com.king.easychat.app.chat.ChatViewModel
import com.king.easychat.app.chat.GroupChatViewModel
import com.king.easychat.app.friend.FriendViewModel
import com.king.easychat.app.group.GroupViewModel
import com.king.easychat.app.home.HomeViewModel
import com.king.easychat.app.me.MeViewModel
import com.king.easychat.app.me.user.ChangeUserInfoViewModel
import com.king.easychat.app.me.user.UserInfoViewModel
import com.king.easychat.app.splash.SplashViewModel
import com.king.easychat.temp.TempViewModel
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel
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
    @ViewModelKey(TempViewModel::class)
    abstract fun bindTempViewModel(viewModel: TempViewModel) : ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    abstract fun bindChatViewModel(viewModel: ChatViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupChatViewModel::class)
    abstract fun bindGroupChatViewModel(viewModel: GroupChatViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindUserInfoViewModel(viewModel: UserInfoViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangeUserInfoViewModel::class)
    abstract fun bindChangeUserInfoViewModel(viewModel: ChangeUserInfoViewModel) : ViewModel


//    @Binds
//    @IntoMap
//    @ViewModelKey(MessageViewModel::class)
//    abstract fun bindMessageViewModel(viewModel: MessageViewModel<out MessageModel>) : ViewModel
}