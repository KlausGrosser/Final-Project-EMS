package finalproject.services;

import finalproject.models.TimeKeeping;

import java.util.Set;

public interface TimeKeepingServices  extends IService<TimeKeeping,String> {
  Set<TimeKeeping> getAll();
}
