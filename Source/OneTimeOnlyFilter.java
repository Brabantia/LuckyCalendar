/**
 *	@(#)OneTimeOnlyFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
package project151;
public class OneTimeOnlyFilter implements EventFilter {
	public String getName() {
		return "one-time events only";
	}

	public Event[] filter(Event[] events) {
		return null;
	}
}
