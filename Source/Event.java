/**
 *	@(#)Event.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.LocalDate;
import java.util.Comparator;

public class Event {
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	private final String name;
	private final LocalDate date;
	private final TimeInterval time;
	private final String description;
	private final String location;
	private final boolean isAvailable;
	private final String[] attendees;

	/**
	 *	Initializes this event with a String representation of the date
	 *	@param name - the name of this event
	 * @param date - the date on which this event takes place
	 * @param time - the time during which this event takes place
	**/
	public Event(String name, String date, TimeInterval time) {
		this(name, LocalDate.parse(date, FORMATTER), time);
	}
	/**
	 *	Initializes this event with a LocalDate instance
	 * @param name - the name of this event
	 * @param date - the date on which this event takes place
	 * @param time - the time during which this event takes place
	**/
	public Event(String name, LocalDate date, TimeInterval time) {
		this(name, "", "", true, time, date);
	}

	public Event(String name, String description, String location, boolean isAvailable, TimeInterval time, LocalDate date, String... attendees) {
		this.name = name;
		this.description = description;
		this.isAvailable = isAvailable;
		this.time = time;
		this.date = date;
		this.attendees = attendees;
		this.location = location;
	}
	/**
	 * Gets the name of this Event
	 * @return the name of this Event
	**/
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getLocation() {
		return this.location;
	}

	public String[] getAttendees() {
		return this.attendees;
	}

	public boolean isAvailable() {
		return this.isAvailable;
	}
	/**
	 * Returns the TimeInterval of this Event
	 * @return the TimeInterval of this Event
	**/
	public TimeInterval getTime() {
		return this.time;
	}
	/**
	 * Returns the date of this Event
	 * @return the date of this Event as a LocalDate
	**/
	public LocalDate getDate() {
		return this.date;
	}
	/**
	 * Checks if this Event occurs on the specified date
	 * @param date - to compare against the date of this Event
	 * @return whether this event occurs on the specified date
	**/
	public boolean occursOn(LocalDate date) {
		return this.date.equals(date);
	}
	/**
	 * Checks if this Event conflicts with another Event
	 * @param other - to compare against this Event for a conflict
	 * @return whether this Event conflicts with the other Event
	**/
	public boolean conflicts(Event other) {
		if (!other.occursOn(this.date)) {
			return false;
		}
		return this.time.conflicts(other.time);
	}
	/**
	 * Returns a String encoding of this Event suitable for saving to file and
	 * being restored from.
	 * @return a String encoding of this Event
	 *
	 * Sample: "Math Class";2014;1;2;MWF;17;18;
	**/
	public String encode() {
		String text = "Event;";
		text += "\"" + this.name + "\";";
		text += "\"" + this.description + "\";";
		text += this.isAvailable + ";";
		text += this.date.format(FORMATTER) + ";";
		text += this.time.encode() + ";";
		text += this.location + ";";
		for (String attendee : this.attendees) {
			text += attendee + ",";
		}
		text += ";";
		return text;
	}
	/**
	 * Returns a String representation of this Event
	 * @return a String representation of this Event
	**/
	@Override
	public String toString() {
		return this.date.format(FORMATTER) + " " + this.time + " " + this.name;
	}
	/**
	 * Returns an Event decoded from two strings in a saved file.
	 * @return an Event decoded from two strings.
	**/
	public static Event decode(String line) {
		if(line.startsWith("RecurringEvent")) {
			return RecurringEvent.decode(line);
		}
		String[] vars = line.split("\"");
		String name = vars[1];
		String description = vars[3];

		vars = vars[4].split(";");
		boolean isAvailable = Boolean.parseBoolean(vars[1]);
		LocalDate date = LocalDate.parse(vars[2], FORMATTER);
		TimeInterval time = TimeInterval.decode(vars[3]);
		String location = vars[4];

		String[] attendees = vars[5].split(",");
		if(attendees.length == 1 && attendees[0].equals("")) {
			return new Event(name, description, location, isAvailable, time, date);
		}
		return new Event(name, description, location, isAvailable, time, date, attendees);
	}
}
