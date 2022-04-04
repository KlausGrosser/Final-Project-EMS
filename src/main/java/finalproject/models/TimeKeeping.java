package finalproject.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class TimeKeeping implements Serializable {

  @Id
  private String recID;
  private int time_In;
  private int time_Out;
  private String empID;

  protected TimeKeeping(){}

  private TimeKeeping(Builder builder){
    this.recID = builder.recID;
    this.time_In = builder.time_In;
    this.time_Out = builder.time_Out;
    this.empID = builder.empID;
  }
  public String getRecID() {
    return recID;
  }

  public int getTime_In() {
    return time_In;
  }

  public int getTime_Out() {
    return time_Out;
  }

  public String getempID() {
    return empID;
  }

  @Override
  public String toString() {
    return "TimekeepingService{" +
            "RecId=" + recID +
            "time_In=" + time_In +
            ", time_Out=" + time_Out +
            ", empID=" + empID +
            '}';
  }
  public static class Builder{
    private String recID;
    private int time_In;
    private int time_Out;
    private String empID;

        /*public Builder(int time_In, int time_Out, String empID) {
            this.time_In = time_In;
            this.time_Out = time_Out;
            this.empID = empID;
        }*/

    public Builder setTime_In(int time_In) {
      this.time_In = time_In;
      return this;
    }

    public Builder setTime_Out(int time_Out) {
      this.time_Out = time_Out;
      return this;
    }

    public Builder setEmployee_id(String empID) {
      this.empID = empID;
      return this;
    }

    public Builder setRecID(String recID) {
      this.recID = recID;
      return this;
    }

    public Builder copy(TimeKeeping timeKeepingService){
      this.recID = timeKeepingService.recID;
      this.time_In= timeKeepingService.time_In;
      this.time_Out= timeKeepingService.time_Out;
      this.empID = timeKeepingService.empID;
      return this;


    }

    public TimeKeeping build(){

      return new TimeKeeping(this);
    }


  }
}

