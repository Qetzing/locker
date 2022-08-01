package qetz.locker.component;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import qetz.locker.Look;
import qetz.locker.Outfit;

import java.util.LinkedHashMap;

import static qetz.locker.component.PermissionFilteredLook.UnknownPolicy.Explicit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionFilteredLook extends Look {
  private final LinkedHashMap<String, Outfit> looks;
  private final UnknownPolicy policy;
  private final Outfit explicit;

  @Override
  public Outfit chooseOutfit(Player receiver) {
    for (var entry : looks.entrySet()) {
      if (receiver.hasPermission(entry.getKey())) {
        return entry.getValue();
      }
    }
    return fallbackOutfit(receiver);
  }

  private Outfit fallbackOutfit(Player receiver) {
    return switch (policy) {
      case Throw -> throw new IllegalStateException(String.format(
        "could not find a suitable outfit for receiver %s",
        receiver.getUniqueId()
      ));
      case Last -> looks.values().toArray(Outfit[]::new)[looks.size() - 1];
      case Explicit -> explicit;
    };
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public enum UnknownPolicy {
    Throw,
    Last,
    Explicit
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder {
    private LinkedHashMap<String, Outfit> looks = Maps.newLinkedHashMap();
    private UnknownPolicy policy;
    private Outfit explicit;

    public Builder withLooks(LinkedHashMap<String, Outfit> looks) {
      Preconditions.checkNotNull(looks, "looks");
      this.looks = looks;
      return this;
    }

    public Builder addLook(String permission, Outfit outfit) {
      Preconditions.checkNotNull(permission, "permission");
      Preconditions.checkNotNull(outfit, "outfit");
      looks.put(permission, outfit);
      return this;
    }

    public Builder withUnknownPolicy(UnknownPolicy policy) {
      this.policy = policy;
      return this;
    }

    public Builder withExplicit(Outfit explicit) {
      this.explicit = explicit;
      return this;
    }

    public Look create() {
      Preconditions.checkNotNull(policy, "policy");
      Preconditions.checkArgument(
        policy == Explicit && explicit != null,
        "if explicit policy chosen, explicit value must be provided"
      );
      return new PermissionFilteredLook(looks, policy, explicit);
    }
  }
}