package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;

import java.util.UUID;

import static com.comphenix.protocol.PacketType.Play.Server.NAMED_ENTITY_SPAWN;

public final class WrappedPlayServerSpawnNamedEntity extends AbstractPacket {
  private static final PacketType type = NAMED_ENTITY_SPAWN;

  public static WrappedPlayServerSpawnNamedEntity withPacket(
    PacketContainer packet
  ) {
    Preconditions.checkNotNull(packet, "packet");
    Preconditions.checkArgument(
      packet.getType().equals(type),
      "packet type does not match with needed packet type"
    );
    return new WrappedPlayServerSpawnNamedEntity(packet, type);
  }

  public static WrappedPlayServerSpawnNamedEntity create() {
    var packet = new WrappedPlayServerSpawnNamedEntity(
      new PacketContainer(type),
      type
    );
    packet.handle().getModifier().writeDefaults();
    return packet;
  }

  private WrappedPlayServerSpawnNamedEntity(
    PacketContainer container,
    PacketType type
  ) {
    super(container, type);
  }

  public void setEntityId(int id) {
    handle().getIntegers().write(0, id);
  }

  public int entityId() {
    return handle().getIntegers().read(0);
  }

  public void setId(UUID id) {
    handle().getUUIDs().write(0, id);
  }

  public UUID id() {
    return handle().getUUIDs().read(0);
  }

  public void setX(double x) {
    handle().getDoubles().write(0, x);
  }

  public double x() {
    return handle().getDoubles().read(0);
  }

  public void setY(double y) {
    handle().getDoubles().write(1, y);
  }

  public double y() {
    return handle().getDoubles().read(1);
  }

  public void setZ(double z) {
    handle().getDoubles().write(2, z);
  }

  public double z() {
    return handle().getDoubles().read(2);
  }

  public void setYaw(float yaw) {
    handle().getBytes().write(0, (byte) (yaw * 256.0F / 360.0F));
  }

  public float yaw() {
    return (handle().getBytes().read(0) * 360.F) / 256.0F;
  }

  public void setPitch(float pitch) {
    handle().getBytes().write(1, (byte) (pitch * 256.0F / 360.0F));
  }

  public float pitch() {
    return (handle().getBytes().read(1) * 360.F) / 256.0F;
  }
}