package qetz.locker.outfit;

import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.base.Preconditions;

import java.util.Objects;

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