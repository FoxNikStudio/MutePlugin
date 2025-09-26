package com.yourname.muteplugin;

import com.yourname.muteplugin.listeners.ChatListener;
import com.yourname.muteplugin.managers.MuteManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MutePlugin extends JavaPlugin {
    private MuteManager muteManager;

    @Override
    public void onEnable() {
        muteManager = new MuteManager();
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public MuteManager getMuteManager() {
        return muteManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("mute")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cТолько игроки могут использовать эту команду!");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage("§cИспользование: /mute <ник>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cИгрок не найден или оффлайн!");
                return true;
            }

            muteManager.mutePlayer(target, (Player) sender);
            sender.sendMessage("§aВы замьютили " + target.getName());
        }

        else if (command.getName().equalsIgnoreCase("ad mute")) {
            if (!sender.hasPermission("muteplugin.admute")) {
                sender.sendMessage("§cНет прав!");
                return true;
            }

            if (args.length < 1) {
                sender.sendMessage("§cИспользование: /ad mute <ник>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cИгрок не найден!");
                return true;
            }

            muteManager.globalMute(target);
            Bukkit.broadcastMessage("§c" + target.getName() + " был замьючен администрацией!");
        }
        return true;
    }
}