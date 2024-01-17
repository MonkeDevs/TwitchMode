package uwu.vutuvmonke.twitchmode;

import org.bukkit.event.EventPriority;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

  private final String banwords;
  private final String replace;
  private final TwitchMode plugin;

  public ChatListener(TwitchMode plugin) {
    this.plugin = plugin;
    this.banwords = plugin.getConfig().getString("banwords");
    this.replace = plugin.getConfig().getString("replace");
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onMessage(final AsyncChatEvent event) {
    var componentMessage = event.originalMessage();

    String message = Serializer.serialize(componentMessage);
    String result = message.replaceAll(banwords, replace);
    if (!result.equals(message)) {
      event.viewers().removeAll(this.plugin.getStreamers());
      if (!replace.isEmpty()) {
        var componentResult = Serializer.deserialize(result);
        this.plugin.getStreamers().forEach(audience -> audience.sendMessage(componentResult));
      }
    }
  }
}
