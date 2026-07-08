package dev.malkist.vezorafeedback.commands;

import dev.malkist.vezorafeedback.VezoraFeedback;
import dev.malkist.vezorafeedback.gui.AdminGUI;
import dev.malkist.vezorafeedback.gui.MainGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedbackCommand implements CommandExecutor {

    private final VezoraFeedback plugin;

    public FeedbackCommand(VezoraFeedback plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        // /feedback
        if (args.length == 0) {

            new MainGUI(plugin).open(player);

            return true;

        }

        // /feedback admin
        if (args[0].equalsIgnoreCase("admin")) {

            if (!player.hasPermission("vezora.feedback.admin")) {
                player.sendMessage("§cYou don't have permission.");
                return true;
            }

            new AdminGUI(plugin).open(player);

            return true;

        }

        // /feedback reload
        if (args[0].equalsIgnoreCase("reload")) {

            if (!player.hasPermission("vezora.feedback.admin")) {
                player.sendMessage("§cYou don't have permission.");
                return true;
            }

            plugin.reloadConfig();
            plugin.getFileManager().reloadFiles();

            player.sendMessage("§aVezoraFeedback reloaded.");

            return true;

        }

        player.sendMessage(" ");
        player.sendMessage("§6Vezora Feedback");
        player.sendMessage("§e/feedback");
        player.sendMessage("§e/feedback admin");
        player.sendMessage("§e/feedback reload");
        player.sendMessage(" ");

        return true;
    }
}