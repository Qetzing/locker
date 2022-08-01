package qetz.locker;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Outfit {
  private final String name;
  private final Skin skin;
  private final UUID id;

  public String name() {
    return name;
  }

  public Skin skin() {
    return skin;
  }

  public UUID id() {
    return id;
  }

  private static final String skinKey = "textures";

  public WrappedGameProfile toGameProfile() {
    var profile = new WrappedGameProfile(id, name);
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

  public static Outfit fromGameProfile(WrappedGameProfile profile) {
    Preconditions.checkNotNull(profile, "profile");
    return new Outfit(
      profile.getName(),
      Skin.fromProperty(profile.getProperties().get("textures").stream()
        .filter(property -> property.getName().equals(skinKey))
        .findFirst()
        .orElseThrow()
      ),
      profile.getUUID()
    );
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
  }

  public static final class Builder {
    private String name;
    private Skin skin;
    private UUID id;

    public Builder withName(String name) {
      this.name = name;
      return this;
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
      Preconditions.checkNotNull(name, "name");
      Preconditions.checkNotNull(skin, "skin");
      Preconditions.checkNotNull(id, "id");
      return new Outfit(name, skin, id);
    }
  }
}