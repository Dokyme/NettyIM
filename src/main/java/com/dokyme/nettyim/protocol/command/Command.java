package com.dokyme.nettyim.protocol.command;

public interface Command {
    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    byte LOGOUT_REQUEST = 7;
    byte LOGOUT_RESPONSE = 8;
    byte JOIN_GROUP_REQUEST = 9;
    byte JOIN_GROUP_RESPONSE = 10;
    byte JOIN_GROUP_BROADCAST = 11;
    byte QUIT_GROUP_REQUEST = 12;
    byte QUIT_GROUP_RESPONSE = 13;
    byte QUIT_GROUP_BROADCAST = 14;
    byte GROUP_MESSAGE_RESPONSE = 15;
    byte GROUP_MESSAGE_REQUEST = 16;
}
