public class Clock implements Comparable<Clock>{
    private int hour;
    private int minute;

    @Override
    public String toString() {
        return hour + ":" + minute;
    }

    public Clock(String time) {
        String strs[] = time.split(":");
        hour = Integer.parseInt(strs[0]);
        minute = Integer.parseInt(strs[1]);
    }

    public Clock(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public int compareTo(Clock o) {
        return hour != o.hour?hour-o.hour:minute-o.minute;
    }
}
