package finalproject.controllers;

import finalproject.models.TimeKeeping;
import finalproject.services.TimeKeepingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/Timekeeping")
public class TimeKeepingController {

  @Autowired
  //@Qualifier("TimekeepingServiceImp")
  private TimeKeepingServices timekeepingservices;
  @PostMapping("/create")
  public TimeKeeping create(@RequestBody TimeKeeping Timekeeping )
  {
    TimeKeeping timekeeping = TimeKeepingFactory.buildTimekeepingService(Timekeeping.getTime_In(),Timekeeping.getTime_Out(),Timekeeping.getempID());
    return timekeepingservices.create(timekeeping);
  }

  @GetMapping("/read/{id}")
  public TimeKeeping read(@PathVariable String id)
  {
    return timekeepingservices.read(id);
  }

  @PutMapping("/update/{id}")
  public TimeKeeping update(@RequestBody TimeKeeping timekeeping) {

    return timekeepingservices.update(timekeeping);
  }

  @DeleteMapping("delete/{id}")
  void delete(@PathVariable String id) {
    timekeepingservices.delete(id);}

  @GetMapping("/all")
  public Set<TimeKeeping> getAll() {
    return timekeepingservices.getAll();
  }
}


