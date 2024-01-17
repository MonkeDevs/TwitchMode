package uwu.vutuvmonke.twitchmode.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import uwu.vutuvmonke.twitchmode.Serializer;
import uwu.vutuvmonke.twitchmode.TwitchMode;

public class MonkeTwitchModeCommand implements CommandExecutor {

  private final Component noPermission;
  private final Component reloaded;
  private final TwitchMode plugin;

  public MonkeTwitchModeCommand(TwitchMode plugin) {
    this.plugin = plugin;
    var messages = plugin.getConfig().getConfigurationSection("messages");
    this.noPermission = Serializer.deserialize(messages.getString("no-permission", ""));
    this.reloaded = Serializer.deserialize(messages.getString("reloaded", ""));
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!sender.hasPermission("monketwitchmode.reload")) {
      sender.sendMessage(this.noPermission);
    } else {
      this.plugin.reload();
      sender.sendMessage(this.reloaded);
    }
    return true;
  }
}
