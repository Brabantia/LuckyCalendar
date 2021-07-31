import java.time.LocalDate;

public class Event implements Comparable<Event>{

    private String name;
    private TimeInterval eventTime;
    private Recurring recurring;

    public Event(String name, TimeInterval eventTime) {
        this.name = name;
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        LocalDate date = getEventTime().getDate();

        if (getRecurring() == null) {
            return getName()+" "+(date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 2000 + " "
                    + getEventTime().getBeginTime() + " " + getEventTime().getEndTime());

        } else {
            return getName()+" "+(getRecurring().getWeek() + " "
                    + getEventTime().getBeginTime() + " "
                    + getEventTime().getEndTime() + " "
                    + date.getMonth().getValue() + "/" + date.getDayOfMonth() + "/" + date.getYear() % 2000 + " "
                    + getRecurring().getEndTime().getMonth().getValue() + "/" + getRecurring().getEndTime().getDayOfMonth() + "/" + getRecurring().getEndTime().getYear() % 2000);

        }

    }

    public boolean recurOccur(LocalDate date){
        String weekStr = "SMTWRFA";
        String dateStr = weekStr.charAt(date.getDayOfWeek().getValue()-1)+"";
        if (eventTime.getDate().compareTo(date) <=0
         && recurring.getEndTime().compareTo(date) >= 0){
            if (recurring.getWeek().contains(dateStr)){
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimeInterval getEventTime() {
        return eventTime;
    }

    public void setEventTime(TimeInterval eventTime) {
        this.eventTime = eventTime;
    }

    public Recurring getRecurring() {
        return recurring;
    }

    public void setRecurring(Recurring recurring) {
        this.recurring = recurring;
    }

    @Override
    public int compareTo(Event o) {
        return eventTime.getDate().compareTo(o.eventTime.getDate());
    }
}
