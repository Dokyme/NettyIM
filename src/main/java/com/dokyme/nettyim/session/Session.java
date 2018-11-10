package com.dokyme.nettyim.session;

import lombok.Data;

@Data
public class Session {
    private String username;
    private String userId;
    public Session(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }
}
