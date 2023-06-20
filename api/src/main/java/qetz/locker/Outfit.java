package qetz.locker;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.base.Preconditions;
import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNullElse;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Accessors(fluent = true)
@Getter
public final class Outfit {
  private final DisplayName displayName;
  private final Skin skin;
  private final UUID id;

  public String name() {
    return displayName.name();
  }

  private static final String skinKey = "textures";

  public WrappedGameProfile toGameProfile() {
    var profile = new WrappedGameProfile(id, displayName.name());
    var properties = profile.getProperties();
    properties.put(
      skinKey,
      WrappedSignedProperty.fromValues(
        skinKey,
        skin.value(),
        skin.signature()
      )
    );
    return profile;
  }

  public static Outfit originalOutfit(Player player) {
    Preconditions.checkNotNull(player, "player");
    return newBuilder().originalFromPlayer(player).create();
  }

  public static Outfit fromGameProfile(WrappedGameProfile profile) {
    Preconditions.checkNotNull(profile, "profile");
    return newBuilder().fromGameProfile(profile).create();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public record Skin(String signature, String value) {
    public static Skin with(String signature, String value) {
      Preconditions.checkNotNull(signature, "signature");
      Preconditions.checkNotNull(value, "value");
      return new Skin(signature, value);
    }

    public static Skin fromProperty(WrappedSignedProperty property) {
      Preconditions.checkNotNull(property, "property");
      return new Skin(property.getSignature(), property.getValue());
    }

    @Override
    public String toString() {
      return "Skin{signature='%s', value='%s'}".formatted(signature, value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(signature, value);
    }

    @Override
    public boolean equals(Object object) {
      if (object == this) {
        return true;
      }
      if (!(object instanceof Skin)) {
        return false;
      }
      var skin = (Skin) object;
      return signature.equals(skin.signature)
        && value.equals(skin.value);
    }
  }

  @lombok.Builder(builderMethodName = "newBuilder", setterPrefix = "with")
  public record DisplayName(
    @NonNull String name,
    @Nullable String prefix,
    @Nullable String suffix,
    @Nullable ChatColor color
  ) {
    public static DisplayName emptyWithName(String name) {
      Preconditions.checkNotNull(name, "name");
      return new DisplayName(name, null, null, null);
    }

    public String formatted() {
      return "%s%s%s".formatted(
        requireNonNullElse(prefix, ""),
        name,
        requireNonNullElse(suffix, "")
      );
    }

    @Override
    public String toString() {
      return "DisplayName{name='%s', prefix='%s', suffix='%s', color='%s'}".formatted(
        name,
        prefix,
        suffix,
        color
      );
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, prefix, suffix, color);
    }

    @Override
    public boolean equals(Object object) {
      if (object == this) {
        return true;
      }
      if (!(object instanceof DisplayName)) {
        return false;
      }
      var displayName = (DisplayName) object;
      return name.equals(displayName.name)
        && Objects.equals(prefix, displayName.prefix)
        && Objects.equals(suffix, displayName.suffix)
        && Objects.equals(color, displayName.color);
    }
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static final class Builder {
    private DisplayName displayName;
    private Skin skin;
    private UUID id;

    public Builder originalFromPlayer(Player player) {
      Preconditions.checkNotNull(player, "player");
      var property = player.getPlayerProfile().getProperties().stream()
        .filter(current -> current.getName().equals(skinKey))
        .findFirst()
        .orElseThrow();
      return withSkin(Skin.with(property.getSignature(), property.getValue()))
        .withId(player.getUniqueId())
        .withName(player.getName());
    }

    public Builder fromGameProfile(WrappedGameProfile profile) {
      Preconditions.checkNotNull(profile, "profile");
      return withSkin(Skin.fromProperty(profile.getProperties().get("textures")
        .stream()
        .filter(property -> property.getName().equals(skinKey))
        .findFirst()
        .orElseThrow()
      ))
        .withName(profile.getName())
        .withId(profile.getUUID());
    }

    public Builder withDisplayName(DisplayName displayName) {
      this.displayName = displayName;
      return this;
    }

    public Builder withName(String name) {
      Preconditions.checkNotNull(name, "name");
      return withDisplayName(DisplayName.emptyWithName(name));
    }

    public Builder withSkin(Skin skin) {
      this.skin = skin;
      return this;
    }

    public Builder withId(UUID id) {
      this.id = id;
      return this;
    }

    public Outfit create() {
      Preconditions.checkNotNull(displayName, "displayName");
      Preconditions.checkNotNull(skin, "skin");
      Preconditions.checkNotNull(id, "id");
      return new Outfit(displayName, skin, id);
    }
  }
}