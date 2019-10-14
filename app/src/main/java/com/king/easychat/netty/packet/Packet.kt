package com.king.easychat.netty.packet

import com.king.base.util.TimeUtils

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
abstract class Packet {

    constructor(): this(TimeUtils.formatDate(System.currentTimeMillis(),TimeUtils.FORMAT_Y_TO_S))

    constructor(dateTime: String){
        this.dateTime = dateTime
    }

    var version = 1

    var dateTime : String

    abstract fun packetType(): Int


    override fun toString(): String {
        return "Packet(version=$version, dateTime='$dateTime')"
    }


}