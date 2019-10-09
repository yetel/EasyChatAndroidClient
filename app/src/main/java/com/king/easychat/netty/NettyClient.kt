package com.king.easychat.netty

import com.king.anetty.ANetty

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
class NettyClient {

  companion object{

    const val HOST = "192.168.100.54"
    const val PORT = 8000

  }

  var netty = ANetty(ChannelInitial(),true)

  /**
   * netty启动示例
   */
  fun start(){
    netty.takeIf { netty.isConnected }?.apply {
      start()
      return
    }

    netty.connect(HOST, PORT)

  }

}