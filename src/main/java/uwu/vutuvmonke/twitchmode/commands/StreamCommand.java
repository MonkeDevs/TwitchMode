package uwu.vutuvmonke.twitchmode.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uwu.vutuvmonke.twitchmode.Serializer;
import uwu.vutuvmonke.twitchmode.TwitchMode;

public class StreamCommand implements CommandExecutor {

  private final Component noPermission;
  private final TwitchMode plugin;

  public StreamCommand(TwitchMode plugin) {
    this.plugin = plugin;
    this.noPermission = Serializer.deserialize(plugin.getConfig().getString("messages.no-permission"));
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage("Only for players");
      return true;
    }
    if (!sender.hasPermission("monketwitchmode.stream")) {
      sender.sendMessage(this.noPermission);
      return true;
    }

    this.plugin.toggleStream(player);
    return true;
  }
}
