/**
 *	@(#)RecurringEvent.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.*;

public class RecurringEvent extends Event {
	/**
	 *	Initializes this RecurringEvent using Strings for the date
	 *	@param name - the name of this RecurringEvent
	 *	@param date - the date on which this RecurringEvent occurs
	 *	@param time - the time during which this RecurringEvent occurs
	 *	@param end - the time at which this RecurringEvent ends
	 *	@param days - the days on which this RecurringEvent occurs
	**/
	public RecurringEvent(String name, String date, TimeInterval time, String end, String days) {
		super(name, date, time);
	}
	/**
	 *	Creates a RecurringEvent.
	 *	@param name - the name of this RecurringEvent
	 *	@param date - the date on which this RecurringEvent occurs
	 *	@param time - the time during which this RecurringEvent occurs
	 *	@param end - the time at which this RecurringEvent ends
	 *	@param days - the days on which this RecurringEvent occurs
	**/
	public RecurringEvent(String name, LocalDate date, TimeInterval time, LocalDate end, DayOfWeek... days) {
		super(name, date, time);
	}
	/**
	 *	Checks if this RecurringEvent occurs on the specified date
	 *	@param date - to check for occurrence
	 *	@return whether this RecurringEvent happens on the specified date
	**/
	@Override
	public boolean occursOn(LocalDate date) {
		return false;
	}
	/**
	 *	Checks for a time conflict with another Event
	 *	@param other - Event to check against for a time conflict
	 *	@return whether there is a time conflict with the other Event
	**/
	@Override
	public boolean conflicts(Event other) {
		return false;
	}
	/**
	 *	Returns a String encoding of this RecurringEvent suitable for saving to
	 *	file and being restored from.
	 *	@return a String encoding of this RecurringEvent
	**/
	@Override
	public String encode() {
		return null;
	}
	/**
	 *	Returns a String representation of this RecurringEvent
	 *	@return a String representation of this RecurringEvent
	**/
	@Override
	public String toString() {
		return null;
	}
	/**
	 *	Returns a String with a list of the days the RecurringEvent occurs on
	 *	@param days - the days this event occurs on
	 *	@return a String with a list of the days this RecurringEvent occurs on
	**/
	public static String daysOfWeek(DayOfWeek... days) {
		return null;
	}
	/**
	 *	Returns an array with all the days of the week this RecurringEvent occurs on
	 *	@param text - a String with the days this event occurs on
	 *	@return an array of all the days of the week this RecurringEvent occurs on
	**/
	public static DayOfWeek[] daysOfWeek(String text) {
		return null;
	}
	/**
	 *	Checks for common occurences of two RecurringEvents
	 *	@param list1 - all the days one event being compared occurs on
	 *	@param list2 - all the days another event being compared occurs on
	 *	@return all the days on which both events take place
	**/
	public static DayOfWeek[] intersect(DayOfWeek[] list1, DayOfWeek[] list2) {
		return null;
	}
	/**
	 *	Checks if the occurence of a RecurringEvent contains a specific day
	 *	@param days - the RecurringEvent occurs on
	 *	@param date - the date to check against
	 *	@return whether that Recurring event indeed occurs on the specific date
	**/
	public static boolean containsDay(DayOfWeek[] days, LocalDate date) {
		return false;
	}
	/**
	 *	Returns an RecurringEvent decoded from two strings in a saved file.
	 *	@return an RecurringEvent decoded from two strings.
	**/
	public static Event decode(String line1, String line2) {
		return null;
	}
}
