/**
 *	@(#)RecurringEvent.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.*;
import java.util.ArrayList;

public class RecurringEvent extends Event {
	private final LocalDate start, end;
	private final DayOfWeek[] days;
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
		this.start = LocalDate.parse(date, super.FORMATTER);
		this.end = LocalDate.parse(end, super.FORMATTER);
		this.days = daysOfWeek(days);
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
		this.start = date;
		this.end = end;
		this.days = days;
	}
	/**
	 *	Checks if this RecurringEvent occurs on the specified date
	 *	@param date - to check for occurrence
	 *	@return whether this RecurringEvent happens on the specified date
	**/
	@Override
	public boolean occursOn(LocalDate date) {
		if (this.start.isAfter(date) || this.end.isBefore(date)) {
			return false;
		}

		for (DayOfWeek dayOfWeek : this.days) {
			if (dayOfWeek == date.getDayOfWeek()) {
				return true;
			}
		}

		return false;
	}
	/**
	 *	Checks for a time conflict with another Event
	 *	@param other - Event to check against for a time conflict
	 *	@return whether there is a time conflict with the other Event
	**/
	@Override
	public boolean conflicts(Event other) {
		// We only know how to check against other recurring events.
		if (!(other instanceof RecurringEvent)) {
			return other.conflicts(this);
		}
		// If the time doesn't conflict, it doesn't matter what days the event occurs on.
		if (!this.getTime().conflicts(other.getTime())) {
			return false;
		}
		RecurringEvent event = (RecurringEvent)other;

		// Check if there is no overlap in dates.
		if (event.start.isAfter(this.end) || event.end.isBefore(this.start)) {
			return false;
		}

		// Check if there is no overlap in days of the week.
		DayOfWeek[] days = intersect(this.days, event.days);
		if (days.length == 0) {
			return false;
		}

		// If there is going to be overlap, it will be over the first 7 days of either event.
		for (int day = 0; day < 7; ++day) {
			LocalDate today = this.start.plusDays(day);
			if (!containsDay(days, today)) {
				continue;
			}
			if (this.occursOn(today) && event.occursOn(today)) {
				return true;
			}
		}
		for (int day = 0; day < 7; ++day) {
			LocalDate today = event.start.plusDays(day);
			if (!containsDay(days, today)) {
				continue;
			}
			if (this.occursOn(today) && event.occursOn(today)) {
				return true;
			}
		}

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
		String text = super.getName() + "\n" + daysOfWeek(this.days) + " " + this.getTime();

		// Show dates near today if possible.
		LocalDate today = LocalDate.now();
		if (this.start.isAfter(today) || this.end.isBefore(today)) {
			today = this.start;
		}

		// Grab the days within the next month
		int count = 0;
		for (int day = 0; day < 30; ++day) {
			today = today.plusDays(1);
			if (!containsDay(days, today)) {
				continue;
			}
			if (count++ > 5) {
				text += " ...";
				break;
			}
			text += " " + today.format(FORMATTER);
		}
		return text;
	}
	/**
	 *	Returns a String with a list of the days the RecurringEvent occurs on
	 *	@param days - the days this event occurs on
	 *	@return a String with a list of the days this RecurringEvent occurs on
	**/
	public static String daysOfWeek(DayOfWeek... days) {
		String text = "";
		for (DayOfWeek day : days) {
			switch (day) {
				case SUNDAY:
					text += "S";
					break;
				case MONDAY:
					text += "M";
					break;
				case TUESDAY:
					text += "T";
					break;
				case WEDNESDAY:
					text += "W";
					break;
				case THURSDAY:
					text += "R";
					break;
				case FRIDAY:
					text += "F";
					break;
				case SATURDAY:
					text += "A";
					break;
				default:
					throw new RuntimeException("Invalid day of the week: " + day);
			}
		}
		return text;
	}
	/**
	 *	Returns an array with all the days of the week this RecurringEvent occurs on
	 *	@param text - a String with the days this event occurs on
	 *	@return an array of all the days of the week this RecurringEvent occurs on
	**/
	public static DayOfWeek[] daysOfWeek(String text) {
		text = text.toUpperCase();
		DayOfWeek[] days = new DayOfWeek[text.length()];
		for (int a = 0; a < text.length(); ++a) {
			switch (text.charAt(a)) {
				case 'S':
					days[a] = DayOfWeek.SUNDAY;
					break;
				case 'M':
					days[a] = DayOfWeek.MONDAY;
					break;
				case 'T':
					days[a] = DayOfWeek.TUESDAY;
					break;
				case 'W':
					days[a] = DayOfWeek.WEDNESDAY;
					break;
				case 'R':
					days[a] = DayOfWeek.THURSDAY;
					break;
				case 'F':
					days[a] = DayOfWeek.FRIDAY;
					break;
				case 'A':
					days[a] = DayOfWeek.SATURDAY;
					break;
				default:
					throw new RuntimeException("Invalid day of the week at index=" + a + " for text: " + text);
			}
		}
		return days;
	}
	/**
	 *	Checks for common occurences of two RecurringEvents
	 *	@param list1 - all the days one event being compared occurs on
	 *	@param list2 - all the days another event being compared occurs on
	 *	@return all the days on which both events take place
	**/
	public static DayOfWeek[] intersect(DayOfWeek[] list1, DayOfWeek[] list2) {
		ArrayList<DayOfWeek> intersection = new ArrayList<>();
		for (int a = 0; a < list1.length; ++a) {
			for (int b = 0; b < list2.length; ++b) {
				if (list1[a] == list2[b]) {
					intersection.add(list1[a]);
				}
			}
		}
		return intersection.toArray(new DayOfWeek[intersection.size()]);
	}
	/**
	 *	Checks if the occurence of a RecurringEvent contains a specific day
	 *	@param days - the RecurringEvent occurs on
	 *	@param date - the date to check against
	 *	@return whether that Recurring event indeed occurs on the specific date
	**/
	public static boolean containsDay(DayOfWeek[] days, LocalDate date) {
		for (DayOfWeek day : days) {
			if (day == date.getDayOfWeek()) {
				return true;
			}
		}
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
