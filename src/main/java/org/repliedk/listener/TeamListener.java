package org.repliedk.listener;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.HugeExplodeParticle;
import cn.nukkit.math.Vector3;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;
import org.repliedk.session.util.Session;
import org.repliedk.utils.MainUtil;

public class TeamListener implements Listener {

    public TeamListener() {}

    @EventHandler
    public void handlerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().isPlayer()) return;

        PlayerSession entity = SessionFactory.get(event.getPlayer().getName());

        entity.addTimer(Session.STARTING, 20);

        if (entity.getTimer(Session.STARTING) != null) {
            entity.sendMessage("Check XD");
        }
    }

    @EventHandler
    public void handlerDeath(PlayerDeathEvent event) {

        if (!event.getEntity().isPlayer()) return;

        PlayerSession entity = SessionFactory.get(event.getEntity().getName());

        assert entity != null;

        if (entity.hasTeam()) {
            entity.getTeam().removeDtr();
            entity.getTeam().sendAnnounce("MEMBER DEATH: " + entity.getName());
            entity.getTeam().sendAnnounce("CURRENT DTR: " + entity.getTeam().getDtr() + "/" + entity.getTeam().getMaxDtr());
            entity.getTeam().setFreezeTeam();
        }

        entity.setDeaths(entity.getDeaths() + 1);

        Vector3 pos = event.getEntity().getPosition();
        Level level = event.getEntity().getLevel();
        level.addParticle(new HugeExplodeParticle(pos));
        level.addSound(pos, Sound.AMBIENT_WEATHER_LIGHTNING_IMPACT);

        if (event.getEntity().getKiller() != null) {
            PlayerSession damager = SessionFactory.get(event.getEntity().getKiller().getName());
            assert damager != null;
            damager.setKills(damager.getKills() + 1);

            String item;

            if (!damager.getPlayer().getInventory().getItemInHand().isNull()) {
                item = damager.getPlayer().getInventory().getItemInHand().getName();
            } else {
                item = "hand";
            }

            event.setDeathMessage("�c" + entity.getName() + "[�e" + entity.getKills() + "] �ehas been slain by �4" + damager.getName() + "[�e" + damager.getName() + "] using " + item);
        } else {
            event.setDeathMessage("�c" + entity.getName() + "[�e" + entity.getName() + "] �e has died!");
        }
    }

    @EventHandler
    public void handlerChat(PlayerChatEvent event) {
        PlayerSession player = SessionFactory.get(event.getPlayer().getName());

        assert player != null;

        if (player.hasTeam()) {
            if (player.getChat().equalsIgnoreCase(MainUtil.TeamChat)) {
                player.getTeam().sendAnnounce("(Team) " + player.getName() + ": " + event.getMessage());
                event.setCancelled();
                return;
            } else {
                event.setCancelled();
                sendChatFormatter(player, event.getMessage());
            }
        } else {
            event.setFormat(("* " + player.getName() + ": " + event.getMessage()));
        }
    }

    public void sendChatFormatter(PlayerSession player, String message) {
        for (Player target : Server.getInstance().getOnlinePlayers().values()) {
            PlayerSession session = SessionFactory.get(target.getName());
            assert session != null;
            session.sendMessage("[" + player.getTeam().getName() + "] " + player.getName() + ": " + message);
        }
    }

}