package qetz.locker;

import java.util.Objects;
import java.util.UUID;

public final class Outfit {
  private final String prefix;
  private final String suffix;
  private final String name;
  private final Skin skin;
  private final UUID id;

  private Outfit(
    String prefix,
    String suffix,
    String name,
    Skin skin,
    UUID id
  ) {
    this.prefix = prefix;
    this.suffix = suffix;
    this.name = name;
    this.skin = skin;
    this.id = id;
  }

  public String prefix() {
    return prefix;
  }

  public String suffix() {
    return suffix;
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

  private record Skin(String signature, String texture) {
    public static Skin with(String signature, String texture) {
      Objects.requireNonNull(signature, "signature");
      Objects.requireNonNull(texture, "texture");
      return new Skin(signature, texture);
    }
  }

  private static final class Builder {
    private String prefix;
    private String suffix;
    private String name;
    private Skin skin;
    private UUID id;

    public Builder withoutPrefix() {
      return withPrefix("");
    }

    public Builder withPrefix(String prefix) {
      this.prefix = prefix;
      return this;
    }

    public Builder withoutSuffix() {
      return withSuffix("");
    }

    public Builder withSuffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

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
      Objects.requireNonNull(prefix, "prefix");
      Objects.requireNonNull(suffix, "suffix");
      Objects.requireNonNull(name, "name");
      Objects.requireNonNull(skin, "skin");
      Objects.requireNonNull(id, "id");
      return new Outfit(prefix, suffix, name, skin, id);
    }
  }
}