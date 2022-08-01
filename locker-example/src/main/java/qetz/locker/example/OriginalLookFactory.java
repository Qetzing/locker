package qetz.locker.example;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import qetz.locker.Look;
import qetz.locker.LookFactory;
import qetz.locker.Outfit;
import qetz.locker.SingleLook;

import javax.inject.Inject;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @Inject)
public final class OriginalLookFactory implements LookFactory {
  @Override
  public Look create(Outfit original) {
    return SingleLook.withOutfit(original);
  }
}