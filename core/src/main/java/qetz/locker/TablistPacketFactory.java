package qetz.locker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import qetz.locker.outfit.Outfit;
import qetz.locker.packet.WrappedPlayServerScoreboardTeam;
import qetz.locker.packet.WrappedPlayServerScoreboardTeam.CollisionRule;
import qetz.locker.packet.WrappedPlayServerScoreboardTeam.Mode;
import qetz.locker.packet.WrappedPlayServerScoreboardTeam.NameTagVisibility;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class TablistPacketFactory {
  private Collection<Player> receivers;
  private Look look;

  TablistPacketFactory withLook(Look look) {
    Preconditions.checkNotNull(look, "look");
    this.look = look;
    return this;
  }

  TablistPacketFactory withReceiver(Player receiver) {
    Preconditions.checkNotNull(receiver, "receiver");
    return withReceivers(Lists.newArrayList(receiver));
  }

  TablistPacketFactory withAllAvailableReceivers() {
    return withReceivers(Lists.newArrayList(Bukkit.getOnlinePlayers()));
  }

  TablistPacketFactory withReceivers(Collection<Player> receivers) {
    Preconditions.checkNotNull(receivers, "receivers");
    this.receivers = Lists.newArrayList(receivers);
    return this;
  }

  void sendCreating() {
    validateArguments();
    for (var receiver : receivers) {
      var outfit = look.chooseOutfit(receiver);
      var wrapped = WrappedPlayServerScoreboardTeam.create();
      wrapped.setMode(Mode.TeamCreated);
      wrapped.setPlayers(List.of(outfit.displayName().name()));
      applyTeamMetadata(wrapped, receiver, outfit);

      wrapped.sendPacket(receiver);
    }
  }

  private void validateArguments() {
    Preconditions.checkNotNull(receivers, "receivers");
    Preconditions.checkNotNull(look, "look");
  }

  // Using 2 times 7 (=14) since the team name length limit is 14
  // (sort id is max. 2 characters long)
  private String createTeamName(Outfit outfit, Player receiver) {
    return "%d%s%s".formatted(
      outfit.tablistAppearance().sortId(),
      outfit.name().subSequence(0, Math.min(7, outfit.name().length())),
      receiver.getUniqueId().toString().subSequence(
        0,
        Math.max(7, 14 - outfit.name().length())
      )
    );
  }

  // Equivalent to deny friendly fire and cannot see invisible players in own team
  private static final int packOptionData = 0;

  private void applyTeamMetadata(
    WrappedPlayServerScoreboardTeam wrapped,
    Player receiver,
    Outfit outfit
  ) {
    wrapped.setName(createTeamName(outfit, receiver));
    wrapped.setPrefix(outfit.displayName().prefix());
    wrapped.setSuffix(outfit.displayName().suffix());
    wrapped.setNameTagVisibility(NameTagVisibility.Always);
    wrapped.setCollisionRule(CollisionRule.Never);
    wrapped.setPackOptionData(packOptionData);
  }

  void sendUpdating() {
    validateArguments();
    for (var receiver : receivers) {
      var outfit = look.chooseOutfit(receiver);
      var wrapped = WrappedPlayServerScoreboardTeam.create();
      wrapped.setMode(Mode.TeamUpdated);
      applyTeamMetadata(wrapped, receiver, outfit);

      wrapped.sendPacket(receiver);
    }
  }

  void sendDestroying() {
    validateArguments();
    for (var receiver : receivers) {
      var outfit = look.chooseOutfit(receiver);
      var wrapped = WrappedPlayServerScoreboardTeam.create();
      wrapped.setMode(Mode.TeamRemoved);
      applyTeamMetadata(wrapped, receiver, outfit);

      wrapped.sendPacket(receiver);
    }
  }
}