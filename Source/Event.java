/**
 *	@(#)Event.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.LocalTime;
import java.time.LocalDate;

public class Event {
	/**
	 *	Initializes this event with a String representation of the date
	 *	@param name - the name of this event
	 * @param date - the date on which this event takes place
	 * @param time - the time during which this event takes place
	**/
	public Event(String name, String date, TimeInterval time) {
		
	}
	/**
	 *	Initializes this event with a LocalDate instance
	 * @param name - the name of this event
	 * @param date - the date on which this event takes place
	 * @param time - the time during which this event takes place
	**/
	public Event(String name, LocalDate date, TimeInterval time) {
	
	}

	public Event(String name, String description, String location, boolean isAvailable, TimeInterval time, LocalDate date, String... attendees) {
	}
	/**
	 * Gets the name of this Event
	 * @return the name of this Event
	**/
	public String getName() {
		return "";
	}

	public String getDescription() {
		return "";
	}

	public String getLocation() {
		return "";
	}

	public String[] getAttendees() {
		return null;
	}

	public boolean isAvailable() {
		return false;
	}
	/**
	 * Returns the TimeInterval of this Event
	 * @return the TimeInterval of this Event
	**/
	public TimeInterval getTime() {
		return null;
	}
	/**
	 * Returns the date of this Event
	 * @return the date of this Event as a LocalDate
	**/
	public LocalDate getDate() {
		return null;
	}
	/**
	 * Checks if this Event occurs on the specified date
	 * @param date - to compare against the date of this Event
	 * @return whether this event occurs on the specified date
	**/
	public boolean occursOn(LocalDate date) {
		return false;
	}
	/**
	 * Checks if this Event conflicts with another Event
	 * @param other - to compare against this Event for a conflict
	 * @return whether this Event conflicts with the other Event
	**/
	public boolean conflicts(Event other) {
		return false;
	}
	/**
	 * Returns a String encoding of this Event suitable for saving to file and
	 * being restored from.
	 * @return a String encoding of this Event
	**/
	public String encode() {
		return null;
	}
	/**
	 * Returns a String representation of this Event
	 * @return a String representation of this Event
	**/
	@Override
	public String toString() {
		return null;
	}
	/**
	 * Returns an Event decoded from two strings in a saved file.
	 * @return an Event decoded from two strings.
	**/
	public static Event decode(String line1, String line2) {
		return null;
	}
}
