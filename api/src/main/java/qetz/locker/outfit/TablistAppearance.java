package qetz.locker.outfit;

import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 *
 * @param sortId Parameter that determines the order in the tablist.
 *               The lower the id, the higher the appearance (asc sorted).
 *               Value must be higher -1 and lower than 100.
 */
public record TablistAppearance(int sortId) {
  public static TablistAppearance fallback() {
    return withSortId(1);
  }

  public static TablistAppearance withSortId(int sortId) {
    Preconditions.checkArgument(
      sortId > -1 && sortId < 100,
      "sort id must be between -1 and 100"
    );
    return new TablistAppearance(sortId);
  }

  @Override
  public String toString() {
    return "TablistAppearance{sortId=%d}".formatted(sortId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sortId);
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof TablistAppearance)) {
      return false;
    }
    var tablistAppearance = (TablistAppearance) object;
    return sortId == tablistAppearance.sortId;
  }
}