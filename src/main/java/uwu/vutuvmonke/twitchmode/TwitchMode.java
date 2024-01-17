package uwu.vutuvmonke.twitchmode;

import java.util.HashSet;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import uwu.vutuvmonke.twitchmode.commands.MonkeTwitchModeCommand;
import uwu.vutuvmonke.twitchmode.commands.StreamCommand;

public final class TwitchMode extends JavaPlugin {

  private static final HashSet<Audience> STREAMERS = new HashSet<>();
  private Component twitchEnabled;
  private Component twitchDisabled;

  @Override
  public void onEnable() {
    this.reload();
  }

  public void reload() {
    this.saveDefaultConfig();
    this.reloadConfig();
    var messages = this.getConfig().getConfigurationSection("messages");
    if (messages == null) {
      throw new RuntimeException();
    }
    this.twitchEnabled = Serializer.deserialize(messages.getString("enabled", ""));
    this.twitchDisabled = Serializer.deserialize(messages.getString("disabled", ""));

    HandlerList.unregisterAll(this);
    this.getServer().getPluginManager().registerEvents(new ChatListener(this), this);

    this.getCommand("stream").setExecutor(new StreamCommand(this));
    this.getCommand("monketwitchmode").setExecutor(new MonkeTwitchModeCommand(this));
  }

  public void toggleStream(Player player) {
    if (STREAMERS.add(player)) {
      player.sendMessage(this.twitchEnabled);
    } else {
      STREAMERS.remove(player);
      player.sendMessage(this.twitchDisabled);
    }
  }

  public HashSet<Audience> getStreamers() {
    return STREAMERS;
  }
}
