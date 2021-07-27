/**
 *	@(#)TimeInterval.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
package project151;
import java.time.*;

public class TimeInterval {
	/**
	 *	Initializes this TimeInterval with String representations of the start and end time
	 *	@param startTime - starting time to set for this interval
	 *	@param endTime - ending time to set for this interval
	**/
	public TimeInterval(String startTime, String endTime) {
	}
	/**
	 *	Initializes this TimeInterval with the start and end time as LocalTime objects
	 *	@param startTime - starting time to set for this interval
	 *	@param endTime - ending time to set for this interval
	**/
	public TimeInterval(LocalTime startTime, LocalTime endTime) {
	}
	/**
	 *	Checks if this TimeInterval conflicts with the other TimeInterval
	 *	@param other TimeInterval to check for overlap
	 *	@return a boolean of whether this TimeInterval conflicts with the other
	**/
	public boolean conflicts(TimeInterval other) {
		return false;
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
		return null;
	}
	/**
	 *	Returns an TimeInterval decoded from two strings in a saved file.
	 *	@return an TimeInterval decoded from two strings.
	**/
	public static TimeInterval decode(String input) {
		return null;
	}
}
