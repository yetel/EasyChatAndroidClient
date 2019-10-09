package com.king.easychat.netty.packet

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
open abstract class Packet {

    val version = 1

    lateinit var dateTime : String

    abstract fun messageType(): Int


    override fun toString(): String {
        return "Packet(version=$version, dateTime='$dateTime')"
    }


}