package com.king.easychat.app.base

import androidx.databinding.ViewDataBinding
import com.king.frame.mvvmframe.base.BaseFragment
import com.king.frame.mvvmframe.base.BaseModel
import com.king.frame.mvvmframe.base.BaseViewModel

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class BaseFragment<VM : BaseViewModel<out BaseModel>,VDB : ViewDataBinding> : BaseFragment<VM,VDB>(){

    
}