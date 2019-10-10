package com.king.easychat.netty

import com.king.anetty.ANetty
import timber.log.Timber

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class NettyClient {

  private var netty = ANetty(ChannelInitial(),true)

  companion object{

    private const val HOST = "120.79.175.213"
    private const val PORT = 8000

    val INSTANCE : NettyClient by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED) { NettyClient() }

  }


  /**
   * netty启动
   */
  fun connect(){
    netty.connect(HOST, PORT)

  }

  /**
   * 发送消息
   */
  fun sendMessage(msg: Any){
    Timber.d(msg.toString())
    netty.sendMessage(msg)
  }

  /**
   * 重连
   */
  fun reconnect(delayMillis: Long){
    netty.reconnect(delayMillis)
  }

  /**
   * 断开连接
   */
  fun disconnect(){
    netty.disconnect()
  }

  /**
   * 关闭
   */
  fun close(){
    netty.close()
  }

}