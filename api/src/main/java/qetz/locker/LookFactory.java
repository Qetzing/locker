package qetz.locker;

public interface LookFactory {
  /**
   * Creates a Look for a player by the given {@link Outfit}.
   *
   * @param original The original {@link Outfit} of a player
   * @return The {@link Look} for the player given by the original outfit
   */
  Look create(Outfit original);
}