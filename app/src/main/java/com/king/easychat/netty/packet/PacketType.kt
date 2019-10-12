package com.king.easychat.netty.packet

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
object PacketType {

    const val LOGIN_REQ =  1
    const val LOGIN_RESP = 2

    const val LOGOUT_REQ =  3
    const val LOGOUT_RESP = 4

    const val SEND_MESSAGE_REQ =  5
    const val SEND_MESSAGE_RESP = 6

    const val ADD_FRIEND_REQ =  7
    const val ADD_FRIEND_RESP = 8
    const val ADD_USER_SELF_RESP = 9
    const val CREATE_GROUP_REQ = 10
    const val CREATE_GROUP_RESP = 11
    const val INVITE_GROUP_REQ = 12
    const val INVITE_GROUP_RESP = 13
    const val INVITE_GROUP_SELF_RESP = 14
    const val GROUP_MESSAGE_REQ = 15
    const val GROUP_MESSAGE_RESP = 16
    const val ACCEPT_GROUP_REQ = 17
    const val ACCEPT_GROUP_RESP = 18
    const val ACCEPT_REQ = 19
    const val ACCEPT_RESP = 20
    const val REGISTER_REQ = 21
    const val REGISTER_RESP = 22
    const val UPDATE_PASSWD_REQ = 23
    const val UPDATE_PASSWD_RESP = 24
    const val MESSAGE_SELF_RESP = 25
    const val HEART_BEAT_REQ = 26
    const val HEART_BEAT_RESP = 27


}