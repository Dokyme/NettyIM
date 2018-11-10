package com.dokyme.nettyim.protocol;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String, ConsoleCommand> consoleCommandMap;

    public ConsoleCommandManager() {
        consoleCommandMap = new HashMap<>();
        consoleCommandMap.put("sendToUser", null);
        consoleCommandMap.put("logout", null);
        consoleCommandMap.put("createGroup", null);
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {

    }
}
