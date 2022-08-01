package qetz.locker.example.nick;

import com.google.inject.Inject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.locker.Outfit;
import qetz.locker.component.NickedLook;

import java.util.UUID;

import static qetz.locker.Outfit.Skin.with;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class StaticNickedLookRepository implements NickedLookRepository {
  private static final Outfit nickedOutfit = Outfit.newBuilder()
    .withId(UUID.fromString("34e57efa-5783-46c7-a9fc-890296aaba1f"))
    .withName("LabyStudio")
    .withSkin(with(
      "ewogICJ0aW1lc3RhbXAiIDogMTY1OTM3Nzc2OTM4MywKICAicHJvZmlsZUlkIiA6ICIzNG" +
        "U1N2VmYTU3ODM0NmM3YTlmYzg5MDI5NmFhYmExZiIsCiAgInByb2ZpbGVOYW1lIiA6IC" +
        "JMYWJ5U3R1ZGlvIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgIC" +
        "AidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VmNj" +
        "k5Yzk3MWRkMTI0NDYwZTlhYzBlOTE5MTNmNWViY2U3ZTAyZjVjNmMzMjQzNWM3N2NkNz" +
        "YzZjQxOWQ1MzQiLAogICAgICAibWV0YWRhdGEiIDogewogICAgICAgICJtb2RlbCIgOi" +
        "Aic2xpbSIKICAgICAgfQogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOi" +
        "AiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lN2RmZWExNmRjOD" +
        "NjOTdkZjAxYTEyZmFiYmQxMjE2MzU5YzBjZDBlYTQyZjk5OTliNmU5N2M1ODQ5NjNlOT" +
        "gwIgogICAgfQogIH0KfQ==",
      ""
    ))
    .create();

  @Override
  public NickedLook create(String bypassPermission, Outfit original) {
    return NickedLook.with(
      bypassPermission,
      original,
      nickedOutfit
    );
  }
}