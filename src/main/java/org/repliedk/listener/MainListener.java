package org.repliedk.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class MainListener implements Listener {

    @EventHandler
    public void handlerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome to the server! Have a wonderful time exploring and playing.");
    }

}