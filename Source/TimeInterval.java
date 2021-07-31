/**
 *	@(#)TimeInterval.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeInterval implements Comparable<TimeInterval>{
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("H:mm");

	private final LocalTime startTime, endTime;
	/**
	 *	Initializes this TimeInterval with String representations of the start and end time
	 *	@param startTime - starting time to set for this interval
	 *	@param endTime - ending time to set for this interval
	**/
	public TimeInterval(String startTime, String endTime) {
		this(LocalTime.parse(startTime, FORMATTER), LocalTime.parse(endTime, FORMATTER));
	}
	/**
	 *	Initializes this TimeInterval with the start and end time as LocalTime objects
	 *	@param startTime - starting time to set for this interval
	 *	@param endTime - ending time to set for this interval
	**/
	public TimeInterval(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;

		if (startTime.compareTo(endTime) >= 0) {
			throw new RuntimeException("Start time must be before the end time");
		}
	}
	/**
	 *	Checks if this TimeInterval conflicts with the other TimeInterval
	 *	@param other TimeInterval to check for overlap
	 *	@return a boolean of whether this TimeInterval conflicts with the other
	**/
	public boolean conflicts(TimeInterval other) {
		// No matter what, there is a conflict if the start time or end time match.
		if (this.startTime.equals(other.startTime) || this.endTime.equals(other.endTime)) {
			return true;
		}

		// This starts before other.
		if (this.startTime.compareTo(other.startTime) < 0) {
			// There is only conflict if this ends after the other starts too.
			return (this.endTime.compareTo(other.startTime) > 0);
		}
		// This starts after other.
		// There is only conflict if this starts before the other ends too.
		return (this.startTime.compareTo(other.endTime) < 0);
	}
	/**
	 *	Returns a String encoding of this TimeInterval suitable for saving to file
	 *	and being restored from.
	 *	@return a String encoding of this TimeInterval
	**/
	public String encode() {
		return null;
	}
	/**
	 *	Returns a String representation of this TimeInterval
	 *	@return a String representation of this TimeInterval
	**/
	@Override
	public String toString() {
		return this.startTime.format(FORMATTER) + " - " + this.endTime.format(FORMATTER);
	}
	/**
	 *	Returns an TimeInterval decoded from two strings in a saved file.
	 *	@return an TimeInterval decoded from two strings.
	**/
	public static TimeInterval decode(String input) {
		return null;
	}
	/**
	 * Compares this TimeInterval to an other TimeInterval
	 * @param other TimeInterval this one is being compared to
	 * @return an integer representation of the difference in the start times of this event and another
	 */
	//@Override
	public int compareTo(TimeInterval other) {
		int comparison = this.startTime.compareTo(other.startTime);
		if (comparison != 0) {
			return comparison;
		}
		return this.endTime.compareTo(other.endTime);
	}
}
