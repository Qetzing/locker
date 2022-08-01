package qetz.locker;

import java.util.Objects;
import java.util.UUID;

public final class Outfit {
  private final String name;
  private final Skin skin;
  private final UUID id;

  private Outfit(
    String name,
    Skin skin,
    UUID id
  ) {
    this.name = name;
    this.skin = skin;
    this.id = id;
  }

  public String name() {
    return name;
  }

  public Skin skin() {
    return skin;
  }

  public UUID id() {
    return id;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public record Skin(String signature, String value) {
    public static Skin with(String signature, String value) {
      Objects.requireNonNull(signature, "signature");
      Objects.requireNonNull(value, "value");
      return new Skin(signature, value);
    }
  }

  private static final class Builder {
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
      Objects.requireNonNull(name, "name");
      Objects.requireNonNull(skin, "skin");
      Objects.requireNonNull(id, "id");
      return new Outfit(name, skin, id);
    }
  }
}