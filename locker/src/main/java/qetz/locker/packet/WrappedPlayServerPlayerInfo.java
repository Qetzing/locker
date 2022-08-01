package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.google.common.base.Preconditions;

import java.util.List;

import static com.comphenix.protocol.PacketType.Play.Server.PLAYER_INFO;

public final class WrappedPlayServerPlayerInfo extends AbstractPacket {
  private static final PacketType type = PLAYER_INFO;

  public static WrappedPlayServerPlayerInfo withPacket(
    PacketContainer packet
  ) {
    Preconditions.checkNotNull(packet, "packet");
    Preconditions.checkArgument(
      packet.getType().equals(type),
      "packet type does not match with needed packet type"
    );
    return new WrappedPlayServerPlayerInfo(packet, type);
  }

  public static WrappedPlayServerPlayerInfo create() {
    var packet = new WrappedPlayServerPlayerInfo(
      new PacketContainer(type),
      type
    );
    packet.handle().getModifier().writeDefaults();
    return packet;
  }

  private WrappedPlayServerPlayerInfo(
    PacketContainer container,
    PacketType type
  ) {
    super(container, type);
  }

  public PlayerInfoAction action() {
    return handle().getPlayerInfoAction().read(0);
  }

  public void setAction(PlayerInfoAction action) {
    Preconditions.checkNotNull(action, "action");
    handle().getPlayerInfoAction().write(0, action);
  }

  public List<PlayerInfoData> data() {
    return handle().getPlayerInfoDataLists().read(0);
  }

  public void setData(List<PlayerInfoData> data) {
    Preconditions.checkNotNull(data, "data");
    handle().getPlayerInfoDataLists().write(0, data);
  }
}