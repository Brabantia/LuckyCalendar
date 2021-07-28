import java.time.LocalDate;

public class Recurring{
    private LocalDate endTime;
    private String week;

    public Recurring(LocalDate endTime, String week) {
        this.endTime = endTime;
        this.week = week;
    }

    @Override
    public String toString() {
        return "model.Recurring{" +
                "endTime=" + endTime +
                ", week='" + week + '\'' +
                '}';
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
