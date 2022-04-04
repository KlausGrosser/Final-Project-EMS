package finalproject.controllers;


import finalproject.models.TimeKeeping;
import finalproject.util.GenericHelper;

public class TimeKeepingFactory {

  public static TimeKeeping buildTimekeepingService(int time_In, int time_Out, String empID) {
    String recID = GenericHelper.generateID();
    return new TimeKeeping.Builder()
            .setRecID(recID)
            .setTime_In(time_In)
            .setTime_Out(time_Out)
            .setEmployee_id(empID)
            .build();
  }
}
