package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import static com.comphenix.protocol.ProtocolLibrary.getProtocolManager;

public abstract class AbstractPacket {
  private final PacketContainer handle;

  AbstractPacket(PacketContainer handle, PacketType type) {
    this.handle = handle;
  }

  public PacketContainer handle() {
    return handle;
  }

  public void sendPacket(Player receiver) {
    getProtocolManager().sendServerPacket(receiver, handle());
  }

  public void broadcastPacket() {
    getProtocolManager().broadcastServerPacket(handle());
  }

  public void receivePacket(Player sender) {
    getProtocolManager().receiveClientPacket(sender, handle());
  }
}