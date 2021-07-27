/**
 *	@(#)EventFilter.java
 *
 *	@author Yorick van de Water
 *	@version 1.00 2021/7/17
**/
package project151;
public interface EventFilter {
	public String getName();
	public Event[] filter(Event[] events);
}
