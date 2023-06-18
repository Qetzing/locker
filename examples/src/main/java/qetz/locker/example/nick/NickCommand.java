package qetz.locker.example.nick;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import qetz.locker.Locker;
import qetz.locker.Outfit;
import qetz.locker.component.SingleLook;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class NickCommand implements CommandExecutor {
  private final NickedLookRepository repository;
  private final NickRegistry registry;
  private final Locker locker;

  private static final String permission = "user.nick";

  @Override
  public boolean onCommand(
    CommandSender sender,
    Command command,
    String label,
    String[] options
  ) {
    if (!(sender instanceof Player)) {
      return false;
    }
    var player = (Player) sender;
    if (!player.hasPermission(permission)) {
      return false;
    }
    var id = player.getUniqueId();
    var nicked = registry.isNicked(id);
    changeLook(player, id, nicked);

    if (nicked) {
      registry.remove(id);
      player.sendMessage(ChatColor.RED + "You unnicked yourself.");
    } else {
      registry.add(id);
      player.sendMessage(ChatColor.GREEN + "You nicked yourself.");
    }
    return true;
  }

  private void changeLook(Player player, UUID id, boolean nicked) {
    var originalOutfit = Outfit.originalOutfit(player);
    var newLook = !nicked
      ? repository.create(permission, originalOutfit)
      : SingleLook.withOutfit(originalOutfit);
    locker.updateLook(id, newLook);
  }
}