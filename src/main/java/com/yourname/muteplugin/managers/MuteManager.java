package com.yourname.muteplugin.managers;

import org.bukkit.entity.Player;
import java.util.*;

public class MuteManager {
    private final Set<UUID> globalMutes = new HashSet<>();
    private final Map<UUID, Set<UUID>> personalMutes = new HashMap<>();

    // Персональные муты
    public void mutePlayer(Player target, Player executor) {
        personalMutes.computeIfAbsent(executor.getUniqueId(), k -> new HashSet<>())
                .add(target.getUniqueId());
    }

    public void unmutePlayer(Player target, Player executor) {
        Set<UUID> mutedList = personalMutes.get(executor.getUniqueId());
        if (mutedList != null) {
            mutedList.remove(target.getUniqueId());
        }
    }

    public boolean isMutedForPlayer(Player target, Player executor) {
        Set<UUID> mutedList = personalMutes.get(executor.getUniqueId());
        return mutedList != null && mutedList.contains(target.getUniqueId());
    }

    // Глобальные муты
    public void globalMute(Player target) {
        globalMutes.add(target.getUniqueId());
    }

    public void globalUnmute(Player target) {
        globalMutes.remove(target.getUniqueId());
    }

    public boolean isGloballyMuted(Player target) {
        return globalMutes.contains(target.getUniqueId());
    }
}