package qetz.locker.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

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
    try {
      getProtocolManager().sendServerPacket(receiver, handle());
    } catch (InvocationTargetException sendFailure) {
      throw new RuntimeException(sendFailure);
    }
  }

  public void broadcastPacket() {
    getProtocolManager().broadcastServerPacket(handle());
  }

  public void receivePacket(Player sender) {
    try {
      getProtocolManager().recieveClientPacket(sender, handle());
    } catch (IllegalAccessException | InvocationTargetException receivingFailure) {
      throw new RuntimeException(receivingFailure);
    }
  }
}