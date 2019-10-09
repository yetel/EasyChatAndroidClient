package com.king.easychat.util

import org.greenrobot.eventbus.EventBus

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object Event {

    private val event = EventBus.getDefault()

    fun sendEvent(msg: Any,isSticky: Boolean = false){
        if(isSticky){
            event.postSticky(msg)
        }else{
            event.post(msg)
        }

    }

    fun registerEvent(obj: Any){
        event.register(obj)
    }

    fun unregisterEvent(obj: Any){
        event.unregister(obj)
    }

}