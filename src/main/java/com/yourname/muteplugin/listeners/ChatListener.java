package com.yourname.muteplugin.listeners;

import com.yourname.muteplugin.MutePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

public class ChatListener implements Listener {
    private final MutePlugin plugin;

    public ChatListener(MutePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        // Проверка глобального мута
        if (plugin.getMuteManager().isGloballyMuted(sender)) {
            event.setCancelled(true);
            sender.sendMessage("§cВы заглушены администрацией!");
            return;
        }

        // Фильтрация сообщений для персональных мутов
        Set<Player> recipients = event.getRecipients();
        recipients.removeIf(recipient ->
                plugin.getMuteManager().isMutedForPlayer(sender, recipient)
        );
    }
}