package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Objects;

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

  public Mode mode() {
    return Mode.fromId(handle().getIntegers().read(1));
  }

  public void setMode(Mode mode) {
    Preconditions.checkNotNull(mode, "mode");
    handle().getIntegers().write(1, mode.id());
  }

  public String displayName() {
    return handle().getStrings().read(1);
  }

  public void setDisplayName(String displayName) {
    handle().getStrings().write(1, Objects.requireNonNullElse(displayName, ""));
  }

  public int friendlyFlags() {
    return handle().getIntegers().read(1);
  }

  public void setFriendlyFlags(int friendlyFlags) {
    handle().getIntegers().write(1, friendlyFlags);
  }

  public NameTagVisibility nameTagVisibility() {
    return NameTagVisibility.fromId(handle().getStrings().read(4));
  }

  public void setNameTagVisibility(NameTagVisibility nameTagVisibility) {
    Preconditions.checkNotNull(nameTagVisibility, "nameTagVisibility");
    handle().getStrings().write(4, nameTagVisibility.id());
  }

  public CollisionRule collisionRule() {
    return CollisionRule.fromId(handle().getStrings().read(5));
  }

  public void setCollisionRule(CollisionRule collisionRule) {
    Preconditions.checkNotNull(collisionRule, "collisionRule");
    handle().getStrings().write(5, collisionRule.id());
  }

  public int packOptionData() {
    return handle().getIntegers().read(2);
  }

  /**
   * Calculated via:
   * <pre>
   * {@code
   * int data = 0;
   * if (team.allowFriendlyFire()) {
   *     data |= 1;
   * }
   * if (team.canSeeFriendlyInvisibles()) {
   *     data |= 2;
   * }
   * }
   * </pre>
   */
  public void setPackOptionData(int packOptionData) {
    handle().getIntegers().write(2, packOptionData);
  }

  public String prefix() {
    return handle().getStrings().read(2);
  }

  public void setPrefix(String prefix) {
    handle().getStrings().write(2, Objects.requireNonNullElse(prefix, ""));
  }

  public String suffix() {
    return handle().getStrings().read(3);
  }

  public void setSuffix(String suffix) {
    handle().getStrings().write(3, Objects.requireNonNullElse(suffix, ""));
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

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Accessors(fluent = true)
  @Getter
  public enum NameTagVisibility {
    Always("always"),
    HideForOtherTeams("hideForOtherTeams"),
    HideForOwnTeam("hideForOwnTeam"),
    Never("never");

    private final String id;

    public static NameTagVisibility fromId(String id) {
      Preconditions.checkNotNull(id, "id");
      for (var nameTagVisibility : values()) {
        if (nameTagVisibility.id.equals(id)) {
          return nameTagVisibility;
        }
      }
      throw new IllegalArgumentException("invalid id");
    }
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  @Accessors(fluent = true)
  @Getter
  public enum CollisionRule {
    Always("always"),
    PushOtherTeams("pushOtherTeams"),
    PushOwnTeam("pushOwnTeam"),
    Never("never");

    private final String id;

    public static CollisionRule fromId(String id) {
      Preconditions.checkNotNull(id, "id");
      for (var collisionRule : values()) {
        if (collisionRule.id.equals(id)) {
          return collisionRule;
        }
      }
      throw new IllegalArgumentException("invalid id");
    }
  }
}