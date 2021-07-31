import java.time.LocalDate;

public class TimeInterval {

    private LocalDate date;
    private Clock beginTime;
    private Clock endTime;

    @Override
    public String toString() {
        return date +" " + beginTime.toString()+" " +endTime.toString();
    }

    public TimeInterval(LocalDate date, String beginTime, String endTime) {
        this.date = date;
        this.beginTime = new Clock(beginTime);
        this.endTime = new Clock(endTime);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Clock getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Clock beginTime) {
        this.beginTime = beginTime;
    }

    public Clock getEndTime() {
        return endTime;
    }

    public void setEndTime(Clock endTime) {
        this.endTime = endTime;
    }
}

