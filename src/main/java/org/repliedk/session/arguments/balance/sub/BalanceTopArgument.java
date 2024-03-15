package org.repliedk.session.arguments.balance.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import org.repliedk.Main;
import org.repliedk.arguments.SubArgument;
import org.repliedk.session.PlayerSession;
import org.repliedk.session.SessionFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class BalanceTopArgument extends SubArgument {

    public BalanceTopArgument() {
        super("top");
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        message(sender, "&l&9The Top of BALANCE&r:");
        message(sender, "&7---------------------------");

        for (int i = 1; i <= 5; i++) {
            PlayerSession player = getPosition(i);
            if (player != null) {

                String color;

                color = "&f";

                if (sender.isPlayer() && Objects.equals(SessionFactory.get(sender.getName()), player)) {
                    color = "&b";
                }

                message(sender, "&o&f#" + i + " &r" + color + player.getName() + ": &e$" + player.getBalance());
            } else {
                message(sender, "&o&f#" + i + " &r&cNone");
            }
        }

        if (sender.isPlayer() && getPosByPlayer(SessionFactory.get(sender.getName())) > 5) {
            message(sender, "&eYour position &b&o#" + getPosByPlayer(SessionFactory.get(sender.getName())));
        }

        message(sender, "&7---------------------------");
        return true;
    }

    public int getPosByPlayer(PlayerSession player) {
        List<PlayerSession> sessionsWithBalance = Main.getMain().getSessionFactory().getSessions().values().stream()
                .filter(session -> session.getBalance() > 0)
                .sorted(Comparator.comparingInt(PlayerSession::getBalance).reversed())
                .toList();

        return sessionsWithBalance.indexOf(player) + 1;
    }

    public PlayerSession getPosition(int pos) {
        List<PlayerSession> sessionsWithBalance = Main.getMain().getSessionFactory().getSessions().values().stream()
                .filter(session -> session.getBalance() > 0)
                .sorted(Comparator.comparingInt(PlayerSession::getBalance).reversed())
                .toList();

        return (pos >= 1 && pos <= sessionsWithBalance.size()) ? sessionsWithBalance.get(pos - 1) : null;
    }

    @Override
    public CommandParameter[] getParameters() {
        return new CommandParameter[0];
    }
}