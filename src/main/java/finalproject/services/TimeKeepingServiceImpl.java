package finalproject.services;


import finalproject.models.TimeKeeping;
import finalproject.repositories.TimeKeepingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimeKeepingServiceImpl implements TimeKeepingServices {
  @Autowired
  private TimeKeepingRepository timeKeepingRepository;

  @Override
  public Set<TimeKeeping> getAll() {
    return this.timeKeepingRepository.findAll().stream().collect(Collectors.toSet());
  }

  @Override
  public TimeKeeping create(TimeKeeping t) {
    return this.timeKeepingRepository.save(t);
  }

  @Override
  public TimeKeeping read(String s) {
    return this.timeKeepingRepository.findById(s).orElse(null);
  }

  @Override
  public TimeKeeping update(TimeKeeping t) {
    return this.timeKeepingRepository.save(t);
  }

  @Override
  public void delete(String s) {
    this.timeKeepingRepository.deleteById(s);
  }
}
