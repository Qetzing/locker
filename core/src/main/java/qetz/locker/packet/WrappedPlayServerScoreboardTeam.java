package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;

import java.util.Collection;

public final class WrappedPlayServerScoreboardTeam extends AbstractPacket {
  public static final PacketType type = PacketType.Play.Server.SCOREBOARD_TEAM;

  public static WrappedPlayServerScoreboardTeam withPacket(
    PacketContainer packet
  ) {
    Preconditions.checkNotNull(packet, "packet");
    Preconditions.checkArgument(
      packet.getType().equals(type),
      "packet type does not match with needed packet type"
    );
    return new WrappedPlayServerScoreboardTeam(packet, type);
  }

  public static WrappedPlayServerScoreboardTeam create() {
    var packet = new WrappedPlayServerScoreboardTeam(
      new PacketContainer(type),
      type
    );
    packet.handle().getModifier().writeDefaults();
    return packet;
  }

  private WrappedPlayServerScoreboardTeam(
    PacketContainer container,
    PacketType type
  ) {
    super(container, type);
  }

  public String name() {
    return handle().getStrings().read(0);
  }

  public void setName(String name) {
    Preconditions.checkNotNull(name, "name");
    handle().getStrings().write(0, name);
  }

  public int mode() {
    return handle().getIntegers().read(0);
  }

  public void setMode(int value) {
    handle().getIntegers().write(0, value);
  }

  public WrappedChatComponent displayName() {
    return handle().getChatComponents().read(0);
  }

  public void setDisplayName(WrappedChatComponent displayName) {
    Preconditions.checkNotNull(displayName, "displayName");
    handle().getChatComponents().write(0, displayName);
  }

  public int friendlyFlags() {
    return handle().getIntegers().read(1);
  }

  public void setFriendlyFlags(int friendlyFlags) {
    handle().getIntegers().write(1, friendlyFlags);
  }

  public String nameTagVisibility() {
    return handle().getStrings().read(1);
  }

  public void setNameTagVisibility(String nameTagVisibility) {
    Preconditions.checkNotNull(nameTagVisibility, "nameTagVisibility");
    handle().getStrings().write(1, nameTagVisibility);
  }

  public String collisionRule() {
    return handle().getStrings().read(2);
  }

  public void setCollisionRule(String collisionRule) {
    Preconditions.checkNotNull(collisionRule, "collisionRule");
    handle().getStrings().write(2, collisionRule);
  }

  public ChatColor color() {
    return handle().getEnumModifier(
      ChatColor.class,
      MinecraftReflection.getMinecraftClass("EnumChatFormat")
    ).read(0);
  }

  public void setColor(ChatColor color) {
    Preconditions.checkNotNull(color, "color");
    handle().getEnumModifier(
      ChatColor.class,
      MinecraftReflection.getMinecraftClass("EnumChatFormat")
    ).write(0, color);
  }

  public WrappedChatComponent prefix() {
    return handle().getChatComponents().read(1);
  }

  public void setPrefix(WrappedChatComponent prefix) {
    Preconditions.checkNotNull(prefix, "prefix");
    handle().getChatComponents().write(1, prefix);
  }

  public WrappedChatComponent suffix() {
    return handle().getChatComponents().read(2);
  }

  public void setSuffix(WrappedChatComponent suffix) {
    Preconditions.checkNotNull(suffix, "suffix");
    handle().getChatComponents().write(2, suffix);
  }

  @SuppressWarnings("unchecked")
  public Collection<String> players() {
    return (Collection<String>) handle()
      .getSpecificModifier(Collection.class)
      .read(0);
  }

  public void setPlayers(Collection<String> players) {
    Preconditions.checkNotNull(players, "players");
    handle().getSpecificModifier(Collection.class).write(0, players);
  }

  public enum Mode {
    TeamCreated,
    TeamRemoved,
    TeamUpdated,
    PlayersAdded,
    PlayersRemoved;

    public static Mode fromId(int id) {
      Preconditions.checkArgument(id >= 0 && id <= 5, "id must be between 0 and 5");
      return Mode.values()[id];
    }

    public int id() {
      return ordinal();
    }
  }
}