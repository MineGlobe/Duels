package me.realized.duels.duel;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.arena.Arena;
import me.realized.duels.arena.ArenaManager;
import me.realized.duels.cache.Setting;
import me.realized.duels.request.Request;
import me.realized.duels.util.Loadable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DuelManager implements Loadable, Listener {

    private final DuelsPlugin plugin;
    private final ArenaManager arenaManager;

    public DuelManager(final DuelsPlugin plugin) {
        this.plugin = plugin;
        this.arenaManager = plugin.getArenaManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void handleLoad() throws Exception {

    }

    @Override
    public void handleUnload() throws Exception {

    }

    public void startMatch(final Player first, final Player second, final Request request) {
        final Setting setting = request.getSetting();
        final Arena arena = setting.getArena() != null ? setting.getArena() : arenaManager.randomArena();

        if (arena == null) {
            tempMsg("The arena is currently unavailable, please try again later.", first, second);
            return;
        }

        first.teleport(arena.getPositions().get(1));
        arena.addPlayer(first);
        second.teleport(arena.getPositions().get(2));
        arena.addPlayer(second);

        if (setting.getKit() != null) {
            setting.getKit().equip(first, second);
        }

        tempMsg("Duel started!", first, second);
    }

    private void tempMsg(final String msg, final Player... players) {
        for (final Player player : players) {
            player.sendMessage(msg);
        }
    }

    @EventHandler
    public void on(final PlayerQuitEvent event) {

    }
}