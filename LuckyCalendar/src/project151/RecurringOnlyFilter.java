/**
 *	@(#)RecurringOnlyFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
package project151;
public class RecurringOnlyFilter implements EventFilter {
	public String getName() {
		return "recurring events only";
	}

	public Event[] filter(Event[] events) {
		return null;
	}
}
