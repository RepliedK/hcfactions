package org.repliedk.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import org.repliedk.Main;
import org.repliedk.session.SessionFactory;

public class SessionListener implements Listener {

    @EventHandler
    public void handlerLogin(PlayerLoginEvent event) {
        String name = event.getPlayer().getName();
        if (SessionFactory.get(name) == null) {
            Main.getMain().getSessionFactory().addSession(name);
        }
    }
}