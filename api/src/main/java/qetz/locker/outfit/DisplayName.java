package qetz.locker.outfit;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.Objects;

import static java.util.Objects.requireNonNullElse;

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