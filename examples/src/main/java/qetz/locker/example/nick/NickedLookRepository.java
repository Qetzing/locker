package qetz.locker.example.nick;

import com.google.inject.ImplementedBy;
import qetz.locker.outfit.Outfit;
import qetz.locker.component.NickedLook;

@ImplementedBy(StaticNickedLookRepository.class)
public interface NickedLookRepository {
  NickedLook create(String bypassPermission, Outfit original);
}